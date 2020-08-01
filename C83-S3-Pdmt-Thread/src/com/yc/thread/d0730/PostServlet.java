package com.yc.thread.d0730;

public class PostServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getParameter("name");
		System.out.println("==============");
		response.getWriter().append("post,name="+name);
		
		/**
		 * 
		 */
	}
}
