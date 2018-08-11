package com.mansoorhaqanee.util;

import java.sql.*;


public class CreateDBConnection {
	
	public static Connection createConnection(String url){
		//h2 db
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
	
	public static void closeConnection(Connection conn){
		try {
			if (conn != null){
				System.out.println("Closing connection...");
				conn.close();
			}else{
				System.out.println("Connection object null");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
