package com.samuel.bookstore.service.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ObjectNotFoundException(String nome, Integer id) {
		super(nome + " com id=" + id + " não existe!");
	}	
}
