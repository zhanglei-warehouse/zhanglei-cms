package com.zhanglei.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhanglei.cms.pojo.Links;
import com.zhanglei.cms.service.LinksService;

/** 
* @author Name: zhanglei
* @version Time：2019年12月20日 下午7:15:48 
* 类说明 
*/
@Controller
@RequestMapping("/links/")
public class linksController {

	@Autowired
	private LinksService linksService;
	
	@RequestMapping("list")
	public String list(Links links,Model model,Integer pageNum){
		if(pageNum==null){
			pageNum=1;
		}
		PageHelper.startPage(pageNum,3);
		List<Links> list = linksService.list(links);
		PageInfo<Links> pageInfo = new PageInfo<>(list);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("links", links);
		return "admin/link";
	}
}
