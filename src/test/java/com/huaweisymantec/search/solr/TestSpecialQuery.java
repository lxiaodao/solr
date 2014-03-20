/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.huaweisymantec.search.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class TestSpecialQuery extends BaseTest {
    public void testString(){
        String sql2="nodevalue_s:[12 TO 13]";
        
        execute("testString",sql2);
    }
    public void testBoolean(){
        String sql2="nodevalue_s:true";
        
        execute("testBoolean",sql2);
    }
    public void testdouble(){
        String sql="_d:[10 TO 11]";
        execute("testdouble",sql);
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
            for (SolrDocument doc : results) {
                String idResult = (String) doc.getFieldValue("id");
                // String nameResult = (String) doc.getFieldValue("name");
                System.out.println(name+"--------------id:" + idResult);

            }
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
