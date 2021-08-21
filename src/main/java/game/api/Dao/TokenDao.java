
package game.api.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import game.api.Model.LeaderBoardRecord;
import game.api.Model.Token;


@Repository
public class TokenDao {

	
	
	public void saveToken(Token token) throws Exception {
		try
		{
			Path pathToFile = Paths.get("src/main/java/game/api/Dao/Token.txt");
			String filePath = pathToFile.toAbsolutePath().toString();
			
		    FileWriter fw = new FileWriter(filePath,true);
		    fw.write(token.getToken());
		    fw.write("\n");
		    fw.write(token.getIssuer());
		    fw.write("\n");

		    fw.close();
		}
		catch(IOException e) {
		    System.err.println("Error while saving token in db");
		    throw e;
		}	
	}
	
	public List<Token> retrieveAllTokens() throws Exception {
		List <Token> tokensList = new ArrayList<Token>();
		
		 try {
			 Path pathToFile = Paths.get("src/main/java/game/api/Dao/Token.txt");
			 String file = pathToFile.toAbsolutePath().toString();		
		     BufferedReader reader = new BufferedReader(new FileReader(file));
		     
		     String tempToken;
		     while ((tempToken = reader.readLine()) != null) {
				Token token = new Token();
				token.setToken(tempToken);
				token.setIssuer(reader.readLine());
				tokensList.add(token);
		     }
		     reader.close();
		} catch (Exception e) {
		    System.err.println("Error while retrieving tokens in db");
			throw e;
		}
		
		return tokensList;
	}
}