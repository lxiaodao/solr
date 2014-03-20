
/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.huaweisymantec.search.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Assert;

import junit.framework.TestCase;


/**
 * TODO Document BaseTest
 * <p />
 *
 * @author l90003709
 */
public class BaseTest extends TestCase{
    protected final int SOLR_EMBDDED = 1;
    protected final int SOLR_HTTPED = 2;
    
    protected SolrServer getSolrServer(int type) {
        SolrServer server = (type == SOLR_EMBDDED) ? SolrServerHelper.getInstance().getSolrServer()
            : SolrServerHelper.getInstance().getHttpSolrServer();
        return server;
    }
    /**
     * 为了有多个搜索引擎实例实用
     * @param url
     * @return
     */
    protected SolrServer getHttpSolrServer(String url){
        return SolrServerHelper.getInstance().getHttpSolrServer(url);
    }
    
    public void execute(String name,String sql) {
        SolrServer server = this.getSolrServer(this.SOLR_EMBDDED);
        SolrQuery q = new SolrQuery();
        q.setStart(0);
        q.setRows(10);
        
        q.setQuery(sql);
        q.addHighlightField("content");
        q.setHighlight(true).setHighlightSnippets(3);
        q.addSortField("id", SolrQuery.ORDER.desc);
        QueryRequest r = new QueryRequest(q);
        r.setMethod(METHOD.POST);
        QueryResponse response;
        try {
            response = r.process(server);
            SolrDocumentList results = response.getResults();
            long after = System.currentTimeMillis();
            List<String> highightSnippets = new ArrayList<String>();
            int number=0;
            for (SolrDocument doc : results) {
                String idResult = (String) doc.getFieldValue("id");
                // String nameResult = (String) doc.getFieldValue("name");
                System.out.println(name+"--------------id:" + idResult);
                number++;
            }
            Assert.assertTrue(number>0);
        } catch (SolrServerException e) {
           fail(name);
            e.printStackTrace();
        }

    }
}
