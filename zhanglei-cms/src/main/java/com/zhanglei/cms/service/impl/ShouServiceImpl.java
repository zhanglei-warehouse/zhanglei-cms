package com.zhanglei.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhanglei.cms.dao.ShouDao;
import com.zhanglei.cms.pojo.Shou;
import com.zhanglei.cms.service.ShouService;
import com.zhanglei.util.StringUtil;

/** 
* @ClassName: ShouServiceImpl 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月18日 上午9:07:55 
*/
@Service
public class ShouServiceImpl implements ShouService{

	@Autowired
	private ShouDao shouDao;
	@Override
	public int addShou(Shou s) {
		boolean httpUrl = StringUtil.isHttpUrl(s.getUrl());
		if(httpUrl) {
			int i = shouDao.addShou(s);
			return i;
		}else {
			return 0;
		}
	}
	//列表 分页
	@Override
	public PageInfo listShou(Shou shou,int pageNum) {
		PageHelper.startPage(pageNum, 4);
		List<Shou> list = shouDao.listShou(shou);
		PageInfo<Shou> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	//删除功能
	@Override
	public int deleteShou(int i) {
		return shouDao.deleteShou(i);
	}
	//单纯列表
	@Override
	public List<Shou> getShouList(Shou shou) {
		return shouDao.listShou(shou);
	}

}
