package com.ekrem.jsf.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.ekrem.jsf.controllers.CandidateController;
import com.ekrem.jsf.models.Candidate;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

@ManagedBean (name = "access")
@SessionScoped
public class LinkedInAccess {
	
	String query = "";
	private String code;
	private String id = "no-login";
	private static final String PROTECTED_RESOURCE_URL = "https://api.linkedin.com/v1/people/~:(id,headline,picture-url,specialties,summary,first-name,last-name,email-address)?format=json";
	
	
	public LinkedInAccess() {
	}
	
	public void main() throws IOException, InterruptedException, ExecutionException{
		final String clientId = "86kc1q0ipcxk03";
        final String clientSecret = "dv4nbCXq5BWvswJF";
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .scope("r_basicprofile r_emailaddress") // replace with desired scope
                .callback("http://localhost:8080/SummerOBSS/faces/Candidate/access.xhtml")
                .state("DCEeFWf45A53sdfKeK424")
                .build(LinkedInAPI.instance());
		
		final OAuth2AccessToken accessToken;
		
		if(getAccessCode() != null) {
		accessToken = service.getAccessToken(getAccessCode());
		
		final OAuthRequest request = new OAuthRequest(Verb.GET, String.format(PROTECTED_RESOURCE_URL, query));
		request.addHeader("x-li-format", "json");
        request.addHeader("Accept-Language", "ru-RU");
        service.signRequest(accessToken, request);
        System.out.println(request);
        final Response response = service.execute(request);
        ArrayList<String> properties = JSONParser(response.getBody());
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        
        Candidate candidate = new Candidate();
        
        candidate.setEmail(properties.get(0));
        candidate.setName(properties.get(1));
        candidate.setHeadline(properties.get(2));
        candidate.setLink_id(properties.get(3));
        candidate.setSurname(properties.get(4));
        candidate.setPictureUrl(properties.get(5));
        candidate.setSummary(properties.get(6));
        candidate.setNumConnections("192");
        candidate.setBlacklist(0);
        
        id = properties.get(3);
        
        try {
			CandidateController cCont = new CandidateController();
			cCont.addCandidate(candidate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		}
	}
	
	public String getAccessCode() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getQueryString() != null) {
		code = request.getQueryString();
		int codeIndex = code.indexOf("&");
		code = code.substring(5, codeIndex);
		}
		System.out.println(code);
		return code;
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public ArrayList<String> JSONParser(String body) {
		ArrayList<String> properties = new ArrayList<>();
		for(int i=0; i<6; i++) {
			int begin = body.indexOf(":");
			body = body.substring(begin+2);
			int end = body.indexOf(",");
			properties.add(body.substring(1, end-1));
			body = body.substring(end);
		}
		
		int begin = body.indexOf(":");
		body = body.substring(begin+2);
		int end = body.indexOf("}");
		properties.add(body.substring(1, end-1));
		
		return properties;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void redirect() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String logout() {
		query = ""; code = ""; id = "no-login";
		return "/sign_in?faces-redirect=true";
	}

}
