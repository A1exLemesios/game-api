
package game.api.Model;

import javax.validation.constraints.NotNull;


public class LoginRequest {
	
	
	@NotNull(message = "Username is mandatory")
	private String userName;
	private String passWord;
	
	public LoginRequest(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	public String getUserName() {

		return userName;
	}

	public String getPassWord() {

		return passWord;
	}
}
