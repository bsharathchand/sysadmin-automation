/**
 * 
 */
package com.org.commons.atomcore.test;

import java.sql.*;


/**
 * @author Sharath
 *
 */
public class TestSqlCommand {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Connection con = null;
		String commonString = "databaseName=master;integratedSecurity=false;";
		String connectionStringINT = "jdbc:sqlserver://10.200.254.38:2325;"+commonString ;
//		String connectionStringQA = "jdbc:sqlserver://10.200.254.168:2325;"+commonString ;
//		String connectionStringPreINT = "jdbc:sqlserver://10.200.40.57:2325;"+commonString ;

		String userNameINT = "loadruser";
		String loginPasswordINT = "PerfTest4QA";
//		String userNameQA = "gaqauser1";//"atlasdatareader4qa";
//		String loginPasswordQA = "GAq@user1Pwd";//"@tlasdatareader4qa";
//		String userNamePreINT = "gaqareader";
//		String loginPasswordPreINT = "gaqareader1";
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//con = DriverManager.getConnection(	 connectionStringQA,userNameQA,loginPasswordQA);
			con = DriverManager.getConnection(	 connectionStringINT,userNameINT,loginPasswordINT);
			//con = DriverManager.getConnection(	 connectionStringPreINT,userNamePreINT,loginPasswordPreINT);
		}
		catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
			System.exit(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		ResultSet rs;
		
		try {
			if(!con.isClosed()){
				stmt = con.createStatement();
				rs = stmt.executeQuery("sp_helpdb tempdb");
				String temp;
				System.out.println("Fetch Size "+rs.getFetchSize());
				while(rs.next()){
					for (int index=1;index<rs.getFetchSize();index++){
					temp = rs.getString(index);
					System.out.println(temp);
					index++;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

