package com.zhanglei.cms.dao;

import java.util.List;

import com.zhanglei.cms.pojo.Shou;

/** 
* @ClassName: ShouDao 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月18日 上午9:06:39 
*/
public interface ShouDao {

	int addShou(Shou s);

	List<Shou> listShou(Shou shou);

	int deleteShou(int i);

}
