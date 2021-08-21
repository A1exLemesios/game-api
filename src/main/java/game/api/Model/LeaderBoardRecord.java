package game.api.Model;


public class LeaderBoardRecord implements Comparable<LeaderBoardRecord>{
	
	private String palindromeText;
	private String score;
	private int scoreInt;
	private String userId;
	

	public int getScoreInt() {
		return scoreInt;
	}
	public void setScoreInt(int scoreInt) {
		this.scoreInt = scoreInt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPalindromeText() {
		return palindromeText;
	}
	public void setPalindromeText(String palindromeText) {
		this.palindromeText = palindromeText;
	}
	@Override
	public int compareTo(LeaderBoardRecord u) {
    if (getScore() == null || u.getScore() == null) {
      return 0;
    }
    if ( (getScore().compareTo(u.getScore()) > 0)) {
    	System.out.println("getScore is bigger than u.getScore") ;
    } else {
    	System.out.println("u.getScore is bigger than getScore") ;
	
    }
    return getScore().compareTo(u.getScore());
  }
	

}
