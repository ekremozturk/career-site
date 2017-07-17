package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.db.AdvertDAO;
import com.ekrem.jsf.db.ApplicationDAO;
import com.ekrem.jsf.models.Advert;
import com.ekrem.jsf.models.Application;

@ManagedBean (name = "advertController")
@SessionScoped
public class AdvertController {

	private AdvertDAO advertDAO;
	private ApplicationDAO applicationDAO;
	
	private List<Advert> adverts;
	
	private List<Advert> activeAdverts;
	
	private Advert theAdvert;
	
	private Date date = new Date();
	
	private long hr_id;
	
	private List<Advert> companyAdverts;
	
	public AdvertController() throws Exception {
		theAdvert = new Advert();
		adverts = new ArrayList<>();
		companyAdverts = new ArrayList<>();
		activeAdverts = new ArrayList<>();
		advertDAO = AdvertDAO.getAdvertInstance();
		applicationDAO = ApplicationDAO.getApplicationInstance();
	}
	
	/**
	 * Loads adverts
	 */
	public void loadAdverts() {
		
		adverts.clear();
		activeAdverts.clear();
		companyAdverts.clear();

		try {
			
			adverts = advertDAO.getAdverts();
			
			for(Advert a:adverts) 
				if(a.getActive()) 
					activeAdverts.add(a);
			
			for(Advert a:adverts)
				if(a.getHr_id() == hr_id)
					companyAdverts.add(a);
			
		} catch (Exception exc) {
			
		}
	}
	
	public void loadTheAdvert(long id) {
		
		theAdvert=new Advert();
		
		try {
			System.out.println(id);
			theAdvert = advertDAO.getAdvert(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Advert> getAdverts() {
		return adverts;
	}
	

	public void setAdverts(List<Advert> ads) {
		this.adverts = ads;
	}

	public Advert getTheAdvert() {
		return theAdvert;
	}

	public void setTheAdvert(Advert theAdvert) {
		this.theAdvert = theAdvert;
	}

	public List<Advert> getActiveAdverts() {
		return activeAdverts;
	}

	public void setActiveAdverts(List<Advert> activeAdverts) {
		this.activeAdverts = activeAdverts;
	}

	public String addAdvert(Advert advert) {
		
		advert.setHr_id(hr_id);
		advert.setCode("JA" + hr_id*100 + companyAdverts.size());
		advert.setOpen_time(date);
		
		try {
			advertDAO.addAdvert(advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
		
	}
	
	public String updateAdvert(Advert advert) {
		advert.setId(theAdvert.getId());
		advert.setHr_id(theAdvert.getHr_id());
		advert.setCode(theAdvert.getCode());
		advert.setOpen_time(theAdvert.getOpen_time());
		try {
			advertDAO.updateAdvert(advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public String deleteAdvert(long id) {
		
		try {
			ApplicationController applicationController = new ApplicationController();
			for(Application a :applicationController.getApplications())
				if (a.getAdvert_id() == id)
					applicationDAO.deleteApplication(a.getId());
			advertDAO.deleteAdvert(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public Advert getAdvert(long id) {
		try {
			return advertDAO.getAdvert(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Advert();
		}
		
		
	}
	
	public void getId(long hr_id) {
		this.hr_id = hr_id;
	}

	public long getHr_id() {
		return hr_id;
	}

	public void setHr_id(long hr_id) {
		this.hr_id = hr_id;
	}

	public List<Advert> getCompanyAdverts() {
		return companyAdverts;
	}

	public void setCompanyAdverts(List<Advert> companyAdverts) {
		this.companyAdverts = companyAdverts;
	}
}
