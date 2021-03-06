
package game.api.Dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import game.api.Model.LeaderBoardRecord;
import game.api.Model.LoginRequest;
import game.api.Model.User;

@Repository
public class UserDao {
   
	
	
	public void register(LoginRequest req) throws Exception {
		System.out.println("Registering  user  :  " + req.getUserName() + " " + req.getPassWord());

		try {
			Path pathToFile = Paths.get("src/main/java/game/api/Dao/User.txt");
			String filePath = pathToFile.toAbsolutePath().toString();
			
		    FileWriter fw = new FileWriter(filePath,true);
		    fw.write(req.getUserName());
		    fw.write("\n");
		    fw.write(req.getPassWord());
		    fw.write("\n");
		    
		    fw.close();
		}
		catch(IOException e) {
		    System.err.println("Error while saving User in db");
		    throw e;
		}	
		
	}
	
	public User getUser(String username) throws Exception {
		System.out.println("Searching for player with username : " + username);

	     User user = null;
		 try {
			 Path pathToFile = Paths.get("src/main/java/game/api/Dao/User.txt");
			 String file = pathToFile.toAbsolutePath().toString();
			
		     BufferedReader reader = new BufferedReader(new FileReader(file));
		     String userId;
		     while ((userId = reader.readLine()) != null) {
		    	 if (userId.equals(username)) {
		    		 String password = reader.readLine();
		    		 user = new User();
		    		 user.setPassword(password);
		    		 user.setUsername(username);
		    		 
		    		 return user;
		    	 } else {
		    		 reader.readLine();
		    	 }    	 
		     }
		     reader.close();
		} catch (Exception e) {
			throw e;
		}
		 
		 return user;
	}
	
	public User getUserByUsernameAndPassword(String username, String pass) throws Exception {
		System.out.println("Searching for player with username : " + username + " and password " + pass);

	     User user = null;
		 try {
			 Path pathToFile = Paths.get("src/main/java/game/api/Dao/User.txt");
			 String file = pathToFile.toAbsolutePath().toString();
			
		     BufferedReader reader = new BufferedReader(new FileReader(file));
		     String userId;
		     while ((userId = reader.readLine()) != null) {
		    	 if (userId.equals(username)) {
		    		 String password = reader.readLine();
		    		 if (password.equals(pass)) {
			    		 user = new User();
			    		 user.setPassword(password);
			    		 user.setUsername(username);
			    		 return user;
		    		 }
		    	 } else {
		    		 reader.readLine();	
		    	 }	    	 
		     }
		     reader.close();
		} catch (Exception e) {
			throw e;
		}

		 return user;
	}
}