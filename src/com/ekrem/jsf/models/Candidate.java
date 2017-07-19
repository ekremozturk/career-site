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
public class Candidate{
	private long id;
	private String name;
	private String surname;
	private String headline;
	private String link_id;
	private String skills;
	private String email;
	private String pictureUrl;
	private String summary;
	private String numConnections;
	private int blacklist;
	
	public Candidate() {
		blacklist=0;
	}

	

	public Candidate(String name, String surname, String headline, String link_id, String skills, String email,
			String pictureUrl, String summary, String numConnections, int blacklist) {
		super();
		this.name = name;
		this.surname = surname;
		this.headline = headline;
		this.link_id = link_id;
		this.skills = skills;
		this.email = email;
		this.pictureUrl = pictureUrl;
		this.summary = summary;
		this.numConnections = numConnections;
		this.blacklist = blacklist;
	}



	public Candidate(long id, String name, String surname, String headline, String link_id, String skills, String email,
			String pictureUrl, String summary, String numConnections, int blacklist) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.headline = headline;
		this.link_id = link_id;
		this.skills = skills;
		this.email = email;
		this.pictureUrl = pictureUrl;
		this.summary = summary;
		this.numConnections = numConnections;
		this.blacklist = blacklist;
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




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getNumConnections() {
		return numConnections;
	}




	public void setNumConnections(String numConnections) {
		this.numConnections = numConnections;
	}




	public String getPictureUrl() {
		return pictureUrl;
	}




	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}




	public String getSummary() {
		return summary;
	}




	public void setSummary(String summary) {
		this.summary = summary;
	}




	public int getBlacklist() {
		return blacklist;
	}




	public void setBlacklist(int blacklist) {
		this.blacklist = blacklist;
	}
	
	public String isBlacklisted() {
		if(blacklist==1)
			return "Blacklisted";
		return "Normal";
	}
	
}
