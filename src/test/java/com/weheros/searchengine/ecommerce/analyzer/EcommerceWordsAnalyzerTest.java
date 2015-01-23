/**
 * Copyright HZCW (He Zhong Chuang Wei) Technologies Co.,Ltd. 2013-2015. All rights reserved.
 */
	
package com.weheros.searchengine.ecommerce.analyzer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.weheros.searchengine.BaseTest;

/**
 * @ClassName: EcommerceWordsAnalyzerTest
 * @author Administrator
 * @date 2015年1月15日 下午5:33:09
 */
public class EcommerceWordsAnalyzerTest extends BaseTest{
	
	
	List<String> ids=new ArrayList<String>();
	@Before
	public void setUp() throws SolrServerException, IOException, ParseException{
		   
           SolrInputDocument doc1 = new SolrInputDocument();
           String uuid=UUID.randomUUID().toString();
           doc1.addField("id", uuid);
           doc1.addField("supply_title", "正品冬季户外冲锋衣男三合一女保暖抓绒 防水两件套情侣款登山服");           
          
           String content="淘宝人气爆款】双12活动现已火爆开启！现在不买后悔终生。优质加厚抓绒内胆，极致保暖，我们家的做到了内胆都可以防水，绝无仅有；"
                         +"外衣腋下透气窗设计，可快速排出体表多余热量及水分。现全国顺丰包邮，30天超长退换，真正零风险无忧购物体验！";
           doc1.addField("supply_memo", content);
           doc1.addField("shop_title", "尚风美美");
           doc1.addField("itel_no", "15788904566");
           doc1.addField("trade_name", "冲锋衣");
           doc1.addField("area_name", "成都市成华区");
           
           doc1.addField("reallegalize", 1);
           doc1.addField("quality_guarantee", 1);
           doc1.addField("sevenlegalize", 1);
           doc1.addField("realname_guarantee",1);
           
           doc1.addField("caution_money",1000.80);
           doc1.addField("low_price",230.00);
           doc1.addField("high_price",498.00);
           
           doc1.addField("updatetime",new Date().getTime());
       	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		
           doc1.addField("end_date",sdf.parse("2014-04-12 10:20:46").getTime());
           doc1.addField("soldcount",22);
           //
           Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
           docs.add(doc1);
         

           server.add(docs);
           server.commit();
           
           ids.add(uuid);
	}
	
	@After
	public void tearDown() throws SolrServerException, IOException{
		this.server.deleteById(ids);
	}

	@Test
	public void testAsLikeQuery() throws SolrServerException {
		String sql = "supply_title:冲锋衣~";
		QueryResponse response = this.execute("testAsLikeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}

	@Test
	public void testAccurateQuery() throws SolrServerException {
		String sql = "supply_title:正品";
		QueryResponse response = this.execute("testAccurateQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}
	@Test
	public void testPriceRangeQuery() throws SolrServerException {
		String sql = "low_price:[200 TO 1000]";
		QueryResponse response = this.execute("testPriceRangeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}
	@Test
	public void testNotInPriceRangeQuery() throws SolrServerException {
		String sql = "low_price:[400 TO 1000]";
		QueryResponse response = this.execute("testNotInPriceRangeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) == 0);
	}
	@Test
	public void testLowAndHighPriceRangeQuery() throws SolrServerException {
		String sql = "low_price:[400 TO 1000] OR high_price:[400 TO 1000]";
		QueryResponse response = this.execute("testLowAndHighPriceRangeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}
	@Test
	public void testIntSoldcountRangeQuery() throws SolrServerException {
		String sql = "soldcount:[22 TO *]";
		QueryResponse response = this.execute("testIntSoldcountRangeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}
	//this is also the greater than query.
	@Test
	public void testWithoutEqualIntSoldcountRangeQuery() throws SolrServerException {
		String sql = "soldcount:{21 TO *}";
		QueryResponse response = this.execute("testIntSoldcountRangeQuery", sql);
		int number=this.printAllFieldsAndValues(response);
		System.out.println("---number of result---"+number);
		Assert.assertTrue(number > 0);
	}
	
	//---------------------------------//
	@Test
	public void testEnd_DateRangeQuery() throws SolrServerException, ParseException {
		long begin=convertStringToDate("2013-04-12 10:20:46").getTime();
		long end=convertStringToDate("2014-04-16 11:00:00").getTime();
		//utcformat(convertStringToDate("2013-04-12 10:20:46")
		//String sql = "end_date:{"+begin+" TO "+end+"}";
		String sql = "end_date:["+begin+" TO "+end+"]";
		//String sql = "end_date:["++" TO 20140416]";
		QueryResponse response = this.execute("testEnd_DateRangeQuery", sql);
		Assert.assertTrue(this.printAllFieldsAndValues(response) > 0);
	}

	private Date convertStringToDate(String string) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		return sdf.parse(string);
	}
	
	private String utcformat(Date date){
		//1995-12-31T23:59:59Z
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		return sdf.format(date);
	}

}
