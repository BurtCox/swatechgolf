package swatechgolf.utility;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Message {
	private DateTime _createDate = new DateTime();
	private MessageType _messageType;
	private Map<String, Object> _headers = new HashMap<>();
	private Object _payload;
	
	public DateTime getCreateDate() {
		return _createDate;
	}
	
	public Object getHeader(String key) {
		return _headers.get(key);
	}
	
	public void setHeader(String key, Object value) {
		_headers.put(key, value);
	}
	
	public void setPayload(Object payload) {
		_payload = payload;
	}
	
	public Object getPayload() {
		return _payload;
	}
	
	public MessageType getMessageType() {
		return _messageType;
	}
	
	public void setMessageType(MessageType messageType) {
		_messageType = messageType;
	}
	
	public String toString() {
		String buf = new String();
		buf += _createDate.toString() + ", ";
		buf += _messageType.name() + ", ";
		
		for (String key : _headers.keySet()) {
		   Object value = _headers.get(key);
		   buf += "," + key + "=" + value;
		}
		buf += _payload;
		return buf;
	}
}
