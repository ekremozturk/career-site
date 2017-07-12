package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import com.ekrem.jsf.db.ApplicationDAO;
import com.ekrem.jsf.models.Application;

public class ApplicationController {
	
	private ApplicationDAO applicationDAO;
	private List<Application> applications;
	
	
	public ApplicationController() throws Exception {
		applications = new ArrayList<>();
		applicationDAO = ApplicationDAO.getApplicationInstance();
	}
	
	/**
	 * Loads applications
	 */
	public void loadApplications() {
		
		applications.clear();

		try {
			applications = applicationDAO.getApplications();
		} catch (Exception exc) {
			
		}
	}
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public String addApplication(Application application) {
		
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


}
