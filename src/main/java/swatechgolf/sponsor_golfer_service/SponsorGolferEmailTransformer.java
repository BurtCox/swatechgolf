package swatechgolf.sponsor_golfer_service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import swatechgolf.sponsor_registration_file_enhancer.Golfer;
import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistration;
import swatechgolf.sponsorship_assignment_controller.EmailMessage;
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
public class SponsorGolferEmailTransformer {
   public Message create(SponsorRegistration sponsorRegistration) {
      Message message = new Message();
      message.setMessageType(MessageType.EMAIL);
      
      List<String> recipients = new ArrayList<>();
      recipients.add(sponsorRegistration.getContactEmail());
      List<Golfer> golfers = sponsorRegistration.getGolfers();
      
      for (Golfer golfer : golfers) {
         recipients.add(golfer.getEmail());
      }

      String subject = "Sponsor Golfer Registration Received - Southwest Airlines TLC Golf Tournament";
      String text = createEmailBody(sponsorRegistration);
      EmailMessage emailMessage = new EmailMessage("swatechgolf@wnco.com", recipients, subject, text);
      message.setPayload(emailMessage);
      
      return message;
   }
   
   private String createEmailBody(SponsorRegistration sponsorRegistration) {
      String companyName = sponsorRegistration.getCompanyName();
      Integer numberOfGolfers = sponsorRegistration.getNumberOfGolfers();
      List<Golfer> golfers = sponsorRegistration.getGolfers();
      
      String text = "We received the " + companyName + " registration for "
            + numberOfGolfers + " golfers.\n"
            + "The current status is " + sponsorRegistration.getStatus() + "\n";
      
      for (Golfer golfer : golfers) {
         text += "\t" + golfer.getName() + "\t" + golfer.getEmail() + "\t" + golfer.getShirtSize() + "\n";
      }
           
      return text;
   }

}
