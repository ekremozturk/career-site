package com.ekrem.jsf.models;

import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean (name = "app")
public class Application {
	
	private long id;
	private long hr_id;
	private long advert_id;
	private long candidate_id;
	private Date apply_date;
	private String cover_letter;
	private String status;
	
	public Application() {
		

	}
	
	public Application( long hr_id, long advert_id, long candidate_id, Date apply_date, String cover_letter, String status) {
		super();
		this.hr_id = hr_id;
		this.advert_id = advert_id;
		this.candidate_id = candidate_id;
		this.apply_date = apply_date;
		this.cover_letter = cover_letter;
		this.status = status;
	}

	public Application(long id, long hr_id, long advert_id, long candidate_id, Date apply_date, String cover_letter, String status) {
		super();
		this.id = id;
		this.hr_id = hr_id;
		this.advert_id = advert_id;
		this.candidate_id = candidate_id;
		this.apply_date = apply_date;
		this.cover_letter = cover_letter;
		this.status = status;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHr_id() {
		return hr_id;
	}

	public void setHr_id(long hr_id) {
		this.hr_id = hr_id;
	}

	public long getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(long advert_id) {
		this.advert_id = advert_id;
	}

	public long getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(long candidate_id) {
		this.candidate_id = candidate_id;
	}



	public Date getApply_date() {
		return apply_date;
	}



	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}



	public String getCover_letter() {
		return cover_letter;
	}



	public void setCover_letter(String cover_letter) {
		this.cover_letter = cover_letter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
