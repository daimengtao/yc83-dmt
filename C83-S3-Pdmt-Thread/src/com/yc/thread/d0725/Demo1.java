package com.yc.thread.d0725;

import java.util.Scanner;

public class Demo1 {
	public static void main(String[] args) {
		//a();  调用a b实现异步  b方法只能在a方法实现之后才能运行
		//b();
		A a=new A("a方法的线程");  //使用线程实现a b 同步 在a方法没有实现b也可以运行
		B b=new B();
		Thread t=new Thread(b,"b方法的线程");
		
		a.start(); //启动线程
		t.start();
		//获取当前线程：主线程
		System.out.println("main getName "+Thread.currentThread().getName()); 
		System.out.println("main getPriority "+Thread.currentThread().getPriority());
		System.out.println("main getId "+Thread.currentThread().getId());
		System.out.println("main getState "+Thread.currentThread().getState());
	}
	public static void a() {
		Scanner sc=new Scanner(System.in);
		System.out.print("请输入：");
		String s=sc.nextLine();
		System.out.println("本次输入的是："+s);
		sc.close();
	}
	public static void b() {
		System.out.println("这是b（）方法");
	}
	//继承
	public static class A extends Thread {
		public A(String name) {
			super(name);
		}
		public void run() {
			a();
			System.out.println("a getName "+Thread.currentThread().getName()); 
			System.out.println("a getPriority "+Thread.currentThread().getPriority());
			System.out.println("a getId "+Thread.currentThread().getId());
			System.out.println("a getState "+Thread.currentThread().getState());
		}
	}
	//实现接口
	public static class B implements Runnable{

		
		public void run() {
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b();
			System.out.println("b getName "+Thread.currentThread().getName()); 
			System.out.println("b getPriority "+Thread.currentThread().getPriority());
			System.out.println("b getId "+Thread.currentThread().getId());
			System.out.println("b getState "+Thread.currentThread().getState());
			
		}
		
	}

}
