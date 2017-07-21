package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.db.ApplicationDAO;
import com.ekrem.jsf.db.CandidateDAO;
import com.ekrem.jsf.models.Application;
import com.ekrem.jsf.models.Candidate;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@ManagedBean (name = "applicationController")
@SessionScoped
public class ApplicationController {
	
	private ApplicationDAO applicationDAO;
	private CandidateDAO candidateDAO;
	private List<Application> applications;
	private List<Application> companyApplications;
	private List<Application> candidateApplications;
	private long candidate_id;
	private long hr_id;
	private Date date = new Date();
	private Application theApplication;
	private String statusFilter = "All";
	
	public ApplicationController() throws Exception {
		applications = new ArrayList<>();
		companyApplications = new ArrayList<>();
		candidateApplications = new ArrayList<>();
		applicationDAO = ApplicationDAO.getApplicationInstance();
		candidateDAO = CandidateDAO.getCandidateInstance();
	}
	
	public Application getTheApplication() {
		return theApplication;
	}

	public void setTheApplication(Application theApplication) {
		this.theApplication = theApplication;
	}

	/**
	 * Loads applications
	 */
	public void loadApplications() {
		
		applications.clear();
		companyApplications.clear();
		candidateApplications.clear();
		
		try {
			applications = applicationDAO.getApplications();
			
			for(Application a: applications)
				if(hr_id == a.getHr_id()) {
					if(statusFilter.equals("All")) {
						companyApplications.add(a);
					}
					else if(statusFilter.equals("In Evaluation")) {
						if(a.getStatus().equals("In Evaluation"))
							companyApplications.add(a);
					}
					else if(statusFilter.equals("Accepted")) {
						if(a.getStatus().equals("Accepted"))
							companyApplications.add(a);
					}
					else if(statusFilter.equals("Rejected")) {
						if(a.getStatus().equals("Rejected"))
							companyApplications.add(a);
					}
					
				}
					
			
			for(Application a: applications)
				if(candidate_id == a.getCandidate_id())
					candidateApplications.add(a);
				
				
		} catch (Exception exc) {
			
		}
	}
	
	public void loadTheApplication(long id) {
		
		theApplication=new Application();
		
		try {
			theApplication = applicationDAO.getApplication(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public String addApplication(long hr_id, long advert_id, long candidate_id, String cover_letter) {
		
		for(Application a:applications)
			if(candidate_id == a.getCandidate_id() && advert_id == a.getAdvert_id())
				return "home?faces-redirect=true";
		
		Application application = new Application(hr_id, advert_id, candidate_id, date, cover_letter, "Pending");
		
		try {
			applicationDAO.addApplication(application);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
		
	}
	
	public String updateApplication(Application application) {
		try {
			applicationDAO.updateApplication(application);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public String deleteApplication(long id) {

		try {
			applicationDAO.deleteApplication(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public String changeStatus(long id, String status) {
		
		Application application = new Application();
		
		try {
			application = applicationDAO.getApplication(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(getCandidate(application).getBlacklist()==0 && !application.getStatus().equals(status)) {
			application.setStatus(status);
			//sendEmail("ekrem.ozturk@boun.edu.tr", "rejected");
			updateApplication(application);
		}
		
		
		return "applications?faces-redirect=true";
	}
	
	public Candidate getCandidate(Application a) {
		
		Candidate candidate = applicationDAO.getCandidate(a.getId());
		return candidate;
		
	}
	
	public void filterResults() {
		loadApplications();
	}
	
	public void getId(long hr_id) {
		this.hr_id = hr_id;
	}
	
	public void getCId(long candidate_id) {
		this.candidate_id = candidate_id;
	}

	public long getHr_id() {
		return hr_id;
	}

	public void setHr_id(long hr_id) {
		this.hr_id = hr_id;
	}

	public List<Application> getCompanyApplications() {
		return companyApplications;
	}

	public void setCompanyApplications(List<Application> companyApplications) {
		this.companyApplications = companyApplications;
	}

	public List<Application> getCandidateApplications() {
		return candidateApplications;
	}

	public void setCandidateApplications(List<Application> candidateApplications) {
		this.candidateApplications = candidateApplications;
	}

	public long getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(long candidate_id) {
		this.candidate_id = candidate_id;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}
	
	public void sendEmail(String email, String status) {
		// Recipient's email ID needs to be mentioned.
	      String to = email;

	      // Sender's email ID needs to be mentioned
	      String from = "ekremozturk22@gmail.com";

	      // Assuming you are sending email from localhost
	      String host = "0.0.0.0";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Status change on your application");

	         // Now set the actual message
	         message.setText(status);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	

}
