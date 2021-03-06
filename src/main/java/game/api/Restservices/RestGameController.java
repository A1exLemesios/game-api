package game.api.Restservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import game.api.Dao.LeaderBoardDao;
import game.api.Dao.UserDao;
import game.api.Model.LeaderBoardRecord;
import game.api.Model.LeaderBoardResponse;
import game.api.Model.LoginRequest;
import game.api.Model.LoginResponse;
import game.api.Model.PlayResponse;
import game.api.Model.PlayResponse.GameResultStatus;
import game.api.Model.ValidatePalindromeRequest;

@RestController
public class RestGameController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LeaderBoardDao leaderBoardDao;
	
	@Autowired
	private AuthorizationFilter authorizationFilter;

	@PostMapping("/game/palindrome")
	public PlayResponse play(
		@RequestHeader(name = "authorizationToken", required = true) String token ,
		@RequestHeader(name = "username", required = true) String username ,
		@RequestBody ValidatePalindromeRequest requestBody
	) throws Exception {
		
		String palindromeText = requestBody.getPalindromeText();
		System.out.println("User : " + username + "is playing with word / phrase :" + palindromeText );
		authorizationFilter.validateToken(username, token);

		PlayResponse playRes = new PlayResponse();
		if (palindromeText == null || palindromeText.equals("")) {	
			  throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Palindrome text must not be empty");
		}
		ApplicationConstants applicationConsts = new ApplicationConstants();
		String reverse = 
	    	       new StringBuilder(palindromeText)
	    	       .reverse()
	    	       .toString();

		if (reverse.equals(palindromeText)) {
			palindromeText = palindromeText.replaceAll("[^A-Za-z]+", "");
			playRes.setScore( (new StringBuilder().append(palindromeText.length() * applicationConsts.SCORE_VARIANT)).toString());
			playRes.setGameResult(GameResultStatus.WIN);
			
			try {
				LeaderBoardRecord leaderBoardRecord = new LeaderBoardRecord();
				
				leaderBoardRecord.setUserId(username);
				leaderBoardRecord.setScore(playRes.getScore());
				leaderBoardRecord.setPalindromeText(palindromeText);
				
				leaderBoardDao.saveLeaderBoardRecord(leaderBoardRecord);
			} catch (Exception e) {
				 throw new ResponseStatusException(
				           HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving leaderBoard Record in db");
			}
		} else if (!reverse.equals(palindromeText)) {
			playRes.setScore("0");
			playRes.setGameResult(GameResultStatus.LOSS);
		}
		return  playRes;
	}
	
	@PostMapping("/auth/token")
	public LoginResponse createToken(
			@RequestBody LoginRequest requestBody
	) throws Exception {
		if (requestBody.getUserName() == null || requestBody.getUserName().equals("")) {
			 throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Username is mandatory");	
		}
		if (requestBody.getPassWord() == null || requestBody.getPassWord().equals("")) {
			 throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Password is mandatory");	
		}

		LoginResponse res = new LoginResponse();
		String token = authorizationFilter.createToken(requestBody);

		res.setAuthorizationToken(token);
		
		return res;
	}
	
	@PostMapping("/register")
	public void login(
			@RequestBody LoginRequest requestBody
	) throws Exception {
		if (requestBody.getUserName() == null || requestBody.getUserName().equals("")) {
			 throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Username is mandatory");	
		}
		if (requestBody.getPassWord() == null || requestBody.getPassWord().equals("")) {
			 throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Password is mandatory");	
		}
		System.out.println("Register  API called for player :  " + requestBody.getUserName());

		LoginResponse res = new LoginResponse();
		
		try {
			authorizationFilter.registerUser(requestBody);		
		} catch (Exception e) {
			 throw e;
		}
	}
	
	@GetMapping("/leaderBoard/player")
	public LeaderBoardResponse getPlayerBoard(
			@RequestHeader(name = "authorizationToken", required = true) String token ,
			@RequestHeader(name = "username", required = true) String username 
	) throws Exception {
		System.out.println("Get leaderBoard called for player : " + username);
		authorizationFilter.validateToken(username, token);

		LeaderBoardResponse res = new LeaderBoardResponse();
		List <LeaderBoardRecord> playerBoardRecordList = new ArrayList<LeaderBoardRecord>();
		try {
			playerBoardRecordList = leaderBoardDao.getPlayerLeaderBoard(username);
		} catch (Exception e) {
			 throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Error while reading from db ");	
		}
		
		res.setLeaderBoardList(playerBoardRecordList);
		
		return res;
	}
	
	@GetMapping("/leaderBoard/all")
	public LeaderBoardResponse getLeaderBoardForAllPlayers(
			@RequestHeader(name = "authorizationToken", required = true) String token ,
			@RequestHeader(name = "username", required = true) String username 
	) throws Exception {
		System.out.println("Get leaderBoard called for all players by player : " + username);
		authorizationFilter.validateToken(username, token);

		LeaderBoardResponse res = new LeaderBoardResponse();
		List <LeaderBoardRecord> playerBoardRecordList = new ArrayList<LeaderBoardRecord>();
		try {
			playerBoardRecordList = leaderBoardDao.getLeaderBoardForAllPlayers();	
		} catch (Exception e) {
			 throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Error while reading from db ");	
		}
		
		res.setLeaderBoardList(playerBoardRecordList);
		
		return res;
	}
}
