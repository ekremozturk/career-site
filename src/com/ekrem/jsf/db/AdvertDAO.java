/**
 * 
 */
package com.ekrem.jsf.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ekrem.jsf.models.Advert;

/**
 * @author ekrem
 *
 */
public class AdvertDAO {
	
	private static AdvertDAO advertInstance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/job_app_db";
	private SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
	
	
	public static AdvertDAO getAdvertInstance() throws Exception {
		if(advertInstance == null) advertInstance = new AdvertDAO();
		return advertInstance;
	}

	public AdvertDAO() throws Exception {
		dataSource = getDataSource();
	}

	public static void setAdvertInstance(AdvertDAO advertInstance) {
		AdvertDAO.advertInstance = advertInstance;
	}

	public DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource dataSource = (DataSource) context.lookup(jndiName);
		
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Advert> getAdverts() throws Exception{
		List<Advert> ads = new ArrayList<>();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			con = dataSource.getConnection();

			String sql = "select * from job_advert";

			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);

			// process result set
			while (rs.next()) {
				
				// retrieve data from result set row
				long id = rs.getInt("id");
				String code = rs.getString("code");
				String head = rs.getString("head");
				String description = rs.getString("description");
				String skills = rs.getString("skills");
				Date open_time = rs.getDate("open_time");
				String close_time = rs.getString("close_time");
				long hr_id = rs.getInt("hr_id");
				int active_i = rs.getInt("active");
				boolean active=false;
				if(active_i == 1) active = true;
				String act_deactTime = rs.getString("act_deact_time");
				Advert ad = new Advert(id, code, head,
						description, skills, open_time, close_time, hr_id, active, act_deactTime);

		
				ads.add(ad);
			}
			
			return ads;		
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}
	}
	
	public void addAdvert(Advert advert) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "insert into job_advert (code, head, description, skills, open_time, close_time, hr_id, active, act_deact_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = con.prepareStatement(sql);

			stmt.setString(1, advert.getCode());
			stmt.setString(2, advert.getHead());
			stmt.setString(3, advert.getDescription());
			stmt.setString(4, advert.getReq_skills());
			stmt.setString(5, format.format(advert.getOpen_time()));
			stmt.setString(6, advert.getClose_time());
			stmt.setString(7, advert.getHr_id()+"");
			boolean active=advert.getActive();
			if(active) stmt.setInt(8, 1);
			else stmt.setInt(8, 0);
			stmt.setString(9, advert.getAct_deactTime());
			
			stmt.execute();			
		}
		finally {
			con.close();
			stmt.close();
		}
	}
	
	public Advert getAdvert(long id) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "select * from job_advert where id=?";

			stmt = con.prepareStatement(sql);
			
			// set params
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();

			Advert advert;
			
			// retrieve data from result set row
			if (rs.next()) {
				long theId = rs.getInt("id");
				String code = rs.getString("code");
				String head = rs.getString("head");
				String description = rs.getString("description");
				String skills = rs.getString("skills");
				Date open_time = rs.getDate("open_time");
				String close_time = rs.getString("close_time");
				long hr_id = rs.getInt("hr_id");
			
				int active_i = rs.getInt("active");
				boolean active=false;
				if(active_i == 1) active = true;
				
				String act_deactTime = rs.getString("act_deact_time");
				
				advert = new Advert(theId, code, head,
						description, skills, open_time, close_time, hr_id, active, act_deactTime);

			}
			else {
				throw new Exception("Could not find the advert");
			}

			return advert;
		}
		finally {
			con.close();
			stmt.close();
			rs.close();
		}

	}
	
	public void updateAdvert(Advert advert) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "update job_advert "
						+ " set head=?, description=?, skills=?, close_time=?, active=?, act_deact_time=?"
						+ " where id=?";

			stmt = con.prepareStatement(sql);

			// set params
			stmt.setString(1, advert.getHead());
			stmt.setString(2, advert.getDescription());
			stmt.setString(3, advert.getReq_skills());
			stmt.setString(4, advert.getClose_time());
		
			boolean active=advert.getActive();
			if(active) stmt.setInt(5, 1);
			else stmt.setInt(5, 0);
			
			stmt.setLong(7, advert.getId());
			stmt.setString(6, advert.getAct_deactTime());
			//System.out.println(advert.getCode());
			System.out.println(stmt);
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}
		
	}
	
	public void updateStatus(Advert advert) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = dataSource.getConnection();

			String sql = "update job_advert "
						+ " set active=?"
						+ " where id=?";

			stmt = con.prepareStatement(sql);

			boolean active=advert.getActive();
			if(active) stmt.setInt(1, 1);
			else stmt.setInt(1, 0);
			stmt.setLong(2, advert.getId());
			
			//System.out.println(advert.getCode());
			//System.out.println(stmt);
			stmt.execute();
		}
		finally {
			con.close();
			stmt.close();
		}
		
	}
	
	public void deleteAdvert(long id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = dataSource.getConnection();

			String sql = "delete from job_advert where id=?";

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
