package com.wip;

public class MainClass {

	public int add(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		MainClass mc = new MainClass();
		System.out.println(mc.add(10, 20));
		System.out.println("Java For Docker!!!! . THis code is pulled from WIP GitHub Repository");
		System.out.println("This is the updated code after adding Jenkinsfile to the repository");
		
		while (true) {
            try {
                Thread.sleep(60000); // 60 seconds
                System.out.println("Application is still running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

}