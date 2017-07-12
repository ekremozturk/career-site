/**
 * 
 */
package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import com.ekrem.jsf.db.HrSpecDAO;
import com.ekrem.jsf.models.HrSpec;

/**
 * @author ekrem
 *
 */
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

}
