package com.ekrem.jsf.jdbc;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.controllers.HrSpecController;

@ManagedBean (name= "hraccess")
@SessionScoped
public class SigninAccess {
	
	private long id=-1;
	
	public SigninAccess() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String SignIn(String company, String password) throws Exception {
		
		HrSpecController hrSpecController = new HrSpecController();
		
		id = hrSpecController.getId(company, password);
		
		if(id != -1) 
			return "HR/home?faces-redirect=true";
		else 
			return "/sign_in?faces-redirect=true";
	}
	
	public String logout() {
		id = -1;
		return "/sign_in?faces-redirect=true";
	}

}
