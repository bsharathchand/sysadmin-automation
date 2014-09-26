package com.org.commons.atomcore.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @author Sharath
 *
 */
public class TestSSHJar {
	/**
	 * @param aa
	 */
	public static void main (String aa[]){
		String hostname = "10.200.252.104";
		String username = "loadruser";
		String password = "PerfTest4QA";
		
		Connection conn = new Connection(hostname);
		
		try {
			conn.connect();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean isAuthenticated;
		try {
			isAuthenticated = conn.authenticateWithPassword(username, password);
			if (isAuthenticated == false)
				System.out.println("Connection is not authenticated");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Session session = null;
		System.out.println("Started execution");
		try {
			
			session = conn.openSession();
			InputStream _resultOut ;
			BufferedReader _buffer;
		//	OutputStream stdout ;
			StringBuffer _storageBuffer;
		//	stdout =   session.getStdin();;
			_resultOut = new StreamGobbler(session.getStdout());
			_buffer = new BufferedReader(new InputStreamReader(_resultOut));
			session.execCommand("top -n 5");
			_storageBuffer = new StringBuffer();
			String _outputLine;
			while (true)
			{	
				try {
					_outputLine = _buffer.readLine();
				} catch (IOException e) {
					_outputLine = "Data Corrupted";
				}
				if (_outputLine == null)
					break;
				_storageBuffer.append(_outputLine);
				_storageBuffer.append("\n");
			}
			System.out.println(_storageBuffer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
			conn.close();
		}
		System.out.println("Completed execution");
	}
}
