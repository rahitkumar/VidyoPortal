/**
 * 
 */
package com.vidyo.framework.beanserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author ysakurikar
 *
 */
public class WhiteSpaceRemovalDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (jp.getValueAsString() != null)
			return jp.getValueAsString().trim();
		
		return jp.getValueAsString();
	}

}
