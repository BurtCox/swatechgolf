package swatechgolf.sponsor_golfer_service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.swacorp.ags.file.GenericCsvWriter;

import swatechgolf.sponsor_registration_file_enhancer.Golfer;
import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistration;
import swatechgolf.utility.Listener;
import swatechgolf.utility.Message;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Log registered sponsor golfers, assign golfers to hole, send email to registered golfers.
 * 
 * Create Date: Jun 6, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Service
public class SponsorGolferService implements Listener {
   private String _sponsorGolferRegistrationLogFileName;
   @Autowired private SponsorGolferEmailTransformer _sponsorGolferEmailTransformer;
   @Autowired private SponsorGolferMessageRouter _sponsorGolferMessageRouter;

   @Override
   @Async
   public void processMessage(Message message) {
      SponsorRegistration sponsorRegistration = (SponsorRegistration)message.getPayload();
      logGolferRegistrations(sponsorRegistration);
      Message emailMessage = _sponsorGolferEmailTransformer.create(sponsorRegistration);
      _sponsorGolferMessageRouter.publish(emailMessage);
   }

   private void logGolferRegistrations(SponsorRegistration sponsorRegistration) {
      List<Golfer> golfers = sponsorRegistration.getGolfers();
      GenericCsvWriter writer = new GenericCsvWriter();
      
      for (int i=0; i<sponsorRegistration.getNumberOfGolfers(); i++) {
         List<String> valuesList = new ArrayList<>();
         valuesList.add(sponsorRegistration.getReceived().toString());
         valuesList.add(sponsorRegistration.getSponsorId().toString());
         valuesList.add(sponsorRegistration.getCompanyName());
         valuesList.add(sponsorRegistration.getNumberOfGolfers().toString());
         Golfer golfer = golfers.get(i);
         valuesList.add(golfer.getName());
         valuesList.add(golfer.getEmail());
         valuesList.add(golfer.getShirtSize());
         String[] values = new String[valuesList.size()];
         values = valuesList.toArray(new String[0]);
         Collection<String[]> valuesCollection = new ArrayList<>();
         valuesCollection.add(values);
         try {
            writer.write(_sponsorGolferRegistrationLogFileName, true, valuesCollection);
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

      }
   }

   public String getSponsorGolferRegistrationLogFileName() {
      return _sponsorGolferRegistrationLogFileName;
   }

   public void setSponsorGolferRegistrationLogFileName(String sponsorGolferRegistrationLogFileName) {
      _sponsorGolferRegistrationLogFileName = sponsorGolferRegistrationLogFileName;
   }
}
