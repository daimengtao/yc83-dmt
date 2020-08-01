package com.yc.thread.d0727;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	public static void main(String[] args) throws IOException {
		
		ServerSocket tomcat = new ServerSocket(8080);
		System.out.println("tomcat服务器启动，监听8080端口");
		boolean running = true;
		while (running) {
			Socket socket = tomcat.accept();
			new Thread() {
				public void run() {
					try {
						System.out.println("接收到请求");
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						byte[] buffer = new byte[1024];
						int count;
						count = in.read(buffer);
						if (count > 0) {
							//打印请求报文
							String requestText=new String(buffer, 0, count);
							System.out.println(requestText);
							
							//解析出请求资源路径
							String[] lines=requestText.split("\\n");
							String[] requestLines=lines[0].split("\\s");
							String webpath=requestLines[1];
							String contenType;
							//结果码 
							int statusCode=200;
							//定义磁盘文件路径
							String path = "D:/三期/photo/"+webpath;
							File file=new File(path);
							if(! file.exists() ) {
								statusCode=404;
								path="D:/三期/photo/404.html";
							}
							if(webpath.endsWith(".js")) {
								contenType="application/javascript;charset=utf-8";
							}else if(webpath.endsWith(".css")) {
								contenType="text/css;charset=utf-8";
							}else {
								contenType="text/html;charset=utf-8";
							}
							// 相应头行
							out.write("HTTP/1.1 200 OK\n".getBytes());
							// 相应头域
							out.write("Content-Type:text/html;charset=utf-8\n".getBytes());
							// 空行CRLF
							out.write("\n".getBytes());
							// 实体
							// out.write("<h1>Hello World</h1>".getBytes());

							
							FileInputStream fis = new FileInputStream(path);
							while ((count = fis.read(buffer)) > 0) {
								out.write(buffer, 0, count);
							}
							fis.close();
						}

						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

		tomcat.close();
	}

}
