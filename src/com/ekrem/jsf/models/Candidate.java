/**
 * 
 */
package com.ekrem.jsf.models;

import javax.faces.bean.ManagedBean;

/**
 * @author ekrem
 *
 */
@ManagedBean
public class Candidate {
	private long id;
	private String name;
	private String surname;
	private String headline;
	private String link_id;
	private String skills;
	
	public Candidate() {
		
	}
	

	
	
	public Candidate(long id, String name, String surname, String headline, String link_id, String skills) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.headline = headline;
		this.link_id = link_id;
		this.skills = skills;
	}




	public Candidate(String name, String surname, String headline, String link_id, String skills) {
		super();
		this.name = name;
		this.surname = surname;
		this.headline = headline;
		this.link_id = link_id;
		this.skills = skills;
	}



	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getHeadline() {
		return headline;
	}


	public void setHeadline(String headline) {
		this.headline = headline;
	}


	public String getLink_id() {
		return link_id;
	}


	public void setLink_id(String link_id) {
		this.link_id = link_id;
	}


	public String getSkills() {
		return skills;
	}


	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	
	
}
