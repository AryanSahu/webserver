package com.aryan.rd.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TheFlyingServer {
	
	
	public static void main(String[] args) throws IOException {
		
		int port = 9000;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		System.out.println("server started at " + port);
		server.createContext("/webserver", new ContextHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		
	}
	
}
	
	class ContextHandler implements HttpHandler {
		
		  public void handle(HttpExchange exchange) throws IOException {
		    String requestMethod = exchange.getRequestMethod();
		    if (requestMethod.equalsIgnoreCase("GET")) {
		      Headers responseHeaders = exchange.getResponseHeaders();
		      responseHeaders.set("Content-Type", "text/plain");
		      exchange.sendResponseHeaders(200, 0);

		      OutputStream responseBody = exchange.getResponseBody();
		      Headers requestHeaders = exchange.getRequestHeaders();
		      Set<String> keySet = requestHeaders.keySet();
		      Iterator<String> iter = keySet.iterator();
		      while (iter.hasNext()) {
		        String key = iter.next();
		        List values = requestHeaders.get(key);
		        String s = key + " = " + values.toString() + "\n";
		        responseBody.write(s.getBytes());
		      }
		      responseBody.close();
		    }
		  }
	}
	

