package swatechgolf.sponsor_registration_file_enhancer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import swatechgolf.utility.DateConverter;
import swatechgolf.utility.InMemoryFile;
import swatechgolf.utility.KeyValueParser;
import swatechgolf.utility.Listener;
import swatechgolf.utility.Message;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Receives messages containing file content, parses the file content into key/value pairs,
 * writes the file contents to a new file with a file name that includes the time the registration was
 * received, the vendor name, and the type of sponsorship. Publishes the key/value pairs for consumers.
 * 
 * Create Date: May 27, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class SponsorRegistrationFileEnhancer implements Listener {
   private String _outputPath;
   @Autowired KeyValueParser _keyValueParser;
   @Autowired DateConverter _dateConverter;
   @Autowired SponsorRegistrationFileEnhancerMessageRouter _sponsorRegistrationFileEnhancerMessageRouter;
   @Autowired SponsorRegistrationFileEnhancerOutputTransformer _outputTransformer;

   public void setOutputPath(String outputPath) {
      _outputPath = outputPath;
   }
   
   @Async
   public void processMessage(Message message) {
      Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());
      _logger.info("Received messsage {}", message);

      File file = (File) message.getHeader("File");
      InMemoryFile inMemoryFile = (InMemoryFile) message.getPayload();
      Map<String, String> keyValues = _keyValueParser.parse(inMemoryFile.getRecords());
      SponsorRegistration sponsorRegistration = create(keyValues);
      writeFile(file, inMemoryFile, keyValues);
      Message outputMessage = _outputTransformer.create(sponsorRegistration);
      _sponsorRegistrationFileEnhancerMessageRouter.publish(outputMessage);
   }

   private SponsorRegistration create(Map<String, String> keyValues) {
      DateTime receivedDateTime = _dateConverter.fromString(keyValues.get("Date"));
      long sponsorId = receivedDateTime.getMillis();
      Integer numberOfGolfers = Integer.valueOf(keyValues.get("Select the number of golfers for this team"));
      
      SponsorRegistration sponsorRegistration = new SponsorRegistration(sponsorId, 
            receivedDateTime, 
            keyValues.get("Sponsorship Choice"),
            keyValues.get("Company Name"), 
            keyValues.get("Point of Contact"), 
            keyValues.get("Contact Phone"), 
            keyValues.get("Contact Email"), 
            keyValues.get("Notes"), 
            numberOfGolfers);
      
      sponsorRegistration.setGolfers(createGolfers(numberOfGolfers, keyValues));
      return sponsorRegistration;
   }
   
   private List<Golfer> createGolfers(Integer numberOfGolfers, Map<String, String> keyValues) {
      List<Golfer> golfers = new ArrayList<>();
      
      for (int i=1; i<=numberOfGolfers; i++) {
         String nameKey = String.format("Golfer %d Name", i);
         String emailKey = String.format("Golfer %d Email", i);
         String shirtKey = String.format("Golfer %d Shirt", i);
         Golfer golfer = new Golfer(keyValues.get(nameKey), 
               keyValues.get(emailKey), 
               keyValues.get(shirtKey));
         golfers.add(golfer);
      }
      
      return golfers;
   }
   
   private void writeFile(File file, InMemoryFile inMemoryFile, Map<String, String> keyValues) {
      DateTime receivedDateTime = _dateConverter.fromString(keyValues.get("Date"));
      String sponsorship = keyValues.get("Sponsorship Choice");
      sponsorship = sponsorship.replace("/", "-");
      String fileName = _dateConverter.toString(receivedDateTime) + "-" + keyValues.get("Company Name") + "-"
            + sponsorship + "-" + "Sponsor_Registration.txt";
      String fullyQualifiedName = _outputPath + File.separator + fileName;
      try {
         BufferedWriter writer = new BufferedWriter(new FileWriter(fullyQualifiedName));
         for (String record : inMemoryFile.getRecords()) {
            writer.write(record + "\n");
         }

         writer.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
}
