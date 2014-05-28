package org.code2bytes.samples.elastic;

import java.util.Date;

public class FileDescription {

	private String path;

	private String name;

	private Date lastModifed;

	private String tags;

	private String content;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastModifed() {
		return lastModifed;
	}

	public void setLastModifed(Date lastModifed) {
		this.lastModifed = lastModifed;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
