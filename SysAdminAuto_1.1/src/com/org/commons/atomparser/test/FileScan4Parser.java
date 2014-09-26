package com.org.commons.atomparser.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Sharath
 *
 */
public class FileScan4Parser {

	/**
	 * 
	 */
	public static String FileName = ".//Temp//Temp2";
	private static void getContentAsDefined(){
		StringTokenizer _tempTokens;
		File _tempFile = new File(FileName);
		Scanner _fileScanner;
		String _line;
		String token;
		int _tokenCount;
		try {
			_fileScanner = new Scanner(_tempFile);
			while(_fileScanner.hasNextLine()){
				_line = _fileScanner.nextLine();
				if(_line.startsWith("Date & Time")){
					System.out.println(_line);
					continue;
				}
				if(_line.startsWith("++++++")){
					if(true)//for the header column
						_fileScanner.nextLine();
					continue;
				}
				_tempTokens = new StringTokenizer(_line);
				_tokenCount = _tempTokens.countTokens();
				if(_tokenCount == 1){
					forLinuxServers(_fileScanner);
					continue;
				}
				for(int _index = 0;_index< _tokenCount;_index++)
				{
					token = _tempTokens.nextToken();
					if(_index ==4)
						System.out.print(token);
					if(_index==5)
						System.out.println("\t\t\t" + token);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void forLinuxServers(Scanner _fileScanner){
		StringTokenizer _tempTokens;
		int _tokenCount;
		String token;
		String _line;
		_line = _fileScanner.nextLine();
		_tempTokens = new StringTokenizer(_line);
		_tokenCount = _tempTokens.countTokens();
		for(int _index=0;_index<_tokenCount;_index++){
			token = _tempTokens.nextToken();
			if(_index == 3)
				System.out.print(token);
			if(_index==4)
				System.out.println("\t\t\t"+token);
		}
	}
	
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File _myFile = new File(".//Atom Results//10.200.252.105");
        String lineSeperator = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
        String _tempString;
        StringBuffer _buffer = new StringBuffer();
        int _index;
        String _line;
        Scanner _fileScanner;
        int i;
        try {
			_fileScanner = new Scanner(_myFile);
			while(_fileScanner.hasNextLine()){
                _tempString = _fileScanner.nextLine();
                _index = _tempString.indexOf("Date & Time");
			if(_index>=0){
                _buffer.append(_tempString); 
                _buffer.append("\n");
			}
			_index = _tempString.indexOf("df -k");
			if(_index>=0){
	            i = 1;
	            while(_fileScanner.hasNext()&&(!(_line = _fileScanner.nextLine()).equals(lineSeperator)||(i == 1))){
	                _buffer.append(_line);
	                _buffer.append("\n");
                    i++;
                }
            }
			}
			//System.out.println("Buffer contents :\n"+_buffer.toString());
        } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        System.out.println(e);
                        e.printStackTrace();
        }
        File _tempFile ;
        FileWriter _writeToFile; 
        try {
        	_tempFile = new File(FileName);
        	if(!_tempFile.exists()){
        		_tempFile.createNewFile();
			}
        	_writeToFile = new FileWriter(_tempFile);
        	_writeToFile.append(_buffer.toString());
        	_writeToFile.close();
        	getContentAsDefined();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
}

}
