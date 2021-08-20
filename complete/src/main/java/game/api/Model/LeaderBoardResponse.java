package game.api.Model;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardResponse {
	

	private List <LeaderBoardRecord> leaderBoardList = new ArrayList<LeaderBoardRecord>();

	public List<LeaderBoardRecord> getLeaderBoardList() {
		return leaderBoardList;
	}

	public void setLeaderBoardList(List<LeaderBoardRecord> leaderBoardList) {
		this.leaderBoardList = leaderBoardList;
	}

	public LeaderBoardResponse(List<LeaderBoardRecord> leaderBoardList) {
		this.leaderBoardList = leaderBoardList;
	}
	
	
	public LeaderBoardResponse() {
		
	}
	
}
