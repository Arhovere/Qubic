package shared.server;

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

    ClientMessages(String message) {
        this.message = message;
    }
    
    public String getMessage() {
    	return message;
    }

}
