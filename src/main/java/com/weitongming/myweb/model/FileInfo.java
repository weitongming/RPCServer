package com.weitongming.myweb.model;

import javax.annotation.Resource;
import java.io.Serializable;

@Resource
public class FileInfo implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String uploaderID;
	private String fileName ;
	private long fileLenght;
	private String fileUUID;
	private String fileType;
	private String fileTime;
	private String filePath;
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUUID() {
		return fileUUID;
	}
	public void setFileUUID(String fileUUID) {
		this.fileUUID = fileUUID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	public long getFileLenght() {
		return fileLenght;
	}
	public void setFileLenght(long fileLenght) {
		this.fileLenght = fileLenght;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUploaderID() {
		return uploaderID;
	}
	public void setUploaderID(String uploaderID) {
		this.uploaderID = uploaderID;
	}

}
