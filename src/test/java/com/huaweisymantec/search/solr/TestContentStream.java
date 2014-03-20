
package com.huaweisymantec.search.solr;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import junit.framework.TestCase;

public class TestContentStream extends BaseTest {

  

    String file = "e:/Document/JPA_Basic.pdf";
    String file2="e:/Document/hadoop分析.doc";
    public void testEmbeddedIndexFile(){
        indexContentStream(SOLR_EMBDDED);
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.execute("testEmbeddedIndexFile", "number_ti:[2 TO 3]");       
    }
    public void testDateRangQuery(){
        //1976-03-06T23:59:59.999Z
        this.execute("testDateRangQuery", "TestDate:[2010-01-05T00:00:00Z TO 2010-01-07T00:00:00Z]");
    }
    public void testNumberRangQuery(){
        
        this.execute("testNumberRangQuery", "testnumber:[4 TO 5] ");
    }
  public void testLongNumberRangQuery(){
      long time1=DateUtil.parseDate("2010-01-02").getTime();
       long time2=DateUtil.parseDate("2010-01-06").getTime();
        
        this.execute("testLongNumberRangQuery", "timelong_tl:["+time1+" TO "+time2+"]");
    }
    public void testHttpIndexFile(){
        //indexContentStream(SOLR_HTTPED);
        //TestSolrQuerySyntax.query(getSolrServer(SOLR_EMBDDED), text);
    }
    private void indexContentStream(int type) {
        SolrInputDocument doc = new SolrInputDocument();
      /*  String uuid=UUID.randomUUID().toString();
       
        doc.addField("id", uuid);
        doc.addField("name", "JPA基础知识文档");
        doc.addField("number_s", 1.5);
        doc.addField("date_s", new Date());*/
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        try {
            up.addFile(new File(file));
            //up.addFile(new File(file2));
            //up.addContentStream(contentStream)
            up.setParam("literal.id", UUID.randomUUID().toString());          
           
            up.setParam("literal.TestDate", "2010-01-05T00:00:00Z");
            up.setParam("literal.number_ti", "2");
           
            up.setParam("literal.testnumber", "3.5");
            up.setParam("literal.timelong_tl", DateUtil.parseDate("2010-01-02").getTime()+"");
            
            
            up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            SolrServer server= getSolrServer(type);
            //server.add(doc);
            server.request(up);
            //server.commit();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
