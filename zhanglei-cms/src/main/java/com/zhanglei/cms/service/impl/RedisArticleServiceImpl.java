package com.zhanglei.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.zhanglei.cms.pojo.Article;
import com.zhanglei.cms.service.RedisArticleService;

/** 
* @ClassName: RedisArticleServiceImpl 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月17日 下午1:49:16 
*/
@Service
public class RedisArticleServiceImpl implements RedisArticleService{

	@Autowired
	private RedisTemplate<String, Article> reidsTemplate;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public void save(Article article) {
		ValueOperations<String, Article> opsForValue = reidsTemplate.opsForValue();
		opsForValue.set(article.getTitle(), article);
		kafkaTemplate.sendDefault( "addRedisArticle", article.getTitle());
	}

	
	
}
