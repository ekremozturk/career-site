package com.ekrem.jsf.models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Application {
	private long id;
	private long hr_id;
	private long advert_id;
	private long candidate_id;
	
	public Application() {
		

	}
	
	

	public Application(long id, long hr_id, long advert_id, long candidate_id) {
		super();
		this.id = id;
		this.hr_id = hr_id;
		this.advert_id = advert_id;
		this.candidate_id = candidate_id;
	}



	public Application(long hr_id, long advert_id, long candidate_id) {
		super();
		this.hr_id = hr_id;
		this.advert_id = advert_id;
		this.candidate_id = candidate_id;
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
	

}
