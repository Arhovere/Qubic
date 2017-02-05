package shared.server;

/**
 * Enum list with commands from the Client to the Server.
 * 
 * @author beitske
 *
 */
public enum ServerMessages {
	
	SERVERCAPABILITIES("serverCapabilities"),
    SENDLISTROOMS("sendListRooms"),
    ROOMCREATED("roomCreated"),
    ASSIGNID("assignID"),
    STARTGAME("startGame"),
    TURNOFPLAYER("playerTurn"),
    NOTIFYMOVE("notifyMove"),
    NOTIFYEND("notifyEnd"),
    ERROR("error"),
    NOTIFYMESSAGE("notifyMessage"),
    SENDLEADERBOARD("sendLeaderBoard");
	
	private final String message;
	
	/**
	 * Returns message with ServerMessage.
	 * @param message
	 * 				String with message
	 */
	ServerMessages(String message) {
		this.message = message;
	}
	
	/**
	 * Returns message.
	 * @return String message
	 */
	public String getMessage() {
		return message;
	}

}
