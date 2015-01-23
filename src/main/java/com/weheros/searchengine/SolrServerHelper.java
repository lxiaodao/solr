/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.weheros.searchengine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

/**
 * 
 * Document SolrServerHelper 搜索引擎服务器
 * <p />
 * 
 * @author l90003768
 */
public final class SolrServerHelper {

    public final static String path = "D:/eclipse/workspace/solr/src/solr/resources/solr";

    /**
     * 搜索引擎服务器,嵌入式
     */
    private static SolrServer solrServer = null;

    /**
     * solr核心容器
     */
    private static CoreContainer coreContainer = null;

    /**
     * 锁
     */
    private static final Lock lock = new ReentrantLock();

    /**
     * 搜索引擎服务器,http
     */
    private SolrServer httpSolrServer = null;

    private final static byte[] bytelock = new byte[0];

    private static SolrServerHelper helper;

    public static SolrServerHelper getInstance() {
        if (null == helper) {
            helper = new SolrServerHelper();
        }
        return helper;
    }

    private SolrServerHelper() {

    }

    /**
     * 关闭服务
     */
    public static void close() {
        if (null != coreContainer) {
            coreContainer.shutdown();
        }
    }
    /**
     * 请注意，每次返回新的实例
     * @param url
     * @return
     */
    public SolrServer getHttpSolrServer(String url) {
        SolrServer aserver = null;
       
            aserver = new HttpSolrServer(url);
     
        return aserver;
    }

    public SolrServer getHttpSolrServer() {
        String serverurl = "http://localhost:8000/solr";
        if (null == httpSolrServer) {

            synchronized (bytelock) {
                
                    httpSolrServer = new HttpSolrServer(serverurl);
             
            }
        }
        return httpSolrServer;
    }

    /**
     * 得到SolrServer实例
     * 
     * @return
     */
    public SolrServer getSolrServer() {
        if (null == solrServer) {
            lock.lock();
            {
                if (null == solrServer) {
                    //System.setProperty("solr.solr.home", path);
                    CoreContainer coreContainer = new CoreContainer(path);
                    coreContainer.load();
                    solrServer = new EmbeddedSolrServer(coreContainer, "collection1");
                   /* CoreContainer.Initializer initializer = new CoreContainer.Initializer();
                    try {
                        coreContainer = initializer.initialize();
                        coreContainer.setPersistent(true);
                        solrServer = new EmbeddedSolrServer(coreContainer, "collection1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }*/

                }
            }
            lock.unlock();
        }
        return solrServer;
    }
}
