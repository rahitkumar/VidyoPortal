package com.vidyo.bo.security;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CRTObject {

	private String version;
	private String serial;
	private String fingerprint;
	private Date notBefore;
	private Date notAfter;

	private String issuerCN;
	private String issuerOU;
	private String issuerO;
	private String issuerL;
	private String issuerST;
	private String issuerSTREET;
	private String issuerEMAIL;
	private String issuerC;

	private String subjectCN;
	private String subjectOU;
	private String subjectO;
	private String subjectL;
	private String subjectST;
	private String subjectSTREET;
	private String subjectEMAIL;
	private String subjectC;
	private boolean expired;
	private boolean notYetValid;
	private List<String> subjectAltNames;


	public CRTObject(){
		version = "";
		serial = "";
		fingerprint = "";
		issuerCN = "";
		issuerOU = "";
		issuerO = "";
		issuerL = "";
		issuerST = "";
		issuerSTREET = "";
		issuerEMAIL = "";
		issuerC = "";

		subjectCN = "";
		subjectOU = "";
		subjectO = "";
		subjectL = "";
		subjectST = "";
		subjectSTREET = "";
		subjectEMAIL = "";
		subjectC = "";
		expired = false;
		notYetValid = false;
	}

	public void setVersion(String s){
		version = s;
	}
	public void setSerial(String s){
		serial = s;
	}
	public void setFingerprint(String s){
		fingerprint = s;
	}
	public void setNotBefore(Date d){
		notBefore = d;
	}
	public void setNotAfter(Date d){
		notAfter = d;
	}
	public void setIssuerCN(String s){
		issuerCN = s;
	}
	public void setIssuerOU(String s){
		issuerOU = s;
	}
	public void setIssuerO(String s){
		issuerO = s;
	}
	public void setIssuerL(String s){
		issuerL = s;
	}
	public void setIssuerST(String s){
		issuerST = s;
	}
	public void setIssuerSTREET(String s){
		issuerSTREET = s;
	}
	public void setIssuerEMAIL(String s){
		issuerEMAIL = s;
	}
	public void setIssuerC(String s){
		issuerC = s;
	}
	public void setSubjectCN(String s){
		subjectCN = s;
	}
	public void setSubjectOU(String s){
		subjectOU = s;
	}
	public void setSubjectO(String s){
		subjectO = s;
	}
	public void setSubjectL(String s){
		subjectL = s;
	}
	public void setSubjectST(String s){
		subjectST = s;
	}
	public void setSubjectSTREET(String s){
		subjectSTREET = s;
	}
	public void setSubjectEMAIL(String s){
		subjectEMAIL = s;
	}
	public void setSubjectC(String s){
		subjectC = s;
	}
	public void setSubjectAltNames(List<String> ss) { subjectAltNames = ss; }

	public String getVersion(){
		if(version == null) return "";
		else return version;
	}
	public String getSerial(){
		if(serial == null) return "";
		else return serial;
	}
	public String getFingerprint(){
		if(fingerprint == null) return "";
		else return fingerprint;
	}
	public String getNotBefore(){
		if(notBefore == null) return "";
		else return notBefore.toString();
	}
	public String getNotAfter(){
		if(notAfter == null) return "";
		else return notAfter.toString();
	}
	public String getIssuerCN(){
		if(issuerCN == null) return "";
		else return issuerCN;
	}
	public String getIssuerOU(){
		if(issuerOU == null) return "";
		else return issuerOU;
	}
	public String getIssuerO(){
		if(issuerO == null) return "";
		else return issuerO;
	}
	public String getIssuerL(){
		if(issuerL == null) return "";
		else return issuerL;
	}
	public String getIssuerST(){
		if(issuerST == null) return "";
		else return issuerST;
	}
	public String getIssuerSTREET(){
		if(issuerSTREET == null) return "";
		else return issuerSTREET;
	}
	public String getIssuerEMAIL(){
		if(issuerEMAIL == null) return "";
		else return issuerEMAIL;
	}
	public String getIssuerC(){
		if(issuerC == null) return "";
		else return issuerC;
	}

	public String getSubjectCN(){
		if(subjectCN == null) return "";
		else return subjectCN;
	}
	public String getSubjectOU(){
		if(subjectOU == null) return "";
		else return subjectOU;
	}
	public String getSubjectO(){
		if(subjectO == null) return "";
		else return subjectO;
	}
	public String getSubjectL(){
		if(subjectL == null) return "";
		else return subjectL;
	}
	public String getSubjectST(){
		if(subjectST == null) return "";
		else return subjectST;
	}
	public String getSubjectSTREET(){
		if(subjectSTREET == null) return "";
		else return subjectSTREET;
	}
	public String getSubjectEMAIL(){
		if(subjectEMAIL == null) return "";
		else return subjectEMAIL;
	}
	public String getSubjectC(){
		if(subjectC == null) return "";
		else return subjectC;
	}
	public void setExpired(boolean b){
		expired = b;
	}
	public boolean getExpired(){
		return expired;
	}

	public void setNotYetValid(boolean b){
		notYetValid = b;
	}
	public boolean getNotYetValid(){
		return notYetValid;
	}

	public List<String> getSubjectAltNames() {
		if (this.subjectAltNames == null) {
			this.subjectAltNames = Collections.emptyList();
		}
		return this.subjectAltNames;
	}
}
