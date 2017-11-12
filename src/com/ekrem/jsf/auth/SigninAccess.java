package com.ekrem.jsf.auth;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ekrem.jsf.controllers.HrSpecController;

@ManagedBean (name= "hraccess")
@SessionScoped
public class SigninAccess {
	
	private long id=-1;
	private boolean accessed = false;
	
	public SigninAccess() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isAccessed() {
		return accessed;
	}

	public void setAccessed(boolean accessed) {
		this.accessed = accessed;
	}

	public String SignIn(String company, String password) throws Exception {
		
		HrSpecController hrSpecController = new HrSpecController();
		
		id = hrSpecController.getId(company, password);
		
		if(id != -1) {
			accessed = true;
			return "HR/home?faces-redirect=true";
		}
			
		else 
			return "/sign_in?faces-redirect=true";
	}
	
	public String logout() {
		id = -1;
		accessed = false;
		return "/sign_in?faces-redirect=true";
	}
	
	public void checkAuth() {
		if(id==-1) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("no-auth.xhtml");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		
	}

}
