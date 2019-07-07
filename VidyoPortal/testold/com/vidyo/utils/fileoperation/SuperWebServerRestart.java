package com.vidyo.utils.fileoperation;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.junit.Before;
import org.junit.Test;

public class SuperWebServerRestart {
	String cacCertificateLocationStg =null;
	String cacCertificateLocation =null;
	String tenantId="8";
	
	
	@Before
	public void setUp() throws Exception {
		cacCertificateLocationStg="C:\\Users\\hjoy\\tenantlist";
		cacCertificateLocation="C:\\Users\\hjoy\\tenantlist1\\";
	}

	/**
	 * this test will test against if refresh time is expired and we need to retrieve thumbnail. So map size should be same as initial
	 * @throws IOException 
	 */
	@Test
	public void testGetTenantsFromFolder() throws IOException {
		List<String>tenantIds=new ArrayList<String>();
		
		Object[] paths=null;
		paths=Files.list(new File(cacCertificateLocationStg).toPath())
	     .filter(p -> p.getFileName().toString().contains(".cfg.unsaved")).toArray();
		
	    for(Object ob:paths){
	    	Path filePath=(Path)ob;
	    	String fileName=filePath.getName(filePath.getNameCount()-1).toString();
	    	String tenantId=fileName.substring(0, fileName.indexOf(".unsaved"));
	    	System.out.println(tenantId);
	    }
	     
	}
	@Test
	public void testMoveFilesBasedonTenantId() throws IOException {

		
		Object[] paths=null;
		paths=Files.list(new File(cacCertificateLocationStg).toPath())
	     .filter(p -> p.getFileName().toString().contains(tenantId)).toArray();
		
	    for(Object ob:paths){
	    	Path filePath=(Path)ob;
	    	System.out.println(filePath.getName(filePath.getNameCount()-1).toString());
	    	Files.move(filePath,new File(cacCertificateLocation+filePath.getName(filePath.getNameCount()-1).toString()).toPath(),StandardCopyOption.REPLACE_EXISTING);
	    }
	     
	}
	

}
