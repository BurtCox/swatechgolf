package swatechgolf.sponsor_registration_file_enhancer;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: Jun 5, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class SponsorRegistration {
   private Long _sponsorId;
   private DateTime _received;
   private String _status;
   private Long _sponsorshipId;
   private String _sponsorship;
   private String _companyName;
   private String _contactName;
   private String _contactPhone;
   private String _contactEmail;
   private String _notes;
   private Integer _numberOfGolfers;
   private List<Golfer> _golfers = new ArrayList<>();
   
   public SponsorRegistration(Long sponsorId, DateTime received, String sponsorship, String companyName, String contactName,
         String contactPhone, String contactEmail, String notes, Integer numberOfGolfers) {
      super();
      _sponsorId = sponsorId;
      _received = received;
      _sponsorship = sponsorship;
      _companyName = companyName;
      _contactName = contactName;
      _contactPhone = contactPhone;
      _contactEmail = contactEmail;
      _notes = notes;
      _numberOfGolfers = numberOfGolfers;
   }
   public Long getSponsorId() {
      return _sponsorId;
   }
   public void setSponsorId(Long sponsorId) {
      _sponsorId = sponsorId;
   }
   public DateTime getReceived() {
      return _received;
   }
   public void setReceived(DateTime received) {
      _received = received;
   }
   public String getCompanyName() {
      return _companyName;
   }
   public void setCompanyName(String companyName) {
      _companyName = companyName;
   }
   public String getContactName() {
      return _contactName;
   }
   public void setContactName(String contactName) {
      _contactName = contactName;
   }
   public String getContactPhone() {
      return _contactPhone;
   }
   public void setContactPhone(String contactPhone) {
      _contactPhone = contactPhone;
   }
   public String getContactEmail() {
      return _contactEmail;
   }
   public void setContactEmail(String contactEmail) {
      _contactEmail = contactEmail;
   }
   public String getNotes() {
      return _notes;
   }
   public void setNotes(String notes) {
      _notes = notes;
   }
   public Integer getNumberOfGolfers() {
      return _numberOfGolfers;
   }
   public void setNumberOfGolfers(Integer numberOfGolfers) {
      _numberOfGolfers = numberOfGolfers;
   }
   public List<Golfer> getGolfers() {
      return _golfers;
   }
   public void setGolfers(List<Golfer> golfers) {
      _golfers = golfers;
   }
   public String getSponsorship() {
      return _sponsorship;
   }
   public void setSponsorship(String sponsorship) {
      _sponsorship = sponsorship;
   }
   public String getStatus() {
      return _status;
   }
   public void setStatus(String status) {
      _status = status;
   }
   public Long getSponsorshipId() {
      return _sponsorshipId;
   }
   public void setSponsorshipId(Long sponsorshipId) {
      _sponsorshipId = sponsorshipId;
   }
}
