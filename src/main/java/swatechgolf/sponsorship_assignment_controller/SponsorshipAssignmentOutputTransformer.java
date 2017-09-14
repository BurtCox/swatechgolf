package swatechgolf.sponsorship_assignment_controller;

import org.springframework.stereotype.Component;

import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistration;
import swatechgolf.utility.Message;
import swatechgolf.utility.MessageType;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: Jun 6, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class SponsorshipAssignmentOutputTransformer {
   public Message create(SponsorRegistration sponsorRegistration) {
      Message message = new Message();
      message.setMessageType(MessageType.SPONSOR);
      message.setPayload(sponsorRegistration);
      return message;
   }
}
