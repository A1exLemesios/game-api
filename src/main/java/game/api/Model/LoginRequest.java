
package game.api.Model;

import javax.validation.constraints.NotNull;


public class LoginRequest {
	
	
	private String userName;
	private String passWord;
	private String signKey;

	public LoginRequest(String userName, String passWord, String signKey) {
		this.userName = userName;
		this.passWord = passWord;
		this.signKey = signKey;
	}
	
	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	
	public String getUserName() {

		return userName;
	}

	public String getPassWord() {

		return passWord;
	}
}
