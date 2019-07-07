package com.vidyo.superapp.exceptions;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@JsonSerialize(using=StringSerializer.class)
    private String url;
    
    @JsonSerialize(using=StringSerializer.class)
    private String message;
     
    public ErrorInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }
 
}