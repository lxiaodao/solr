/**
 * Copyright HZCW (He Zhong Chuang Wei) Technologies Co.,Ltd. 2013-2015. All rights reserved.
 */
	
package com.weheros.searchengine.ecommerce.analyzer;

import java.util.Collection;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import com.weheros.searchengine.BaseTest;

/**
 * @ClassName: QueryAllDocumentTest
 * @author Administrator
 * @date 2015年1月21日 下午2:04:58
 */
public class QueryAllDocumentTest extends BaseTest {
	SolrServer server =this.getSolrServer(this.SOLR_EMBDDED);
	
	@Test
	public void testLoadDocuments() throws SolrServerException{
		  String sql="id:*";
          SolrQuery q = new SolrQuery();
          q.setStart(0);
          q.setRows(10);
          
          q.setQuery(sql);
          //q.addHighlightField("content");
          q.setHighlight(true).setHighlightSnippets(3);
          q.addSortField("id", SolrQuery.ORDER.desc);
          QueryRequest r = new QueryRequest(q);
          r.setMethod(METHOD.POST);
          QueryResponse response;
          
              response = r.process(server);
              SolrDocumentList results = response.getResults();
              
              for (SolrDocument doc : results) {
           
                  Collection<String> names=doc.getFieldNames();
                  Iterator<String> it=names.iterator();
                  while(it.hasNext()){
                	  String fieldname=it.next();
                	 
                	  System.out.println("-------fieldname is-------" + fieldname+"--------------value is--------------"+ doc.getFieldValue(fieldname));
                  }

              }
	}

}


