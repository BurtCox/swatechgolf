package swatechgolf.file_input;

import java.io.File;

import org.springframework.stereotype.Component;

import swatechgolf.utility.InMemoryFile;
import swatechgolf.utility.Message;
import swatechgolf.utility.MessageType;

@Component
public class FileInputOutputTransformer {
	
	public Message transform(File file, int line, String record) {
		Message message = new Message();
		message.setHeader("File", file);
		message.setHeader("Line", line);
		MessageType messageType = MessageType.getType(file.getName());
		message.setMessageType(messageType);
		message.setPayload(record);
		return message;
	}
	
	public Message transform(File file, InMemoryFile inMemoryFile) {
	     Message message = new Message();
	      message.setHeader("File", file);
	      MessageType messageType = MessageType.getType(file.getName());
	      message.setMessageType(messageType);
	      message.setPayload(inMemoryFile);
	      return message;

	}
}
