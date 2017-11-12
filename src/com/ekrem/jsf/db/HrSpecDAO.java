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

import com.ekrem.jsf.models.HrSpec;

/**
 * @author ekrem
 *
 */
public class HrSpecDAO {
	
	private static HrSpecDAO hrSpecInstance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/job_app_db";
	
	public static HrSpecDAO getHrSpecInstance() throws Exception {
		if(hrSpecInstance == null) hrSpecInstance = new HrSpecDAO();
		return hrSpecInstance;
	}

	public HrSpecDAO() throws Exception {
		dataSource = getDataSource();
	}
	

	public static void setHrSpecInstance(HrSpecDAO hrSpecInstance) {
		HrSpecDAO.hrSpecInstance = hrSpecInstance;
	}



	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource dataSource = (DataSource) context.lookup(jndiName);
		
		return dataSource;
	}



	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<HrSpec> getHrSpecs() throws Exception{
		List<HrSpec> hrSpecs = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();

			String sql = "select * from hrSpec";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				long id = myRs.getInt("id");
				String company = myRs.getString("company");
				String password = myRs.getString("password");
				
			
				HrSpec hrSpec = new HrSpec(id, company, password);

		
				hrSpecs.add(hrSpec);
			}
			
			return hrSpecs;		
		}
		finally {
			myConn.close();
			myStmt.close();
			myRs.close();
		}
	}

	public void addHrSpec(HrSpec hrSpec) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "insert into hrSpec (company, password) values (?, ?)";

			stmt = con.prepareStatement(sql);

			stmt.setString(1, hrSpec.getCompany());
			stmt.setString(2, hrSpec.getPassword());
			
			stmt.execute();			
		}
		finally {
			con.close();
			stmt.close();
		}
	}
	
	public HrSpec getHrSpec(long id) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "select * from hr_specialist where id=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();

			HrSpec hrSpec;
			
			// retrieve data from result set row
			if (rs.next()) {
			
				String company = rs.getString("company");
				String password = rs.getString("password");
			

				hrSpec = new HrSpec(id, company, password);

			}
			else {
				throw new Exception("Could not find the HrSpec");
			}

			return hrSpec;
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}

	}
	
	public void updateHrSpec(HrSpec hrSpec) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "update hr_specialist "
						+ " set company=?, password=?"
						+ " where id=?";

			stmt = con.prepareStatement(sql);

			// set params
			stmt.setString(1, hrSpec.getCompany());
			stmt.setString(2, hrSpec.getPassword());
			
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}
		
	}
	
	public void deleteHrSpec(long id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "delete from hr_specialist where id=?";

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
	
	public long checkPassword(String company, String password) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select * from hr_specialist where company=? and password=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setString(1, company);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			
			long id = -1;
			
			// retrieve data from result set row
			if (rs.next()) {
				
				id = rs.getInt("id");
				
			}else {
				throw new Exception("Could not find the HrSpec");
			}

			return id;
			
		} 
		finally {
			con.close();
			stmt.close();
			rs.close();
		}
	}


}
