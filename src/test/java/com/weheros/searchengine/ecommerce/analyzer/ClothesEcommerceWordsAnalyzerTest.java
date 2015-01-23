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

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.weheros.searchengine.BaseTest;

public class ClothesEcommerceWordsAnalyzerTest extends BaseTest {
	
	List<String> ids=new ArrayList<String>();
	@Before
	public void setUp() throws SolrServerException, IOException, ParseException{
		   
           SolrInputDocument doc1 = new SolrInputDocument();
           String uuid=UUID.randomUUID().toString();
           doc1.addField("id", uuid);
           String title="韩语琳2015新款春秋装女装大牌简约显瘦拼色连衣裙子 礼服长裙冬";	
           doc1.addField("supply_title", title);           
          
           String description="韩语琳-------名媛时尚美丽绽放----百分百用心始终如一的服务态度1.质感OL连衣裙，名媛范设计。2.修身版型，蝙蝠袖遮掩手臂赘肉，如花苞绽放，修身优雅。3.送模特同款腰带，立即抢购！！！";
       	
           doc1.addField("supply_memo", description);
           doc1.addField("shop_title", "韩语琳空间服饰旗舰店");
           doc1.addField("itel_no", "15788904566");
           doc1.addField("trade_name", "连衣裙");
           doc1.addField("area_name", "广东广州");
           
           doc1.addField("reallegalize", 1);
           doc1.addField("quality_guarantee", 1);
           doc1.addField("sevenlegalize", 1);
           doc1.addField("realname_guarantee",1);
           
           doc1.addField("caution_money",1000.80);
           doc1.addField("low_price",199.00);
           doc1.addField("high_price",399.00);
           
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
	public void testEqualQuery() throws SolrServerException {
		String sql = "supply_title:连衣裙";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);
	}
	/*Assert.assertTrue(descSegs.indexOf("名媛|")!=-1);
	Assert.assertTrue(descSegs.indexOf("时尚|")!=-1);
	Assert.assertTrue(descSegs.indexOf("美丽|")!=-1);
	Assert.assertTrue(descSegs.indexOf("模特|")!=-1);
	Assert.assertTrue(descSegs.indexOf("花苞|")!=-1);
	Assert.assertTrue(descSegs.indexOf("修身|")!=-1);
	Assert.assertTrue(descSegs.indexOf("优雅|")!=-1);
	Assert.assertTrue(descSegs.indexOf("抢购|")!=-1 ||descSegs.indexOf("|抢购")!=-1);*/
	@Test
	public void testDescriptionEqualQuery() throws SolrServerException {
		String sql = "supply_memo:名媛";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery2() throws SolrServerException {
		String sql = "supply_memo:时尚";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery3() throws SolrServerException {
		String sql = "supply_memo:美丽";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery4() throws SolrServerException {
		String sql = "supply_memo:修身";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery5() throws SolrServerException {
		String sql = "supply_memo:优雅";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery6() throws SolrServerException {
		String sql = "supply_memo:抢购";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery7() throws SolrServerException {
		String sql = "supply_memo:花苞";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}
	@Test
	public void testDescriptionEqualQuery8() throws SolrServerException {
		String sql = "supply_memo:模特";
		QueryResponse response = this.execute("testEqualQuery", sql);
		Assert.assertEquals(this.printAllFieldsAndValues(response),1);	
	}

}
