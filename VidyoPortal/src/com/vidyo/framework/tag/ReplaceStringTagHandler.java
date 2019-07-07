package com.vidyo.framework.tag;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.tagext.*;

public class ReplaceStringTagHandler extends BodyTagSupport {
	private String _from;
	private String _to;
	
	protected final Logger logger = LoggerFactory.getLogger(ReplaceStringTagHandler.class.getName());
	
	public void setFrom(String arg){
		_from = arg;
	}
	
	public void setTo(String arg){
		_to = arg;
	}
	
	public int doAfterBody(){
		BodyContent body = this.getBodyContent();
		
		Writer writer = body.getEnclosingWriter();
		
		try{
			String output = body.getString().replaceAll(_from, _to);
			
			writer.write(output);
		}
		catch(Exception e){
			logger.error("String Replace tag execution failed: " + e.getMessage());
		}
		
		return BodyTagSupport.SKIP_BODY;
	}	
}
