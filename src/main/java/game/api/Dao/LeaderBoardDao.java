
package game.api.Dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import game.api.Model.LeaderBoardRecord;
import game.api.Restservices.ApplicationConstants;

@Repository
public class LeaderBoardDao {
   
	public List<LeaderBoardRecord> getPlayerLeaderBoard(String userId) throws Exception {
		 List<LeaderBoardRecord> dbList = new ArrayList<LeaderBoardRecord>();
		 List<LeaderBoardRecord> res = new ArrayList<LeaderBoardRecord>();
	
		 try {
			 Path pathToFile = Paths.get("src/main/java/game/api/Dao/LeaderBoardRecord.txt");
			 String file = pathToFile.toAbsolutePath().toString();
			
		     BufferedReader reader = new BufferedReader(new FileReader(file));
		     
		     String user;
		     String score;
		     String palindromeText;

		     while ((user = reader.readLine()) != null) {
		    	 LeaderBoardRecord ldbr  = new LeaderBoardRecord();
		    	 
		    	 score = reader.readLine();
		    	 palindromeText = reader.readLine();
		    	 
		    	 ldbr.setUserId(user);
		    	 ldbr.setScore(score);
		    	 ldbr.setPalindromeText(palindromeText);
		    	 dbList.add(ldbr);
		     }
		     reader.close();
		} catch (Exception e) {
		    System.err.println("Error while retrieving leaderBoard from db");
			throw e;
		}
		

		 for (LeaderBoardRecord leaddboardRec : dbList) {
			 if (leaddboardRec.getUserId().equals(userId)) {
				 leaddboardRec.setScoreInt(Integer.parseInt(leaddboardRec.getScore()));
				res.add(leaddboardRec); 
			 }
		 }
		 
			res.sort(Comparator.comparing(LeaderBoardRecord::getScoreInt).reversed());
		return res;
	}
	
	public List<LeaderBoardRecord> getLeaderBoardForAllPlayers() throws Exception {
		 List<LeaderBoardRecord> dbList = new ArrayList<LeaderBoardRecord>();

		 try {
				 Path pathToFile = Paths.get("src/main/java/game/api/Dao/LeaderBoardRecord.txt");
				 String filePath = pathToFile.toAbsolutePath().toString();
				 ApplicationConstants applicationConstants = new ApplicationConstants();
				 
			     BufferedReader reader = new BufferedReader(new FileReader(filePath));
			     
			     String userId;
			     String score;
			     String palindromeText;
			     int counter = 0;
			    
			     while ((userId = reader.readLine()) != null) {
			    	 LeaderBoardRecord ldbr  = new LeaderBoardRecord();
			    	 
			    	 score = reader.readLine();
			    	 palindromeText = reader.readLine();
			    	 
			    	 ldbr.setUserId(userId);
			    	 ldbr.setScore(score);
			    	 ldbr.setPalindromeText(palindromeText);
			    	 dbList.add(ldbr);
			    	 counter ++;
			    	 if (counter == applicationConstants.LEADER_BOARD_PAGE_SIZE) {
			 			break;
			    	 }
			     }
			     reader.close();
			} catch (Exception e) {
			    System.err.println("Error while retrieving leaderBoard from db");
				throw e;
			}
		 List<LeaderBoardRecord> res = new ArrayList<LeaderBoardRecord>();

		 	for (LeaderBoardRecord ldbr : dbList) {
		 		ldbr.setScoreInt(Integer.parseInt(ldbr.getScore()));
		 		res.add(ldbr);
		 		
		 	}
			res.sort(Comparator.comparing(LeaderBoardRecord::getScoreInt).reversed());
			
		return res;
	}
	
	public void saveLeaderBoardRecord(LeaderBoardRecord leaderBoardRecord) throws Exception {
		try
		{
			Path pathToFile = Paths.get("src/main/java/game/api/Dao/LeaderBoardRecord.txt");
			String filePath = pathToFile.toAbsolutePath().toString();
			
		    FileWriter fw = new FileWriter(filePath,true);
		    fw.write(leaderBoardRecord.getUserId());
		    fw.write("\n");
		    fw.write(leaderBoardRecord.getScore());
		    fw.write("\n");
		    fw.write(leaderBoardRecord.getPalindromeText());
		    fw.write("\n");

		    fw.close();
		}
		catch(IOException e) {
		    System.err.println("Error while saving leaderBoard in db");
		    throw e;
		}	
	}
}