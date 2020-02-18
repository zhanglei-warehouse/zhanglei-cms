package com.zhanglei.cms.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.zhanglei.cms.pojo.Article;

/** 
* @ClassName: ArticleRep 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月17日 下午2:40:41 
*/
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer>{



}
