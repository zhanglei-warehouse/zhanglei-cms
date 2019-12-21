package com.zhanglei.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanglei.cms.dao.LinksDao;
import com.zhanglei.cms.pojo.Links;

/** 
* @author Name: zhanglei
* @version Time：2019年12月20日 下午6:38:39 
* 类说明 
*/
@Service
public class LinksService {
	@Autowired
	private LinksDao linkDao;

	public List<Links> list(Links links) {
		// TODO Auto-generated method stub
		return linkDao.list(links);
	}
	
}
