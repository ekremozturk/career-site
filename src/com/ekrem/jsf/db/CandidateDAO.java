/**
 * 
 */
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

import com.ekrem.jsf.models.Candidate;

/**
 * @author ekrem
 *
 */
public class CandidateDAO {
	
	private static CandidateDAO candidateInstance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/job_app_db";
	
	public static CandidateDAO getCandidateInstance() throws Exception {
		if(candidateInstance == null) candidateInstance = new CandidateDAO();
		return candidateInstance;
	}

	public CandidateDAO() throws Exception {
		dataSource = getDataSource();
	}
	

	public static void setCandidateInstance(CandidateDAO candidateInstance) {
		CandidateDAO.candidateInstance = candidateInstance;
	}



	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource dataSource = (DataSource) context.lookup(jndiName);
		
		return dataSource;
	}



	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Candidate> getCandidates() throws Exception{
		List<Candidate> candidates = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = dataSource.getConnection();

			String sql = "select * from candidate";

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				long id = myRs.getInt("id");
				String name = myRs.getString("name");
				String surname = myRs.getString("surname");
				String headline = myRs.getString("headline");
				String link_id = myRs.getString("link_id");
				String skills = myRs.getString("skills");
			
				Candidate candidate = new Candidate(id, name, surname,
						headline, link_id, skills);

		
				candidates.add(candidate);
			}
			
			return candidates;		
		}
		finally {
			myConn.close();
			myStmt.close();
			myRs.close();
		}
	}

	public void addCandidate(Candidate candidate) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "insert into candidate (name, surname, headline, link_id, skills) values (?, ?, ?, ?, ?)";

			stmt = con.prepareStatement(sql);

			stmt.setString(1, candidate.getName());
			stmt.setString(2, candidate.getSurname());
			stmt.setString(3, candidate.getHeadline());
			stmt.setString(4, candidate.getLink_id());
			stmt.setString(5, candidate.getSkills());
			
			stmt.execute();			
		}
		finally {
			con.close();
			stmt.close();
		}
	}
	
	public Candidate getCandidate(long id) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "select * from candidate where id=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();

			Candidate candidate;
			
			// retrieve data from result set row
			if (rs.next()) {
			
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String headline = rs.getString("headline");
				String link_id = rs.getString("link_id");
				String skills = rs.getString("skills");

				candidate = new Candidate(id, name, surname,
						headline, link_id, skills);

			}
			else {
				throw new Exception("Could not find the candidate");
			}

			return candidate;
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}

	}
	
	public void updateCandidate(Candidate candidate) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "update candidate "
						+ " set name=?, surname=?, headline=?, skills=?"
						+ " where id=?";

			stmt = con.prepareStatement(sql);

			// set params
			stmt.setString(1, candidate.getName());
			stmt.setString(2, candidate.getSurname());
			stmt.setString(3, candidate.getHeadline());
			stmt.setString(4, candidate.getSkills());
			
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}
		
	}
	
	public void deleteCandidate(long id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "delete from candidate where id=?";

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
	
	public Candidate getCandidate(String link_id) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "select * from candidate where link_id=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setString(1, link_id);
			
			rs = stmt.executeQuery();

			Candidate candidate;
			
			// retrieve data from result set row
			if (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String headline = rs.getString("headline");
				String skills = rs.getString("skills");

				candidate = new Candidate(id, name, surname,
						headline, link_id, skills);

			}
			else {
				throw new Exception("Could not find the candidate");
			}

			return candidate;
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}

	}


}
