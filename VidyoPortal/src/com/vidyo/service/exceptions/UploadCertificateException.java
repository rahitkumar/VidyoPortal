package com.vidyo.service.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.*;

/**
 * This is the generic exception when failed to upload/apply the certificate files.
 */
public class UploadCertificateException extends Exception {

    public UploadCertificateException() {
         super("Failed to upload Certificate files");
    }

    public UploadCertificateException(String msg) {
        super(msg);
    }

    public UploadCertificateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UploadCertificateException(CertificateException e) {
        super("Invalid Certificate: "+e.getMessage(), e);
    }

    public UploadCertificateException(FileNotFoundException e) {
        super("File Not Found: "+e.getMessage(), e);// internal error
    }

    public UploadCertificateException(CertificateNotYetValidException e) {
        super("Invalid Certificate Expiry: "+e.getMessage(),e);
    }    
    
    public UploadCertificateException(CertificateExpiredException e) {
    	super("Invalid Certificate Expiry: "+e.getMessage(), e);    	
    }
    
    public UploadCertificateException(KeyStoreException e) {
    	super("Invalid data store or data store not found: "+e.getMessage(),e);// internal error
    } 
    
    public UploadCertificateException(IOException e) {
    	super("Failed to perform a file read or write: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(NoSuchAlgorithmException e) {
    	super("Server Internal Error: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(UnrecoverableKeyException e) {
    	super("Failed to load the server private key: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(CertPathValidatorException e) {
    	super("Failed to validate certificate chain: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(InvalidAlgorithmParameterException e) {
    	super("Server Internal Error: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(CertPathBuilderException e) {
    	super("Failed to build a chain to a trusted certificate: "+e.getMessage(), e); // internal error
    }

    public UploadCertificateException(Exception e) {
    	super("Unknown Error in parsing, validation or import of a certificate: "+e.getMessage(), e);    	
    }

}
