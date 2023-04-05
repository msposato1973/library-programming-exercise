package com.sky.library;

/*
 * Copyright © 2015 Sky plc All Rights reserved.
 * Please do not make your solution publicly available in any way e.g. post in forums or commit to GitHub.
 */

public class BookNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/***
	 * 
	 */
	public BookNotFoundException() {
		super();
	}

	/***
	 * 
	 * @param message
	 */
	public BookNotFoundException(String message) {
		super(message);
	}

	/***
	 * 
	 * @param errorMessage
	 * @param err
	 */
	public BookNotFoundException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
