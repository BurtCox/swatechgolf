package swatechgolf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import swatechgolf.email.EmailAdapter;
import swatechgolf.file_input.FileInputAdapter;
import swatechgolf.file_input.FileInputMessageRouter;
import swatechgolf.sponsor_golfer_service.SponsorGolferMessageRouter;
import swatechgolf.sponsor_golfer_service.SponsorGolferService;
import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistrationFileEnhancer;
import swatechgolf.sponsor_registration_file_enhancer.SponsorRegistrationFileEnhancerMessageRouter;
import swatechgolf.sponsor_registration_service.SponsorRegistrationService;
import swatechgolf.sponsorship_assignment_controller.SponsorshipAssignmentController;
import swatechgolf.sponsorship_assignment_controller.SponsorshipAssignmentMessageRouter;
import swatechgolf.utility.MessageType;
import swatechgolf.utility.Subscriber;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Configures the SwaTechGolf services.
 * 
 * Create Date: May 28, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Controller
public class SwaTechGolfController {
	@Autowired private FileInputAdapter _fileInputAdapter;
	@Autowired private SponsorRegistrationFileEnhancer _sponsorRegistrationFileEnhancer;
	@Autowired private FileInputMessageRouter _fileInputMessageRouter;
	@Autowired private SponsorRegistrationFileEnhancerMessageRouter _sponsorRegistrationFileEnhancerMessageRouter;
	@Autowired private SponsorshipAssignmentController _sponsorshipAssignmentController;
	@Autowired private SponsorshipAssignmentMessageRouter _sponsorshipAssignmentMessageRouter;
	@Autowired private EmailAdapter _emailAdapter;
	@Autowired private SponsorRegistrationService _sponsorRegistrationService;
	@Autowired private SponsorGolferService _sponsorGolferService;
	@Autowired private SponsorGolferMessageRouter _sponsorGolferMessageRouter;
	
	Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());


	public void process(String[] args) {
	
	   /*
	    * Configure the Sponsor Registration File Enhancer service
	    */
      _sponsorRegistrationFileEnhancer.setOutputPath("U:/tlc/2018/GolfTournament/Registrations/EmailReceivedEnhanced");
		_fileInputMessageRouter.subscribe(new Subscriber(MessageType.SPONSOR, _sponsorRegistrationFileEnhancer));
      _sponsorRegistrationFileEnhancerMessageRouter.start();
		
		/*
		 * Configure the File Input adapter.
		 */
		_fileInputAdapter.setInput("U:/tlc/2018/GolfTournament/Registrations/EmailReceived");
		_fileInputAdapter.setArchiveDir("U:/tlc/2018/GolfTournament/Registrations/EmailArchives");
		_fileInputAdapter.start();
		_fileInputMessageRouter.start();
		
		/*
		 * Configure the Sponsorship Assignment service.
		 */
		_sponsorshipAssignmentController.setAvailableSponsorshipsFileName("U:/tlc/2018/GolfTournament/Registrations/AvailableSponsorships.csv");
		_sponsorshipAssignmentController.setAssignedSponsorshipsFileName("U:/tlc/2018/GolfTournament/Registrations/AssignedSponsorships.csv");
		_sponsorshipAssignmentController.setStandbyListFileName("U:/tlc/2018/GolfTournament/Registrations/SponsorshipStandbyList.csv");
      _sponsorRegistrationFileEnhancerMessageRouter.subscribe(new  Subscriber(MessageType.SPONSOR, _sponsorshipAssignmentController));
		_sponsorshipAssignmentMessageRouter.start();
		
		/*
		 * Configure the Email adapter.
		 */
      _sponsorshipAssignmentMessageRouter.subscribe(new Subscriber(MessageType.EMAIL, _emailAdapter));
      _sponsorGolferMessageRouter.subscribe(new Subscriber(MessageType.EMAIL, _emailAdapter));
		
		/*
		 * Configure Sponsorship Registration service
		 * 
		 */
		_sponsorRegistrationService.setSponsorRegistrationLogFileName("U:/tlc/2018/GolfTournament/Registrations/SponsorshipRegistrations.csv");
		_sponsorshipAssignmentMessageRouter.subscribe(new Subscriber(MessageType.SPONSOR, _sponsorRegistrationService));
		
		/*
		 * Configure Sponsor Golfer Registration service
		 */
		_sponsorGolferService.setSponsorGolferRegistrationLogFileName("U:/tlc/2018/GolfTournament/Registrations/SponsorGolfers.csv");
		_sponsorshipAssignmentMessageRouter.subscribe(new Subscriber(MessageType.SPONSOR, _sponsorGolferService));
	}
}
