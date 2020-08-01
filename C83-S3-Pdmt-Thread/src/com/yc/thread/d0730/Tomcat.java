package com.yc.thread.d0730;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Tomcat {
	
	private HashMap<String, Servlet> servletMap;
	
	public void startup() throws IOException {
		
		
		servletMap = new HashMap<>();
		servletMap.put("/photo/hello", new HelloServlet());
		
		servletMap.put("/", new ToIndexServlet());
		servletMap.put("/index", new ToIndexServlet());
		servletMap.put("/toindex", new ToIndexServlet());
		
		servletMap.put("/cookie", new CookieServlet());
		servletMap.put("/login.jsp",new LoginPageServlet());
		servletMap.put("/photo/post.do",new LoginPageServlet());
		
		
		ServerSocket tomcat = new ServerSocket(8080);
		System.out.println("tomcat 已启动，监听端口：8080");
		boolean running = true;
		while(running) {
			Socket socket = tomcat.accept();
			
			new Thread() {
				public void run() {
					try {
						System.out.println("���ܵ�����");
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						
						byte[] buffer = new byte[1024];
						int count;
						count = in.read(buffer);
						if(count > 0) {
							
							String requestText = new String(buffer,0,count);
							System.out.println(requestText);
							
							
							HttpServletRequest request = buildRequest(requestText);
							HttpServletResponse response =new HttpServletResponse(out);
							
							String uri = request.getRequestURI();
							Servlet servlet = servletMap.get(uri);
							if(servlet != null) {
								
								servlet.service(request, response);
							}else {
								
								processStaticReuqest(request,out);
							}
						}
						socket.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		
				tomcat.close();
	}
	
	public void shutdown() {
		
	}
	
	/**
	 * 
	 */
	private HttpServletRequest buildRequest(String requestText) {
		return new HttpServletRequest(requestText);
	}
	
	public static void main(String[] args) throws IOException {
		new Tomcat().startup();
	}
	
	public void processStaticReuqest(HttpServletRequest request,OutputStream out) throws IOException {

		//
		String webpath = request.getRequestURI();
		String contentType;
		//
		int statusCode = 200;
		//
		String path = "D:/F/Tomcat/webapps"+webpath;
		File file = new File(path);
		if(!file.exists()) {
			statusCode = 404;
			path = "D:/F/Tomcat/webapps/photo/404.html";
		}
		if(webpath.endsWith(".js")) {
			contentType = "application/javascript; charset=utf-8";
		}else if(webpath.endsWith(".css")) {
			contentType = "text/css; charset=utf-8";
		}else {
			//
			contentType = "text/html; charset=utf-8";
		}
		
		//
		out.write(("HTTP/1.1 "+ statusCode +" OK\n").getBytes());
		//
		out.write(("Content-Type:"+ contentType +"\n").getBytes());
		//
		out.write("\n".getBytes());
		//ʵ
//		out.write("<h3>Hello World</h3>".getBytes());
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int count;
		while((count = fis.read(buffer)) > 0) {
			out.write(buffer,0,count);
		}
		/**
		 *
		 */
		fis.close();
		//
		//
	}
}	
