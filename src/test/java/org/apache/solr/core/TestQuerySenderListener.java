/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.solr.core;

import org.apache.solr.util.AbstractSolrTestCase;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.common.params.EventParams;
import org.apache.lucene.store.Directory;

public class TestQuerySenderListener extends AbstractSolrTestCase {

  @Override public String getSchemaFile() { return "schema.xml"; }
  @Override public String getSolrConfigFile() { return "solrconfig-querysender.xml"; }

  public void testRequestHandlerRegistry() {
    // property values defined in build.xml
    SolrCore core = h.getCore();

    assertEquals( 1, core.firstSearcherListeners.size() );
    assertEquals( 1, core.newSearcherListeners.size() );
  }

  public void testSearcherEvents() throws Exception {
    SolrCore core = h.getCore();
    SolrEventListener newSearcherListener = core.newSearcherListeners.get(0);
    assertTrue("Not an instance of QuerySenderListener", newSearcherListener instanceof QuerySenderListener);
    QuerySenderListener qsl = (QuerySenderListener) newSearcherListener;

    SolrIndexSearcher currentSearcher = core.getSearcher().get();
    qsl.newSearcher(currentSearcher, null);//test new Searcher
    MockQuerySenderListenerReqHandler mock = (MockQuerySenderListenerReqHandler) core.getRequestHandler("mock");
    assertNotNull("Mock is null", mock);
    String evt = mock.req.getParams().get(EventParams.EVENT);
    assertNotNull("Event is null", evt);
    assertTrue(evt + " is not equal to " + EventParams.FIRST_SEARCHER, evt.equals(EventParams.FIRST_SEARCHER) == true);
    Directory dir = currentSearcher.getReader().directory();
    SolrIndexSearcher newSearcher = new SolrIndexSearcher(core, core.getSchema(), "testQuerySenderListener", dir, true, false);

    qsl.newSearcher(newSearcher, currentSearcher);
    evt = mock.req.getParams().get(EventParams.EVENT);
    assertNotNull("Event is null", evt);
    assertTrue(evt + " is not equal to " + EventParams.NEW_SEARCHER, evt.equals(EventParams.NEW_SEARCHER) == true);
  }

}

