/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */
package com.huaweisymantec.search.solr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.SolrInputDocument;


public class MutipleCoreTest extends TestCase {
    
    
    public void testWsIndex(){
        try {
            
            SolrServer server =SolrServerHelper.getInstance().getHttpSolrServer("http://localhost:8000/solr/ws");
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", UUID.randomUUID().toString());
            doc1.addField("name", "北京2008年奥运会");           
          
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            docs.add(doc1);
            //docs.add(doc2);

            server.add(docs);
            
            server.commit();           
           

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void testDmsTextfile() {
        String file = "D:/workspace/solr/src/test/resources/index/chinese_index.txt";
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        try {
            up.addFile(new File(file));
            //up.addFile(new File(file2));
            //up.addContentStream(contentStream)
            up.setParam("literal.id", UUID.randomUUID().toString());


            up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            SolrServer server =SolrServerHelper.getInstance().getHttpSolrServer("http://localhost:8000/solr/dms");
            
            server.request(up);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
