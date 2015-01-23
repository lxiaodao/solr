/**
 * Copyright HZCW (He Zhong Chuang Wei) Technologies Co.,Ltd. 2013-2015. All rights reserved.
 */
	
package com.weheros.searchengine.ecommerce.analyzer.mmseg4j;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.chenlb.mmseg4j.example.Complex;
import com.chenlb.mmseg4j.example.MaxWord;
import com.chenlb.mmseg4j.example.Simple;

/**
 * @ClassName: EcommerceWordsSegTest
 * @author Administrator
 * @date 2015年1月22日 下午2:56:03
 */
public class ClothesEcommerceWordsSegTest {
	
	Simple segW = new Simple();
	


	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testMerchandiseDscription() throws IOException {
		String content="淘宝人气爆款】双12活动现已火爆开启！现在不买后悔终生。优质加厚抓绒内胆，极致保暖，我们家的做到了内胆都可以防水，绝无仅有；"
	            +"外衣腋下透气窗设计，可快速排出体表多余热量及水分。现全国顺丰包邮，30天超长退换，真正零风险无忧购物体验！";
		String words = segW.segWords(content, "|");
		System.out.println("-------"+words);
		Assert.assertTrue(words.indexOf("优质|")!=-1);
		Assert.assertTrue(words.indexOf("保暖|")!=-1);
	}
	
	@Test
	public void testDressWordsAnalyze() throws IOException{
		String title="韩语琳2015新款春秋装女装大牌简约显瘦拼色连衣裙子 礼服长裙冬";		
		String words = segW.segWords(title, "|");
		System.out.println("-------"+words);
		Assert.assertTrue(words.indexOf("连衣裙|")!=-1);
		
	
	}
	@Test
	public void testSpecialWordsAboutWomanAnalyze() throws IOException{
		//MaxWord maxseg=new MaxWord();
		Complex maxseg=new Complex();
		String description="韩语琳-------名媛时尚美丽绽放----百分百用心始终如一的服务态度1.质感OL连衣裙，名媛范设计。2.修身版型，蝙蝠袖遮掩手臂赘肉，如花苞绽放，修身优雅。3.送模特同款腰带，立即抢购！！！";
		String descSegs = maxseg.segWords(description, "|");
		System.out.println("-------"+descSegs);
		//TODO:没测试通过的词需要加入词典
		Assert.assertTrue(descSegs.indexOf("名媛|")!=-1);
		Assert.assertTrue(descSegs.indexOf("时尚|")!=-1);
		Assert.assertTrue(descSegs.indexOf("美丽|")!=-1);
		Assert.assertTrue(descSegs.indexOf("模特|")!=-1);
		Assert.assertTrue(descSegs.indexOf("花苞|")!=-1);
		Assert.assertTrue(descSegs.indexOf("修身|")!=-1);
		Assert.assertTrue(descSegs.indexOf("优雅|")!=-1);
		Assert.assertTrue(descSegs.indexOf("抢购|")!=-1 ||descSegs.indexOf("|抢购")!=-1);
	}


}
