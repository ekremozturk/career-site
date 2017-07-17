/**
 * 
 */
package com.ekrem.jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ekrem.jsf.db.ApplicationDAO;
import com.ekrem.jsf.db.CandidateDAO;
import com.ekrem.jsf.models.Candidate;

/**
 * @author ekrem
 *
 */
@ManagedBean (name="candidateController")
@SessionScoped
public class CandidateController {
	
	private CandidateDAO candidateDAO;
	
	private List<Candidate> candidates;
	
	private Candidate theCandidate;
	
	private String blacklist;
	
	private ApplicationDAO applicationDAO;
	
	public CandidateController() throws Exception {
		candidates = new ArrayList<>();
		candidateDAO = CandidateDAO.getCandidateInstance();
		applicationDAO = ApplicationDAO.getApplicationInstance();
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
	
	public Candidate getCandidate(String link_id) {
		Candidate candidate = null;
		try {
			candidate = candidateDAO.getCandidate(link_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return candidate;
	}
	
	public Candidate getCandidate(long id) {
		Candidate candidate = null;
		try {
			candidate = candidateDAO.getCandidate(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return candidate;
	}
	
	public void loadTheCandidate(long id) {
		
		theCandidate=new Candidate();
		
		try {
			System.out.println(id);
			theCandidate = candidateDAO.getCandidate(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Candidate getTheCandidate() {
		return theCandidate;
	}

	public void setTheCandidate(Candidate theCandidate) {
		this.theCandidate = theCandidate;
	}
	
	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public String blacklist(Candidate candidate) {
		if(blacklist.equals("Yes")) {
			
			candidate.setBlacklist(1);
			try {
				applicationDAO.dropApplications(candidate.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else candidate.setBlacklist(0);
		updateCandidate(candidate);
		
		
		
		return "applications?faces-redirect=true";
	}

}
