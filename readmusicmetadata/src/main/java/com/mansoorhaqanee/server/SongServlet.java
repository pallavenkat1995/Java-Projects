package com.mansoorhaqanee.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mansoorhaqanee.util.CreateDBConnection;

public class SongServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		StringBuilder sb = new StringBuilder();
		//write to response..return html
		//String testHtmlString = "<html><h1>testing!!!</h1></html>";
		//resp.getWriter().write(testHtmlString);
        Connection conn = null;
        Statement stmt = null;
        String query = "select * from SONGS";
        try{
        	//grab connection to db again to retrieve song values
        	String url = "jdbc:h2:~/mydatabase"; //without init param so it doesnt wipe out 
        	conn = CreateDBConnection.createConnection(url);
        	stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	
        	while(rs.next()){
        		//creates as many rows in the table/iterators through
        		System.out.println("TESTING" + rs.getString("ARTIST"));
        		sb.append("<tr class=\"table\">")
        		.append("<td>").append(rs.getString("ARTIST")).append("</td>")
        		.append("<td>").append(rs.getString("YEAR")).append("</td>")
        		.append("<td>").append(rs.getString("ALBUM")).append("</td>")
        		.append("<td>").append(rs.getString("TITLE")).append("</td>")
        		.append("</tr>");
        	}
        	System.out.println(sb.toString());
    		String fullHTMLString = "<html><h1>Your Songs</h1><table><tr><th>Artist</th><th>Year</th><th>Album</th><th>Title</th></tr>" + sb.toString() + "</table></html>";
            resp.getWriter().write(fullHTMLString);
        	
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	System.out.println("test1");
        	//close connection
         CreateDBConnection.closeConnection(conn);
        	
        }
	}

}
