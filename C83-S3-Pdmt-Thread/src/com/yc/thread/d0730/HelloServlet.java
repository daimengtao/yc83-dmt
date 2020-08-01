package com.yc.thread.d0730;

import java.io.PrintWriter;

public class HelloServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		
		PrintWriter out = response.getWriter();
		
		out.print("<h1>hello world</h1>");
		
		System.out.println("hello world!");
	}
}
