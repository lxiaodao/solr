/**
 * Copyright HZCW (He Zhong Chuang Wei) Technologies Co.,Ltd. 2013-2015. All rights reserved.
 */
	
package com.weheros.searchengine.ecommerce.analyzer.mmseg4j;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.chenlb.mmseg4j.example.Complex;
import com.chenlb.mmseg4j.example.MaxWord;

/**
 * @ClassName: FoodEcommerceWordsSegTest
 * @author Administrator
 * @date 2015年1月22日 下午5:34:23
 */
public class FoodEcommerceWordsSegTest {
	
	@Test
	public void testCandyWordsAnalyze() throws IOException{
		//MaxWord maxseg=new MaxWord();
		Complex maxseg=new Complex();
		String description="优之良品 进口食品QQ软糖糖果 果汁混合橡皮糖280g*3包 包邮";
		String descSegs = maxseg.segWords(description, "|");
		System.out.println("-------"+descSegs);
		//TODO:没测试通过的词需要加入词典
		Assert.assertTrue(descSegs.indexOf("软糖|")!=-1);
		Assert.assertTrue(descSegs.indexOf("糖果|")!=-1);
		Assert.assertTrue(descSegs.indexOf("橡皮糖|")!=-1);
		Assert.assertTrue(descSegs.indexOf("|包邮")!=-1);
	}

}
