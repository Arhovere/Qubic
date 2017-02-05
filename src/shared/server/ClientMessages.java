package shared.server;

/**
 * Enum list with commands from the Server to the Client.
 * 
 * @author beitske
 *
 */
public enum ClientMessages {
	
	CREATEROOM("createRoom"),
    JOINROOM("joinRoom"),
    GETROOMLIST("getRoomList"),
    LEAVEROOM("leaveRoom"),
    MAKEMOVE("makeMove"),
    SENDMESSAGE("sendMessage"),
    REQUESTLEADERBOARD("requestLeaderboard"),
    SENDCAPABILITIES("sendCapabilities");

    private final String message;

    /**
     * Method to return the clientmessages.
     * 
     * @param message
     * 				String to be returned
     */
    ClientMessages(String message) {
        this.message = message;
    }
    
    /**
     * Returns message.
     * 
     * @return String message
     */
    public String getMessage() {
    	return message;
    }

}
