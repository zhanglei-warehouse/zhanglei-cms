package com.zhanglei.cms.controller;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.fieldstats.FieldStats.Ip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.zhanglei.cms.pojo.Article;
import com.zhanglei.cms.pojo.Category;
import com.zhanglei.cms.pojo.Channel;
import com.zhanglei.cms.pojo.Slide;
import com.zhanglei.cms.pojo.User;
import com.zhanglei.cms.service.ArticleService;
import com.zhanglei.cms.service.SlideService;
import com.zhanglei.cms.service.UserService;
import com.zhanglei.util.HLUtils;

@Controller
public class IndexController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	private SlideService slideService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor; 
	
	@RequestMapping(value="/")
	public String index(Model model,String like) {
		return index(1, model, like);
	}
	
	@RequestMapping(value="/hot/{pageNum}.html")
	public String index(@PathVariable Integer pageNum, Model model,String like) {
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		/** 轮播图 */
		List<Slide> slideList = slideService.getAll();
		model.addAttribute("slideList", slideList);
		/** 最新文章 **/
		List<Article> newArticleList = articleService.getNewList(6);
		model.addAttribute("newArticleList", newArticleList);
		/** 热点文章 **/
		if(pageNum==null) {
			pageNum=1;
		}
		
		//高亮显示
		if(like != null && like != "") {
			long start = System.currentTimeMillis();
			//调用高亮显示工具类
			PageInfo<Article> pageInfo = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, pageNum, 3, new String[] {"title"}, "id", like);
			long end = System.currentTimeMillis();
			//设置分页
			pageInfo.setPageNum(pageNum);
			pageInfo.setPrePage(pageInfo.getPrePage()==1?pageInfo.getPrePage():1);
			pageInfo.setNextPage(pageInfo.getNextPage()==pageInfo.getPages()?pageInfo.getNextPage():pageInfo.getPages());
			model.addAttribute("pageInfo", pageInfo);
			model.addAttribute("like",like);
		}else {
			//普通查询
			PageInfo<Article> pageInfo =  articleService.getHotList(pageNum);
			
			
			//实现首页热门文章第一页记录Redis缓存功能，有效期5分钟。
			List<Article> list = pageInfo.getList();
			for (Article article : list) {
				redisTemplate.opsForValue().set(article.getId().toString(), article.toString(),5,TimeUnit.MINUTES);
			}
			model.addAttribute("pageInfo", pageInfo);
		}
		
		return "index";
	}
	
	
	@RequestMapping("/{channelId}/{cateId}/{pageNo}.html")
	public String channel(@PathVariable Integer channelId, Model model,
			@PathVariable Integer cateId,@PathVariable Integer pageNo) {
		/** 频道 */
		List<Channel> channelList = articleService.getChannelList();
		model.addAttribute("channelList", channelList);
		/** 最新文章 **/
		List<Article> newArticleList = articleService.getNewList(6);
		model.addAttribute("newArticleList", newArticleList);
		/** 分类 */
		List<Category> cateList = articleService.getCateListByChannelId(channelId);
		model.addAttribute("cateList", cateList);
		PageInfo<Article> pageInfo =  articleService.getListByChannelIdAndCateId(channelId,cateId,pageNo);
		model.addAttribute("pageInfo", pageInfo);
		return "index";
	}
	
	
	@RequestMapping("article/{id}.html")
	public String articleDetail(@PathVariable Integer id,Model model,HttpServletRequest request) {
		//通过kafka添加浏览数据
		/* kafkaTemplate.sendDefault("articleId", String.valueOf(id)); */
		String ip = request.getRemoteAddr();
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		String string = opsForValue.get("Hits_${"+id+"}_${"+ip+"}");
		if(("Hits_${"+id+"}_${"+ip+"}").equals(string)) {
			
		}else {
			opsForValue.set("Hits_${"+id+"}_${"+ip+"}", "Hits_${"+id+"}_${"+ip+"}", 5, TimeUnit.MINUTES);
			/* kafkaTemplate.sendDefault("articleId", String.valueOf(id)); */
			int i = articleService.addHits(String.valueOf(id));
			threadPoolTaskExecutor.execute(new Runnable() {

				@Override
				public void run() {
					
				}
			});
			
		}
		/** 查询文章 **/
		Article article = articleService.getById(id);
		/** 查询用户 **/
		User user = userService.getById(article.getUserId());
		model.addAttribute("article", article);
		
		model.addAttribute("user", user);
		/** 查询相关文章 **/
		List<Article> articleList = articleService.getListByChannelId(article.getChannelId(),id,10);
		model.addAttribute("articleList", articleList);
		return "article/detail";
	}
}
