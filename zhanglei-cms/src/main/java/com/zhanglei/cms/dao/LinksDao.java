package com.zhanglei.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhanglei.cms.pojo.Links;

/** 
* @author Name: zhanglei
* @version Time：2019年12月20日 下午6:58:00 
* 类说明 
*/
public interface LinksDao {

	List<Links> list(@Param("links") Links links);

}
