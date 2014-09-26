/**
 * 
 */
package com.org.commons.atomparser.test;

import java.io.File;

/**
 * @author Sharath
 *
 */
public class Files4Parser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = ".//Atom Results//";
		File _myFile = new File(fileName);
		File _parseFiles[];
		if(_myFile.isDirectory()){
			_parseFiles = _myFile.listFiles();
			for(int _index=0;_index<_parseFiles.length;_index++){
				System.out.println(_parseFiles[_index].getAbsolutePath());
			}
			
		}

	}

}
