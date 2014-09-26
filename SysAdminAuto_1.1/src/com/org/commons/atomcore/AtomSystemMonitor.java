/**
 * 
 */
package com.org.commons.atomcore;

import java.util.Date;

import com.org.commons.atomcore.exceptions.CheckedException;

/**
 * @author Sharath
 *
 */
public class AtomSystemMonitor {
	

	/**
	 * @param aa
	 */
	public static void main(String aa[]){
		AtomCoreExecute ace = null;
		Thread.yield();
		System.out.println("Date Started :"+new Date()+" Time : "+(new Date()).getTime());
		try {
			 ace = new AtomCoreExecute();
			 ace.prepareRunnerList();
			 ace.startRunners();
		} catch (CheckedException e) {
			System.out.println("Critical Error "+e.getCustomMessage());
			e.printStackTrace();
		}
		System.out.println("Date Finished :"+new Date()+" Time : "+(new Date()).getTime());
		System.out.println("Execution Completed");
	}

}
