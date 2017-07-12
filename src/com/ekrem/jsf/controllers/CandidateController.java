/**
 * 
 */
package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.db.CandidateDAO;
import com.ekrem.jsf.models.Candidate;

/**
 * @author ekrem
 *
 */
@ManagedBean
@SessionScoped
public class CandidateController {
	
	private CandidateDAO candidateDAO;
	//MAKE CANDİDATES SESSİON LONG
	private List<Candidate> candidates;
	
	
	public CandidateController() throws Exception {
		candidates = new ArrayList<>();
		candidateDAO = CandidateDAO.getCandidateInstance();
	}
	
	/**
	 * Loads candidates
	 */
	public void loadCandidates() {
		
		candidates.clear();

		try {
			candidates = candidateDAO.getCandidates();
		} catch (Exception exc) {
			
		}
	}
	
	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public String addCandidate(Candidate candidate) {
		
		candidate.setSkills("java, c++");
		
		try {
			candidateDAO.addCandidate(candidate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
		
	}
	
	public String updateCandidate(Candidate candidate) {
		
		try {
			candidateDAO.updateCandidate(candidate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}
	
	public String deleteCandidate(long id) {
		
		try {
			candidateDAO.deleteCandidate(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "home?faces-redirect=true";
	}


}
