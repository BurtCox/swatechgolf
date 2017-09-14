package swatechgolf.sponsorship_assignment_controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: May 21, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class EmailMessage {
   private String _from;
   private List<String> _recipients = new ArrayList<>();
   private String _subject;
   private String _text;
   
   public EmailMessage(String from, List<String> recipients, String subject, String text) {
      super();
      _from = from;
      _recipients = recipients;
      _subject = subject;
      _text = text;
   }
   
   public EmailMessage(String from, String recipient, String subject, String text) {
      List<String> recipients = new ArrayList<>();
      recipients.add(recipient);
      _from = from;
      _recipients = recipients;
      _subject = subject;
      _text = text;     
   }
   
   public String getFrom() {
      return _from;
   }
   public void setFrom(String from) {
      _from = from;
   }
   public List<String> getRecipients() {
      return _recipients;
   }
   public void setRecipients(List<String> recipients) {
      _recipients = recipients;
   }
   public String getSubject() {
      return _subject;
   }
   public void setSubject(String subject) {
      _subject = subject;
   }
   public String getText() {
      return _text;
   }
   public void setText(String text) {
      _text = text;
   }

}
