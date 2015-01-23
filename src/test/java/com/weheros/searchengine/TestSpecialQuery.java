/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.weheros.searchengine;

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
    public void testString() throws SolrServerException{
        String sql2="nodevalue_s:[12 TO 13]";
        
        execute("testString",sql2);
    }
    public void testBoolean() throws SolrServerException{
        String sql2="nodevalue_s:true";
        
        execute("testBoolean",sql2);
    }
    public void testdouble() throws SolrServerException{
        String sql="_d:[10 TO 11]";
        execute("testdouble",sql);
    }
   

    
}
