package com.ekrem.jsf.auth;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPcon {
	
	private String url = "ldap://localhost:10389";
	
	public LDAPcon() {
		
	}
	
	public boolean Authenticate(String cn, String credentials) {
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn="+cn+",ou=users,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, credentials);
		
		try {
		    DirContext ctx = new InitialDirContext(env);
		    System.out.println("connected");
		    //System.out.println(ctx.getEnvironment());
		 
		    ctx.close();
		    
		    return true;
		 
		} catch (AuthenticationNotSupportedException ex) {
		    System.out.println("The authentication is not supported by the server");
		    return false;
		} catch (AuthenticationException ex) {
		    System.out.println("incorrect password or username");
		    return false;
		} catch (NamingException ex) {
		    System.out.println("error when trying to create the context");
		    return false;
		}
	}

}
