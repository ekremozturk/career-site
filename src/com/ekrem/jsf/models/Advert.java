/**
 * 
 */
package com.ekrem.jsf.models;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.convert.FacesConverter;

/**
 * @author ekrem
 *
 */
@ManagedBean (name = "advert")
@FacesConverter(forClass=Advert.class)
public class Advert {
	
	private long id;
	private String code;
	private String head;
	private String description;
	private String req_skills;
	private Date open_time;
	private String close_time;
	private long hr_id;
	private boolean active;
	private String act_deactTime;
	
	public Advert() {
		
	}

	public Advert(String head, String description, String req_skills) {
		super();
		this.head = head;
		this.description = description;
		this.req_skills = req_skills;
	}




	public Advert(long hr_id, String code, String head, String description, String req_skills, Date open_time,
			String close_time, boolean active, String act_deactTime) {
		super();
		this.hr_id = hr_id;
		this.code = code;
		this.head = head;
		this.description = description;
		this.req_skills = req_skills;
		this.open_time = open_time;
		this.close_time = close_time;
		this.active = active;
		this.act_deactTime=act_deactTime;
	}



	public Advert(long id, String code, String head, String description, String req_skills, Date open_time,
			String close_time, long hr_id, boolean active, String act_deactTime) {
		super();
		this.id = id;
		this.code = code;
		this.head = head;
		this.description = description;
		this.req_skills = req_skills;
		this.open_time = open_time;
		this.close_time = close_time;
		this.hr_id = hr_id;
		this.active = active;
		this.act_deactTime=act_deactTime;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReq_skills() {
		return req_skills;
	}

	public void setReq_skills(String req_skills) {
		this.req_skills = req_skills;
	}

	public Date getOpen_time() {
		return open_time;
	}

	public void setOpen_time(Date open_time) {
		this.open_time = open_time;
	}

	public String getClose_time() {
		return close_time;
	}

	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAct_deactTime() {
		return act_deactTime;
	}

	public void setAct_deactTime(String act_deactTime) {
		this.act_deactTime = act_deactTime;
	}
	
	public String Status() {
		if(active) return "Active";
		else return "Passive";
	}

}
