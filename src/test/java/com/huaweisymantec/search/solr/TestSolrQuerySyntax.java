
package com.huaweisymantec.search.solr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;


public class TestSolrQuerySyntax extends BaseTest {
    public void  testQuerySyntax(){
     
        try {
           
            SolrServer server =getSolrServer(SOLR_EMBDDED);
            //
            Test.readBook(server, new File(SolrServerHelper.path+"/txt/关于hadoop的使用经验.txt"));
            //
            String sql="name:hadoop";
            query(server,sql);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    public static void query(SolrServer server, String text) throws SolrServerException, IOException {
        long before = System.currentTimeMillis();
        SolrQuery q = new SolrQuery();
        q.setStart(0);
        q.setRows(10);
        q.setQuery(text);
        q.addHighlightField("content");
        q.setHighlight(true).setHighlightSnippets(3);
        q.addSortField("id", SolrQuery.ORDER.desc);     
        QueryRequest r = new QueryRequest(q);
        r.setMethod(METHOD.POST);
        QueryResponse response = r.process(server);
        SolrDocumentList results = response.getResults();
        long after = System.currentTimeMillis();
        
      
        System.out.println("--------查询所用的时间："+(after - before)+"  ---------");
        System.out.println("--------查询结果的总长度："+ results.getNumFound()+"  ---------");
        
        List<String> highightSnippets = new ArrayList<String>();
        for (SolrDocument doc : results) {   
            String idResult = (String) doc.getFieldValue("id");            
            String nameResult = (String) doc.getFieldValue("name");
            System.out.println("id:" + idResult + ",name:" + nameResult);
            Map<String, List<String>> map = response.getHighlighting().get(idResult);
            if (map != null) {
                for (String key : map.keySet()) {
                    highightSnippets = map.get(key);
                    for (String line : highightSnippets) {
                        System.out.println(line);
                        System.out.println("----------分割线----------");
                    }
                }
            }
            /*if (response.getHighlighting().get(idResult) != null) {
                highightSnippets = response.getHighlighting().get(idResult).get("十八春");
                for (String line : highightSnippets) {
                    System.out.println(line);
                }
            }*/
            //System.out.println("--------查询内容是："+nameResult +"   查询结果对应的Id是: "+ idResult + "  ---------");   
        }
    }
}
