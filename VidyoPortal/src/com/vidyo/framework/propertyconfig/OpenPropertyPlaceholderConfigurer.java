/**
 * 
 */
package com.vidyo.framework.propertyconfig;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Ganesh
 * 
 */
public class OpenPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	/**
	 * 
	 */
	private Properties mergedProperties;

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Properties getMergedProperties() throws IOException {
		if (mergedProperties == null) {

			mergedProperties = mergeProperties();

		}
		return mergedProperties;

	}

}
