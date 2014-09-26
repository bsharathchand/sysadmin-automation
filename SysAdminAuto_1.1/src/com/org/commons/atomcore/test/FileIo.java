/**
 * 
 */
package com.org.commons.atomcore.test;

import java.io.File;
import java.io.IOException;

/**
 * @author Sharath
 *
 */
public class FileIo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File _myFile ;
		_myFile = new File(".\\Temp\\TestFile");
		if(!_myFile.exists()){
			try {
				_myFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Error while creating a file "+e);
				e.printStackTrace();
			}
		}
		System.out.println("Execution Completed");
	}

}
