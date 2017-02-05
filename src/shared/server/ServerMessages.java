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
	
	ServerMessages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean equals(String other) {
        return other.equalsIgnoreCase(message);
    }

}
