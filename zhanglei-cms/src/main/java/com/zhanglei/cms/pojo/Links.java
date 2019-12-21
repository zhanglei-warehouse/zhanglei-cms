package com.zhanglei.cms.pojo;

import java.io.Serializable;

/** 
* @author Name: zhanglei
* @version Time：2019年12月20日 下午6:42:03 
* 类说明 
*/
public class Links implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String text;
	private String url;
	private String created;
	@Override
	public String toString() {
		return "Links [id=" + id + ", text=" + text + ", url=" + url + ", created=" + created + "]";
	}
	public Links(Integer id, String text, String url, String created) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.created = created;
	}
	public Links() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
