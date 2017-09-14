package swatechgolf.utility;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageRouter {
	private Queue<Message> _messages = new LinkedList<Message>();
	private Set<Subscriber> _subscribers = new HashSet<>();
	private boolean stop = true;
	Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Async
	public void publish(Message message) {
		_messages.add(message);
	}
	
	@Async
	public void subscribe(Subscriber subscriber) {
		_subscribers.add(subscriber);
	}
	
	@Async
	public void start() {
		stop = false;
		while (!stop) {
			notifySubscribers();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public void stop() {
		stop = true;
	}
	
	@Async
	private void notifySubscribers() {
		Message message;
		
		while ((message = _messages.poll()) != null) {
			notifySubscribers(message);
			_messages.remove(message);
		}		
	}
	
	@Async
	private void notifySubscribers(Message message) {
		for (Subscriber subscriber : _subscribers) {
			if (subscriber.getMessageType().equals(message.getMessageType())) {
				_logger.info("Sending message to subscriber");
				subscriber.getListener().processMessage(message);
			}
		}
	}
}
