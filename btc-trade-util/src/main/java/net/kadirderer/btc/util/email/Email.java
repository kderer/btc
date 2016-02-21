package net.kadirderer.btc.util.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Email {
	
	private String from;
	private List<String> toList;
	private List<String> ccList;
	private String subject;
	private String body;
	private List<File> attachmentList;	
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getToList() {
		return toList;
	}
	
	public void setToList(List<String> toList) {
		this.toList = toList;
	}
	
	public void addToToList(String address) {
		if(toList == null) {
			toList = new ArrayList<String>();
		}
		
		toList.add(address);
	}
	
	public String[] getToListArray() {
		if(toList != null) {
			return toList.toArray(new String[toList.size()]);
		}
		
		return null;
	}
	
	public List<String> getCcList() {
		return ccList;
	}
	
	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}
	
	public void addToCcList(String address) {
		if(ccList == null) {
			ccList = new ArrayList<String>();
		}
		
		ccList.add(address);
	}
	
	public String[] getCcListArray() {
		if(ccList != null) {
			return ccList.toArray(new String[ccList.size()]);
		}
		
		return null;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public List<File> getAttachmentList() {
		return attachmentList;
	}
	
	public void setAttachmentList(List<File> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	public void addAttachment(File file) {
		if(attachmentList == null) {
			attachmentList = new ArrayList<File>();
		}
		
		attachmentList.add(file);
	}		

}
