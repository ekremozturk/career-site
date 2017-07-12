/**
 * 
 */
package com.ekrem.jsf.models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author ekrem
 *
 */
@ManagedBean
@SessionScoped
public class HrSpec {
	private long id;
	private String company;
	private String password;

	public HrSpec() {
		
	}
	
	public HrSpec(String company) {
		super();
		this.company = company;
	}
	
	

	public HrSpec(long id, String company, String password) {
		super();
		this.id = id;
		this.company = company;
		this.password = password;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
