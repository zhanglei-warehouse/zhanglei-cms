package com.zhanglei.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zhanglei.cms.pojo.Shou;

/** 
* @ClassName: ShouService 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月18日 上午9:07:42 
*/
public interface ShouService {

	int addShou(Shou s);

	PageInfo<Shou> listShou(Shou shou,int pageNum);

	int deleteShou(int i);

	List<Shou> getShouList(Shou shou);

	
}
