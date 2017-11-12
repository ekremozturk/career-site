package com.ekrem.jsf.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
	
	private SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");;
	
	private long hr_id;
	
	private List<Advert> companyAdverts;
	
	private String advertFilter = "All";
	
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
			
			
			for(Advert a:adverts) {
				if(timeTrigger(a)) {
					if(a.getActive()){
						a.setActive(false);
						updateStatus(a);
					} 
					
					else {
						a.setActive(true);
						updateStatus(a);
					}
				}
					
			}
				
			
			for(Advert a:adverts) {
				if(isTimePassed(a)) {
					a.setActive(false);
					updateStatus(a);
				} 
					
			}
				
			
			
			for(Advert a:adverts) 
				if(a.getActive()) 
					activeAdverts.add(a);
			
			for(Advert a:adverts)
				if(a.getHr_id() == hr_id && advertFilter.equals("All")) 
					companyAdverts.add(a);
				else if(a.getHr_id() == hr_id && advertFilter.equals("Active") && a.getActive()) 
					companyAdverts.add(a);
				else if(a.getHr_id() == hr_id && advertFilter.equals("Passive") && !a.getActive()) 
					companyAdverts.add(a);
					
			
		} catch (Exception exc) {
			
		}
	}
	
	public void updateStatus(Advert advert) {
		try {
			advertDAO.updateStatus(advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public String loadAdvert(long id) {
		Advert advert;
		try {
			advert = advertDAO.getAdvert(id);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("advert", advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "edit_advert";
		
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
		if(advert.getId()==0 && theAdvert.getId() != 0) {
			advert.setId(theAdvert.getId());
			advert.setHr_id(theAdvert.getHr_id());
			advert.setCode(theAdvert.getCode());
			advert.setOpen_time(theAdvert.getOpen_time());
		}
		
		//System.out.println(advert.getId());
		
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
			applicationController.loadApplications();
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
	
	public boolean isTimePassed(Advert a) {
		String currentDate = format.format(this.date);
		
		if(getYear(currentDate)<=getYear(a.getClose_time())) 
			if(getMonth(currentDate)<=getMonth(a.getClose_time())) 
				if(getDay(currentDate)<getDay(a.getClose_time())) 
					return true;	
		
		return false;
	}
	
	public boolean timeTrigger(Advert a) {
		String currentDate = format.format(this.date);
		
		if(getYear(currentDate)<=getYear(a.getClose_time())) 
			if(getMonth(currentDate)<=getMonth(a.getClose_time())) 
				if(getDay(currentDate)<getDay(a.getClose_time())) 
					return true;
		
		return false;
	}
	
	public int getYear(String date) {
		String year = date.substring(0, 4);
		return Integer.parseInt(year);
	}
	
	public int getMonth(String date) {
		String month = date.substring(5, 7);
		return Integer.parseInt(month);
	}
	
	public int getDay(String date) {
		String day = date.substring(8, 10);
		return Integer.parseInt(day);
	}
	
	public void loadTheAdd(Advert advert) {
		theAdvert = advert;
	}

	public String getAdvertFilter() {
		return advertFilter;
	}

	public void setAdvertFilter(String advertFilter) {
		this.advertFilter = advertFilter;
	}
	
	public void filterResults() {
		loadAdverts();
	}
	
}
