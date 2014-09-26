/**
 * 
 */
package com.org.commons.atomcore.test;

import java.util.ArrayList;

/**
 * @author Sharath
 *
 */
public class TestThreading {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting Threads");
		System.out.println("*******************************************");
		Thread.yield();
		final ArrayList<Integer> _al;
		ArrayList<Integer> _bl = new ArrayList<Integer>();
		for(int index = 0; index<10;index++){
			_bl.add(index, (Integer)index);
		}
		_al = _bl;
		if(_al.size()>0){
			System.out.println("I am in ");
			new Thread(){
				public void run(){
					for(int i=0;i<10;i++){
						System.out.println("a -"+_al.get(i));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							System.out.println("Thread interruped on "+i+"iteration of a");
							e.printStackTrace();
						}
					}
				}
			}.start();
			new Thread(){
				public void run(){
					for(int i=0;i<10;i++){
						System.out.println("b -"+_al.get(i));
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							System.out.println("Thread interruped on "+i+"iteration of b");
							e.printStackTrace();
						}
					}
				}
			}.start();
			new Thread(){
				public void run(){
					for(int i=0;i<10;i++){
						System.out.println("c -"+_al.get(i));
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							System.out.println("Thread interruped on "+i+"iteration of c");
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		System.out.println("*******************************************");
		System.out.println("Threads Completed");
	}

}
