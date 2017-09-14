package swatechgolf.sponsor_registration_file_enhancer;

import org.springframework.stereotype.Component;

import swatechgolf.utility.Message;
import swatechgolf.utility.MessageType;

@Component
public class SponsorRegistrationFileEnhancerOutputTransformer {
	
	public Message create(SponsorRegistration sponsorRegistration) {
		Message message = new Message();
		message.setMessageType(MessageType.SPONSOR);
		message.setPayload(sponsorRegistration);
		return message;
	}
}
