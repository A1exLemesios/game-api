package game.api.Restservices;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import game.api.Dao.TokenDao;
import game.api.Dao.UserDao;
import game.api.Model.LoginRequest;
import game.api.Model.Token;
import game.api.Model.User;

@Component
public class AuthorizationFilter {
	
	@Autowired
	private TokenDao tokenDao;
	
	@Autowired
	private UserDao userDao;

	public String createToken (LoginRequest loginRequest) throws Exception {
		System.out.println("Create token called for player : " + loginRequest.getUserName());
		ApplicationConstants applicationConstants = new ApplicationConstants();
		
		User user = userDao.getUserByUsernameAndPassword(loginRequest.getUserName(), loginRequest.getPassWord());
		
		if (user == null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User doesn't exist . Please use register API first");			
		}
		
		String token = null;
		String signingKey = applicationConstants.SIGNING_KEY;
		String issuer = UUID.randomUUID().toString();
		if (loginRequest.getSignKey().equals(signingKey)) {
			
			try {
			    Algorithm algorithm = Algorithm.HMAC256(signingKey);
			    token = JWT.create()
			        .withIssuer(issuer)
			        .sign(algorithm);
			} catch (JWTCreationException exception){
				 throw new ResponseStatusException(
				           HttpStatus.UNAUTHORIZED, "Error while generating token");		
			}
		} else {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "The signing key is incorrect  . !");	
		}
		
		try {
			Token tokenObj = new Token();
			tokenObj.setIssuer(issuer);
			tokenObj.setToken(token);
			
			tokenDao.saveToken(tokenObj);
		} catch (Exception e) {
			throw e;
		}
		
		return token;
	}
	
	public boolean validateToken (String username, String authorizationToken) throws Exception {
		System.out.println("Validate token called for player : " + username);

		ApplicationConstants applicationConstants = new ApplicationConstants();

		List <Token> tokensList = tokenDao.retrieveAllTokens();
		boolean valid = false;
		String issuer = null;
		User user = userDao.getUser(username);
		if (user == null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User not found");	
		}
		
		for(Token token : tokensList) {		
			if (token.getToken().equals(authorizationToken)) {
				valid = true;
				issuer = token.getIssuer();
			}
		}
		if (!valid) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "Token is not valid");		
		}

		try {
		    Algorithm algorithm = Algorithm.HMAC256(applicationConstants.SIGNING_KEY);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(issuer)
		        .build();
		    DecodedJWT jwt = verifier.verify(authorizationToken);
		} catch (JWTVerificationException exception){
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "Signature verification failed. Invalid token");	
		}
	


		return valid;
	}
	
	public void registerUser(LoginRequest request) throws Exception {
		System.out.println("Register user called for player : " + request.getUserName());

		User user = userDao.getUser(request.getUserName());
		if (user != null) {
			 throw new ResponseStatusException(
			           HttpStatus.UNAUTHORIZED, "User with the same username already exists. Please choose another username");	
		}
		
		userDao.register(request);
	}
	
	
	
}
