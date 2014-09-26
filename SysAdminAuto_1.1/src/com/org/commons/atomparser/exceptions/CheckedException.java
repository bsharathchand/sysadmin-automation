package com.org.commons.atomparser.exceptions;

/**
 * @author Sharath
 *
 */
public class CheckedException extends java.lang.Exception {

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
	public CheckedException(Exception exception){
		cause = exception.getCause();
		message = exception.getMessage();
		stackTrace = exception.getStackTrace();
	}

	/**
	 * @param customMessage
	 */
	public CheckedException(String customMessage){
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
