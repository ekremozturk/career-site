package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.db.AdvertDAO;
import com.ekrem.jsf.models.Advert;

@ManagedBean (name = "advertController")
@SessionScoped
public class AdvertController {

	private AdvertDAO advertDAO;
	
	private List<Advert> adverts;
	
	private List<Advert> activeAdverts;
	
	private Advert theAdvert;
	
	private Date date = new Date();
	
	public AdvertController() throws Exception {
		theAdvert = new Advert();
		adverts = new ArrayList<>();
		activeAdverts = new ArrayList<>();
		advertDAO = AdvertDAO.getAdvertInstance();
	}
	
	/**
	 * Loads adverts
	 */
	public void loadAdverts() {
		
		adverts.clear();
		activeAdverts.clear();
		theAdvert=new Advert();

		try {
			
			adverts = advertDAO.getAdverts();
			
			for(Advert a:adverts) 
				if(a.getActive()) 
					activeAdverts.add(a);
			
		} catch (Exception exc) {
			
		}
	}
	
	public void loadTheAdvert(long id) {
		
		theAdvert=new Advert();
		
		try {
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
		
		advert.setHr_id(1);
		advert.setCode("JA" + advert.getHr_id());
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
}
