package swatechgolf.utility;

public class Subscriber {
	private MessageType _messageType;
	private Listener _listener;
	
	public Subscriber(MessageType messageType, Listener listener) {
		super();
		_messageType = messageType;
		_listener = listener;
	}

	public MessageType getMessageType() {
		return _messageType;
	}

	public Listener getListener() {
		return _listener;
	}
}
