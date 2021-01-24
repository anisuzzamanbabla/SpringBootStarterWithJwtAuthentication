package info.anisuzzaman.springbootstarter.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
