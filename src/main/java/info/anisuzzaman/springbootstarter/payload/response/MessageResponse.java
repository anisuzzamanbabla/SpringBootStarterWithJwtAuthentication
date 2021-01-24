package info.anisuzzaman.springbootstarter.payload.response;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
