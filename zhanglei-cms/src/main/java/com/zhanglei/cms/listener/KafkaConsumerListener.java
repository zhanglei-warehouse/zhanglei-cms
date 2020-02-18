package com.zhanglei.cms.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.zhanglei.cms.dao.ArticleDao;
import com.zhanglei.cms.pojo.Article;
import com.zhanglei.cms.service.ArticleService;
import com.zhanglei.util.RandomUtil;

/** 
* @ClassName: KafkaListener 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月13日 下午1:40:47 
*/
@Component("kafkaConsumerListener")
public class KafkaConsumerListener implements MessageListener<String, String>{

	@Resource
	private ArticleDao articleDao;
	@Autowired
	private ArticleService articleServiceImpl;
	@Autowired
	private RedisTemplate<String, Article> redisTemplate;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		//System.err.println(data.key()+data.value());
		//修改浏览数量
		if(data.key()!=null && data.key().equals("articleId")) {
			articleDao.addHits(data.value());
		}else if(data.key()!=null && data.key().equals("add")) {
			String[] split = data.value().split("\\@\\@");
			Article article = new Article();
			article.setId(null);
			article.setTitle(split[0]);
			article.setContent(split[1].substring(0,139));
			Date date = new Date();
			long time = date.getTime();
			article.setCreated(new Date(time));
			article.setUserId(153);
			article.setHits(RandomUtil.random(0, 100));
			article.setHot(RandomUtil.random(0, 100));
			article.setPicture("/pic/image/20191221/20191221081440_596.png");
			article.setStatus(1);
			article.setChannelId(1);
			article.setCategoryId(1);
			System.err.println(article);
			articleDao.insert(article);
		}else if(data.key()!=null && data.key().equals("addRedisArticle")) {
			ValueOperations<String, Article> opsForValue = redisTemplate.opsForValue();
			Article article = opsForValue.get(data.value());
			int insert = articleServiceImpl.insert(article);
			if(insert>0) {
				redisTemplate.delete(data.value());
				System.out.println(data.value()+"文章导入完毕");
			}
		}
	}

	

}
