package com.ekrem.jsf.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ekrem.jsf.models.Application;

public class ApplicationDAO {
	
	private static ApplicationDAO applicationInstance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/job_app_db";
	
	public static ApplicationDAO getApplicationInstance() throws Exception {
		if(applicationInstance == null) applicationInstance = new ApplicationDAO();
		return applicationInstance;
	}

	public ApplicationDAO() throws Exception {
		dataSource = getDataSource();
	}
	

	public static void setApplicationInstance(ApplicationDAO applicationInstance) {
		ApplicationDAO.applicationInstance = applicationInstance;
	}



	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource dataSource = (DataSource) context.lookup(jndiName);
		
		return dataSource;
	}



	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Application> getApplications() throws Exception{
		List<Application> applications = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();

			String sql = "select * from application";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				long id = myRs.getInt("id");
				long hr_id = myRs.getInt("hr_id");
				long advert_id = myRs.getInt("advert_id");
				long candidate_id = myRs.getInt("candidate_id");
				
			
				Application application = new Application(id, hr_id, advert_id, candidate_id);

		
				applications.add(application);
			}
			
			return applications;		
		}
		finally {
			myConn.close();
			myStmt.close();
			myRs.close();
		}
	}

	public void addApplication(Application application) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "insert into application (hr_id, advert_id, candidate_id) values (?, ?, ?)";

			stmt = con.prepareStatement(sql);

			stmt.setLong(1, application.getHr_id());
			stmt.setLong(2, application.getAdvert_id());
			stmt.setLong(3, application.getCandidate_id());
			
			stmt.execute();			
		}
		finally {
			con.close();
			stmt.close();
		}
	}
	
	public Application getApplication(long id) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "select * from application where id=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();

			Application application;
			
			// retrieve data from result set row
			if (rs.next()) {
			
				long hr_id = rs.getInt("hr_id");
				long advert_id = rs.getInt("advert_id");
				long candidate_id = rs.getInt("candidate_id");
			

				application = new Application(id, hr_id, advert_id,
						candidate_id);

			}
			else {
				throw new Exception("Could not find the Application");
			}

			return application;
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}

	}
	
	public void updateApplication(Application application) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "update application "
						+ " set hr_id=?, advert_id=?, candidate_id=?"
						+ " where id=?";

			stmt = con.prepareStatement(sql);

			// set params
			stmt.setLong(1, application.getHr_id());
			stmt.setLong(2, application.getAdvert_id());
			stmt.setLong(3, application.getCandidate_id());
			
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}
		
	}
	
	public void deleteApplication(long id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "delete from application where id=?";

			stmt = con.prepareStatement(sql);

			// set params
			stmt.setLong(1, id);
			
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}		
	}


}
