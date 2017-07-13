package com.ekrem.jsf.jdbc;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;

@ManagedBean(name = "auth")
@SessionScoped
public class LinkedInAuth {

    public LinkedInAuth() {
    	
    }

    public String main() throws IOException, InterruptedException, ExecutionException {
        // Replace these with your client id and secret
        final String clientId = "86kc1q0ipcxk03";
        final String clientSecret = "dv4nbCXq5BWvswJF";
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .scope("r_basicprofile r_emailaddress") // replace with desired scope
                .callback("http://localhost:8080/SummerOBSS/faces/home_candidate.xhtml")
                .state("DCEeFWf45A53sdfKeK424")
                .build(LinkedInAPI.instance());

        final String authorizationUrl = service.getAuthorizationUrl();
        System.out.println(authorizationUrl);
        
		return authorizationUrl;
    }
}
