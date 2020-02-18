package com.zhanglei.cms.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/** 
* @ClassName: Shou 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月18日 上午9:00:55 
*/
@Document(indexName = "shou",type = "shou")
public class Shou implements Serializable{
	@Id
	private Integer id;
	@Field(analyzer = "ik_smart",searchAnalyzer = "ik_smaert",store = true,type = FieldType.text,index = true)
	private String text;
	private String url;
	private Integer userId;
	private Date created;
	@Override
	public String toString() {
		return "Shou [id=" + id + ", text=" + text + ", url=" + url + ", userId=" + userId + ", created=" + created
				+ "]";
	}
	public Shou(Integer id, String text, String url, Integer userId, Date created) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.userId = userId;
		this.created = created;
	}
	public Shou() {
		super();
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	
}
