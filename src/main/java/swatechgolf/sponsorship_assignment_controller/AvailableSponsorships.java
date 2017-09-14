package swatechgolf.sponsorship_assignment_controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: May 28, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class AvailableSponsorships {
   private Map<Long, AvailableSponsorship> _sponsorships = new HashMap<>();
   Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());

   public void add(AvailableSponsorship availableSponsorship) {
      _sponsorships.put(availableSponsorship.getSponsorshipId(), availableSponsorship);
   }
   
   public void add(List<String[]> availableSponsorships) {
      for (String[] values : availableSponsorships) {
         AvailableSponsorship availableSponsorship = new AvailableSponsorship(values);
         add(availableSponsorship);
      }
   }
   
   public void addAssignedSponsorships(List<String[]> assignedSponsorships) {
      if (assignedSponsorships != null) {
         for (String[] values : assignedSponsorships) {
            Long sponsorshipId = Long.valueOf(values[0]);
            AvailableSponsorship availableSponsorship = _sponsorships.get(sponsorshipId);
            availableSponsorship.setSponsorshipId(Long.valueOf(values[5]));
            availableSponsorship.setSponsor(values[6]);
         }         
      }
   }
   
   
   public synchronized AvailableSponsorship assign(Long sponsorId, String sponsor, String sponsorshipType) {
      AvailableSponsorship assignedSponsorship = null;
      
      for (AvailableSponsorship availableSponsorship : _sponsorships.values()) {
         if (availableSponsorship.getSponsorshipType().equalsIgnoreCase(sponsorshipType) 
               && availableSponsorship.getSponsor() == null) {
            availableSponsorship.setSponsor(sponsor);
            availableSponsorship.setSponsorId(sponsorId);
            assignedSponsorship = availableSponsorship;
            _logger.info(availableSponsorship.toString());
            break;
         }
      }
      
      return assignedSponsorship;
   }
}
