package com.mansoorhaqanee.util;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.mansoorhaqanee.server.SongServlet;

public class StartHTTPServer {
	//mainly jetty dependencies and methods to create the server
	public static void startServer() throws Exception{
			Server server = new Server(8080);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");
	        context.setResourceBase(System.getProperty("java.io.tmpdir"));
	        server.setHandler(context);
	        //servlet extends capabilities of the servers
	        context.addServlet(SongServlet.class, "/songs");//.addServlet(SongServlet.class, ""));
	        server.start();

	        if (Desktop.isDesktopSupported()) {
	            Desktop.getDesktop().browse(new URI("http://localhost:8080/songs"));
	        }
	}

}
