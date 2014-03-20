/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.huaweisymantec.search.solr;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;

public class TextFileIndexTest extends BaseTest {

    public void testTextfile() {
        String file = "D:/workspace/solr/src/test/resources/index/chinese_index.txt";
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        try {
            up.addFile(new File(file));
            //up.addFile(new File(file2));
            //up.addContentStream(contentStream)
            up.setParam("literal.id", UUID.randomUUID().toString());


            up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            SolrServer server = this.getSolrServer(this.SOLR_EMBDDED);
            
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
