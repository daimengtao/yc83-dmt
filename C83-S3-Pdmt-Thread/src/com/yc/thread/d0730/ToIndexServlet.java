package com.yc.thread.d0730;

public class ToIndexServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		/**
		 * ʵ����Ӧ�ض���
		 */
		response.sendRedirect("/photo/index.html");
	}
}
