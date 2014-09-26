package com.org.commons.atomcore.sequel.exceptions;

/**
 * @author Sharath
 * Overrides the Exceptions raised by the External JAR..
 * This overriding is done provide complete details of the exception raised.
 * This will also have most of the exception related methods.
 */
public class AtomSqlException extends Exception {

	/**
	 * Done as per the java doc principles and guide lines to maintain serialization. 
	 */
	private static final long serialVersionUID = 1L;
	
	private StackTraceElement[] stackTrace;
	private Throwable cause;
	private String message;
	private String customMessage;
	
	
	/**
	 * @param exception
	 */
	public AtomSqlException(Exception exception){
		cause = exception.getCause();
		message = exception.getMessage();
		stackTrace = exception.getStackTrace();
	}

	/**
	 * @param customMessage
	 */
	public AtomSqlException(String customMessage){
		this.customMessage = customMessage;
	}

	/**
	 * @return the stackTrace
	 */
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}


	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param customMessage the customMessage to set
	 */
	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}


	/**
	 * @return the customMessage
	 */
	public String getCustomMessage() {
		return customMessage;
	}
	

}
