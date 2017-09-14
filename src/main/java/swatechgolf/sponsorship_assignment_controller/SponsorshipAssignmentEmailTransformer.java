/**
 * 
 */
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
 * Create Date: May 21, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class SponsorshipAssignmentEmailTransformer {
   public Message create(SponsorRegistration sponsorRegistration) {
      Message message = new Message();
      message.setMessageType(MessageType.EMAIL);
      String recipient = sponsorRegistration.getContactEmail();
      String subject = "Sponsorship Registration Received - Southwest Airlines TLC Golf Tournament";
      String text = createEmailBody(sponsorRegistration);
      EmailMessage emailMessage = new EmailMessage("swatechgolf@wnco.com", recipient, subject, text);
      message.setPayload(emailMessage);
      
      return message;
   }
   
   private String createEmailBody(SponsorRegistration sponsorRegistration) {
      String sponsorship = sponsorRegistration.getSponsorship();
      String companyName = sponsorRegistration.getCompanyName();
      Integer numberOfGolfers = sponsorRegistration.getNumberOfGolfers();
      
      String text = "We received the " + companyName + " registration for a " + sponsorship + " sponsorship"
            + " with " + numberOfGolfers + " golfers.\n"
            + "The current status is " + sponsorRegistration.getStatus();
      return text;
   }

}
