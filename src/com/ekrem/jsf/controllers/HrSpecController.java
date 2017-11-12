/**
 * 
 */
package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.auth.LDAPcon;
import com.ekrem.jsf.db.HrSpecDAO;
import com.ekrem.jsf.models.HrSpec;

/**
 * @author ekrem
 *
 */
@ManagedBean (name = "hrSpecController")
@SessionScoped
public class HrSpecController {
	
	private HrSpecDAO hrSpecDAO;
	private List<HrSpec> hrSpecs;
	
	
	public HrSpecController() throws Exception {
		hrSpecs = new ArrayList<>();
		hrSpecDAO = HrSpecDAO.getHrSpecInstance();
	}
	
	/**
	 * Loads hrSpecs
	 */
	public void loadHrSpecs() {
		
		hrSpecs.clear();

		try {
			hrSpecs = hrSpecDAO.getHrSpecs();
		} catch (Exception exc) {
			
		}
	}
	
	public List<HrSpec> getHrSpecs() {
		return hrSpecs;
	}

	public void setHrSpecs(List<HrSpec> hrSpecs) {
		this.hrSpecs = hrSpecs;
	}

	public String addHrSpec(HrSpec hrSpec) {
		
		try {
			hrSpecDAO.addHrSpec(hrSpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
		
	}
	
	public String updateHrSpec(HrSpec hrSpec) {
		
		try {
			hrSpecDAO.updateHrSpec(hrSpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public String deleteHrSpec(long id) {
		
		try {
			hrSpecDAO.deleteHrSpec(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public long getId(String company, String password) {
		long id = -1;
		try {
			
			LDAPcon con = new LDAPcon();
			
			if(con.Authenticate(company, password))
				id = hrSpecDAO.checkPassword(company, password);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public HrSpec getHrSpec(long id) {
		HrSpec hrSpec = new HrSpec();
		try {
			hrSpec = hrSpecDAO.getHrSpec(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hrSpec;
		
	}

}
