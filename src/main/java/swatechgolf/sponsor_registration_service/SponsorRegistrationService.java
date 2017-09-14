package swatechgolf.sponsor_registration_service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.swacorp.ags.file.GenericCsvWriter;

import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistration;
import swatechgolf.utility.Listener;
import swatechgolf.utility.Message;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: Jun 2, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Service
public class SponsorRegistrationService implements Listener {
   private String _sponsorRegistrationLogFileName;
   
   @Override
   @Async
   public synchronized void processMessage(Message message) {
      SponsorRegistration sponsorRegistration = (SponsorRegistration)message.getPayload();
      List<String> valuesList = new ArrayList<>();
      valuesList.add(sponsorRegistration.getReceived().toString());
      valuesList.add(sponsorRegistration.getStatus());
      valuesList.add(sponsorRegistration.getSponsorId().toString());
      valuesList.add(sponsorRegistration.getCompanyName());
      valuesList.add(sponsorRegistration.getSponsorshipId().toString());
      valuesList.add(sponsorRegistration.getSponsorship());
      valuesList.add(sponsorRegistration.getNumberOfGolfers().toString());
      valuesList.add(sponsorRegistration.getNotes());
      String[] values = new String[valuesList.size()];
      values = valuesList.toArray(new String[0]);
      Collection<String[]> valuesCollection = new ArrayList<>();
      valuesCollection.add(values);
      GenericCsvWriter writer = new GenericCsvWriter();
      try {
         writer.write(_sponsorRegistrationLogFileName, true, valuesCollection);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   public String getSponsorRegistrationLogFileName() {
      return _sponsorRegistrationLogFileName;
   }

   public void setSponsorRegistrationLogFileName(String sponsorRegistrationLogFileName) {
      _sponsorRegistrationLogFileName = sponsorRegistrationLogFileName;
   }

}
