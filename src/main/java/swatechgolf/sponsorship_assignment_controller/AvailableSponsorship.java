package swatechgolf.sponsorship_assignment_controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: May 16, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class AvailableSponsorship {
   private Long _sponsorshipId;
   private String _sponsorshipType;
   private String _course;
   private String _hole;
   private String _description;
   private Long _sponsorId;
   private String _sponsor;
   
   public AvailableSponsorship(Long sponsorshipId, 
         String sponsorshipType, 
         String course, 
         String hole, 
         String description,
         Long sponsorId,
         String sponsor) {
      super();
      _sponsorshipId = sponsorshipId;
      _sponsorshipType = sponsorshipType;
      _course = course;
      _hole = hole;
      _description = description;
      _sponsorId = sponsorId;
      _sponsor = sponsor;
   }
   
   public String[] getValues() {
      List<String> values = new ArrayList<>();
      values.add(_sponsorshipId.toString());
      values.add(_sponsorshipType);
      values.add(_course);
      values.add(_hole);
      values.add(_description);
      values.add(_sponsorId.toString());
      values.add(_sponsor);
      String[] valueArray = new String[values.size()];
      valueArray = values.toArray(valueArray);
      return valueArray;
   }
   
   public AvailableSponsorship(String[] values) {
      if (values.length > 0) {
         _sponsorshipId = Long.valueOf(values[0]);
      }
      
      if (values.length > 1) {
         setSponsorshipType(values[1]);
      }
      
      if (values.length > 2) {
         setCourse(values[2]);
      }
      
      if (values.length > 3) {
         setHole(values[3]);
      }
      
      if (values.length > 4) {
         setDescription(values[4]);
      }
      
      if (values.length > 5) {
         setSponsorId(Long.valueOf(values[5]));
      }
      
      if (values.length > 6) {
         setSponsor(values[6]);
      }
   }
   
   public String getSponsorshipType() {
      return _sponsorshipType;
   }
   
   public void setSponsorshipType(String sponsorshipType) {
      _sponsorshipType = sponsorshipType;
   }
   
   public String getCourse() {
      return _course;
   }
   
   public void setCourse(String course) {
      _course = course;
   }
   
   public String getHole() {
      return _hole;
   }
   
   public void setHole(String hole) {
      _hole = hole;
   }
   
   public String getDescription() {
      return _description;
   }
   
   public void setDescription(String description) {
      _description = description;
   }
   
   public String getSponsor() {
      return _sponsor;
   }
   
   public void setSponsor(String sponsor) {
      _sponsor = sponsor;
   }
   
   public String toString() {
      ToStringBuilder buf = new ToStringBuilder(this);
      buf.append(_sponsorshipType);
      buf.append(_course);
      buf.append(_hole);
      buf.append(_description);
      buf.append(_sponsor);
      return buf.toString();
   }

   public Long getSponsorshipId() {
      return _sponsorshipId;
   }

   public void setSponsorshipId(Long sponsorshipId) {
      _sponsorshipId = sponsorshipId;
   }

   public Long getSponsorId() {
      return _sponsorId;
   }

   public void setSponsorId(Long sponsorId) {
      _sponsorId = sponsorId;
   }
}
