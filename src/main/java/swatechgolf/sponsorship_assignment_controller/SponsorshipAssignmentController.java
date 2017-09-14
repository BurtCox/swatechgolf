package swatechgolf.sponsorship_assignment_controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.swacorp.ags.file.GenericCsvReader;
import com.swacorp.ags.file.GenericCsvWriter;

import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistration;
import swatechgolf.utility.Listener;
import swatechgolf.utility.Message;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: May 28, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class SponsorshipAssignmentController implements Listener {
   @Autowired SponsorshipAssignmentEmailTransformer _emailTransformer;
   @Autowired SponsorshipAssignmentOutputTransformer _outputTransformer;
   @Autowired SponsorshipAssignmentMessageRouter _messageRouter;
   Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());
   private AvailableSponsorships _availableSponsorships;
   private String _assignedSponsorshipsFileName;
   private String _standbyListFileName;
   private String _availableSponsorshipsFileName;
   private boolean _initialized = false;
   
   @Override
   @Async
   public synchronized void processMessage(Message message) {
      if (!_initialized) {
         initialize();
      }
      
      SponsorRegistration sponsorRegistration = (SponsorRegistration)message.getPayload();
      String sponsorshipType = sponsorRegistration.getSponsorship();
      String sponsor = sponsorRegistration.getCompanyName();
      Long sponsorId = sponsorRegistration.getSponsorId();
      _logger.info("Company: {}, Sponsorship: {}", sponsor, sponsorshipType);
      AvailableSponsorship assignedSponsorship = _availableSponsorships.assign(sponsorId, sponsor, sponsorshipType);
      
      if (assignedSponsorship != null) {
         writeAssignedSponsorship(assignedSponsorship);
         sponsorRegistration.setStatus("Assigned");
         sponsorRegistration.setSponsorshipId(assignedSponsorship.getSponsorshipId());
      }
      else {
         writeStandbyList(sponsorId, sponsor, sponsorshipType);
         sponsorRegistration.setStatus("Standby");
      }
      
      Message emailMessage = _emailTransformer.create(sponsorRegistration);
      _messageRouter.publish(emailMessage);
      Message outputMessage = _outputTransformer.create(sponsorRegistration);
      _messageRouter.publish(outputMessage);
   }

   private void initialize() {
      _availableSponsorships = new AvailableSponsorships();
      _availableSponsorships.add(readAvailableSponsorships());
      _availableSponsorships.addAssignedSponsorships(readAssignedSponsorships());         
      _initialized = true;
   }
   
   private void writeStandbyList(Long sponsorId, String sponsor, String sponsorshipType) {
      GenericCsvWriter writer = new GenericCsvWriter();
      boolean append = true;
      String[] fields = new String[3];
      fields[0] = sponsorId.toString();
      fields[1] = sponsorshipType;
      fields[2] = sponsor;
      Collection<String[]> records = new ArrayList<>();
      records.add(fields);
      try {
         writer.write(_standbyListFileName, append, records);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private void writeAssignedSponsorship(AvailableSponsorship availableSponsorship) {
      GenericCsvWriter writer = new GenericCsvWriter();
      boolean append = true;
      String[] fields = availableSponsorship.getValues();
      Collection<String[]> records = new ArrayList<>();
      records.add(fields);

      try {
         writer.write(_assignedSponsorshipsFileName, append, records);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private List<String[]> readAvailableSponsorships() {
      GenericCsvReader reader = new GenericCsvReader();
      List<String[]> records = null;
      
      try {
         records = reader.read(_availableSponsorshipsFileName, "\\|");
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return records;
   }

   private List<String[]> readAssignedSponsorships() {
      GenericCsvReader reader = new GenericCsvReader();
      List<String[]> records = null;
      
      File file = new File(_assignedSponsorshipsFileName);
      
      if (file.canRead()) {
         try {
            records = reader.read(_assignedSponsorshipsFileName, "\\|");
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }         
      }
      
      return records;
   }
   
   public String getAssignedSponsorshipsFileName() {
      return _assignedSponsorshipsFileName;
   }

   public void setAssignedSponsorshipsFileName(String assignedSponsorshipsFileName) {
      _assignedSponsorshipsFileName = assignedSponsorshipsFileName;
   }

   public String getAvailableSponsorshipsFileName() {
      return _availableSponsorshipsFileName;
   }

   public void setAvailableSponsorshipsFileName(String availableSponsorshipsFileName) {
      _availableSponsorshipsFileName = availableSponsorshipsFileName;
   }

   public String getStandbyListFileName() {
      return _standbyListFileName;
   }

   public void setStandbyListFileName(String standbyListFileName) {
      _standbyListFileName = standbyListFileName;
   }
}
