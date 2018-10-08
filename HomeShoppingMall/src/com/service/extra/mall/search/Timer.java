package com.service.extra.mall.search;


import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.service.extra.mall.mapper.ProductMapper;
import com.service.extra.mall.model.vo.ProductVo;





public class Timer extends QuartzJobBean{
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			 ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-common.xml");
			 ProductMapper productMapper = (ProductMapper) ctx.getBean("productMapper");
			List<ProductVo> list =productMapper.getProduct();
			CreateIndexFile createIndexFile = new CreateIndexFile();
			createIndexFile.ForIndexCreate(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
