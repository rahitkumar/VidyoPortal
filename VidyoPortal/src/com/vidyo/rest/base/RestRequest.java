package com.vidyo.rest.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestRequest implements Serializable {
	private static final long serialVersionUID = -3152248054782341693L;
}
