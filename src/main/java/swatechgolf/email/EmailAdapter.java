package swatechgolf.email;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import swatechgolf.sponsorship_assignment_controller.EmailMessage;
import swatechgolf.utility.Listener;
import swatechgolf.utility.Message;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Receives messages containing email information and sends the email. 
 * 
 * Create Date: May 21, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class EmailAdapter implements Listener {
   private final static String _mailSmtpHost = "AEPMEGSMTP02.swacorp.com";
   private final static String _mailSmtpPort = "25";
   private Session _session;
   
   @Override
   @Async
   public synchronized void processMessage(Message message) {
      if (_session == null) {
         initializeMail();
      }

      EmailMessage emailMessage = (EmailMessage)message.getPayload();
      sendEmail(emailMessage.getFrom(), emailMessage.getRecipients(), emailMessage.getSubject(), emailMessage.getText());
   }

   private void sendEmail(String from, List<String> recipients, String subject, String text) {
      Address[] distribution = new Address[recipients.size()];
      
      try {
         for (int i=0; i<recipients.size(); i++) {
            distribution[i] = new InternetAddress(recipients.get(i));
         }
         
         MimeMessage mimeMessage = new MimeMessage(_session);
         mimeMessage.setFrom(new InternetAddress(from));
         mimeMessage.addRecipients(javax.mail.Message.RecipientType.TO, distribution);
         mimeMessage.setSubject(subject);
         mimeMessage.setText(text);
         Transport.send(mimeMessage);
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private void initializeMail() {
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", _mailSmtpHost);
      properties.setProperty("mail.smtp.port", _mailSmtpPort);
      _session = Session.getDefaultInstance(properties);
   }
}
