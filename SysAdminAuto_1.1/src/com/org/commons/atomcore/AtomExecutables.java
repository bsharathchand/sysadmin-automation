package com.org.commons.atomcore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.org.commons.atomcore.exceptions.CheckedException;
import com.org.commons.atomcore.helpers.AtomExecutableCommand;


/**
 * @author Sharath
 *
 */
public class AtomExecutables {
	
	private ArrayList<AtomExecutableCommand> executables;
	private File executablesFile;
	private Scanner fileScanner;
	/**
	 * This file contains the list of commands and details
	 */
	public static final String ServersFileName = ".\\System\\ExecutablesList";
	
	/**
	 * @throws CheckedException 
	 * 
	 */
	public AtomExecutables() throws CheckedException{
		
		executablesFile = new File(ServersFileName);
		
		if(executablesFile.exists()){
			try {
				fileScanner = new Scanner(executablesFile);
			} catch (FileNotFoundException e) {
				CheckedException _exception = new CheckedException(e);
				_exception.setCustomMessage("Unable to open the Command File");
			}
		}else {
			throw new CheckedException("Executable File Not Found");
		}
		
		this.populateExecutables();
	}
	
	private  void populateExecutables(){
		
		AtomExecutableCommand _executable;
		executables = new ArrayList<AtomExecutableCommand>();
		String _executableId;
		while(fileScanner.hasNextLine()){
			_executable = new AtomExecutableCommand();
			_executableId = fileScanner.nextLine();
			_executable.setExecutableID(_executableId);
			executables.add(_executable);
		}
	}

	/**
	 * @return executables
	 */
	public ArrayList<AtomExecutableCommand> getExecutables() {
		return executables;
	}
}
