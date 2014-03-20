package com.huaweisymantec.search.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

public class Test {
    
    private static Map<File, List<String>> fileMap = new HashMap<File, List<String>>();
    
	public  static void main(String[]args) throws ParserConfigurationException, IOException, SAXException, SolrServerException{
		String path="D:/workspace/solr1.3";
		
		/*File home = new File( "E:/apache-solr-1.3.0/example/multicore" );
		if(!home.exists()){
			home.mkdirs();
		}
	    File f = new File( home, "solr.xml" );
	    CoreContainer container = new CoreContainer();
	    container.load( "E:/apache-solr-1.3.0/example/multicore", f );
	    EmbeddedSolrServer server = new EmbeddedSolrServer( container, "core name as defined in solr.xml" );
	    container.shutdown();*/
		
		System.setProperty("solr.solr.home", path+"/resources");
		CoreContainer.Initializer initializer = new CoreContainer.Initializer();
		CoreContainer coreContainer = initializer.initialize();
		coreContainer.setPersistent(true);
		SolrServer server = new EmbeddedSolrServer(coreContainer, "");
	    
		for (int i = 0; i<20;i++) {
		    File file = new File(path+"/resources/txt/�Ű��᳤ƪС˵��.txt");
	        readBook(server, file);
	        
	        file = new File(path+"/resources/txt/���ĵ���.txt");
	        readBook(server, file);
	        
	        System.out.println(i);
		}
		//server.optimize();
		
		query(server, "*:*");
		
		query(server, "content:���");
		
		query(server, "content:�˲���");
		
		query(server, "content:����");
		
		query(server, "content:���");
		
		query(server, "content:");
		
        coreContainer.shutdown();
	}
	
	private static void query(SolrServer server, String text) throws SolrServerException, IOException {
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
        System.out.println("--------��ѯ���õ�ʱ�䣺"+(after - before)+"  ---------");
        System.out.println("--------��ѯ�����ܳ��ȣ�"+ results.getNumFound()+"  ---------");
        
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
                    }
                }
            }
            /*if (response.getHighlighting().get(idResult) != null) {
                highightSnippets = response.getHighlighting().get(idResult).get("ʮ�˴�");
                for (String line : highightSnippets) {
                    System.out.println(line);
                }
            }*/
            //System.out.println("--------"+nameResult +"  ��� "+ idResult + "  ---------");   
        }
	}
	
	public static void readBook(SolrServer server, File file) throws IOException, SolrServerException {
	    List<SolrInputDocument> list = new ArrayList<SolrInputDocument>();
	    List<String> fileContent = getFileContent(file);
	    for (String line : fileContent) {
	        SolrInputDocument doc = new SolrInputDocument();
            doc.setField("id", UUID.randomUUID());
            doc.setField("name", file.getName());
            doc.setField("content", line);
            
            list.add(doc);
	    }
	    server.add(list);
	   
        server.commit();
	}
	
	private static List<String> getFileContent(File file) throws IOException {
	    List<String> list = fileMap.get(file);
	    if (list != null) {
	        return list;
	    }
	    list = new ArrayList<String>();
	    BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = reader.readLine()) != null) {
            if (line.trim().equals("")) {
                continue;
            }
            list.add(line);
        }
        fileMap.put(file, list);
        return list;
	}
	
}
