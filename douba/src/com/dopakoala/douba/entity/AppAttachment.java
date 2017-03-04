package com.dopakoala.douba.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigInteger;

public class AppAttachment implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Timestamp created;

	private String desc;

	private BigInteger fileid;

	private String filename;

	private Integer filesize;

	private String filetype;

	private Integer isimage;

	private String oldfilename;

	private String path;

	private String title;

	private String type;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigInteger getFileid() {
		return this.fileid;
	}

	public void setFileid(BigInteger fileid) {
		this.fileid = fileid;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Integer getIsimage() {
		return this.isimage;
	}

	public void setIsimage(Integer isimage) {
		this.isimage = isimage;
	}

	public String getOldfilename() {
		return this.oldfilename;
	}

	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}