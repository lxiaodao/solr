/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 */
package org.apache.solr.common.params;

import junit.framework.TestCase;

/**
 * Unit Test Case for {@link org.apache.solr.common.params.ModifiableSolrParams
 * ModifiableSolrParams}
 * 
 * @author kkumar
 */
public class ModifiableSolrParamsTest extends TestCase
{

  @Override
  public void setUp()
  {
    modifiable = new ModifiableSolrParams();
  }


  @Override
  public void tearDown()
  {
    modifiable.clear();
  }


  public void testAdd()
  {

    String key = "key";
    String[] values = new String[1];
    values[0] = null;
    modifiable.add(key, values);
    String[] result = modifiable.getParams(key);
    assertEquals("params", values, result);
  }


  public void testAddNormal()
  {

    String key = "key";
    String[] helloWorld = new String[] { "Hello", "World" };
    String[] universe = new String[] { "Universe" };
    String[] helloWorldUniverse = new String[] { "Hello", "World", "Universe" };
    modifiable.add(key, helloWorld);
    assertEquals("checking Hello World: ", helloWorld, modifiable.getParams(key));

    modifiable.add(key, universe);
    String[] result = modifiable.getParams(key);
    compareArrays("checking Hello World Universe ", helloWorldUniverse, result);
  }


  public void testAddNull()
  {

    String key = "key";
    String[] helloWorld = new String[] { "Hello", "World" };
    String[] universe = new String[] { null };
    String[] helloWorldUniverse = new String[] { "Hello", "World", null };
    modifiable.add(key, helloWorld);
    assertEquals("checking Hello World: ", helloWorld, modifiable.getParams(key));

    modifiable.add(key, universe);
    String[] result = modifiable.getParams(key);
    compareArrays("checking Hello World Universe ", helloWorldUniverse, result);
  }


  public void testOldZeroLength()
  {

    String key = "key";
    String[] helloWorld = new String[] {};
    String[] universe = new String[] { "Universe" };
    String[] helloWorldUniverse = new String[] { "Universe" };
    modifiable.add(key, helloWorld);
    assertEquals("checking Hello World: ", helloWorld, modifiable.getParams(key));

    modifiable.add(key, universe);
    String[] result = modifiable.getParams(key);
    compareArrays("checking Hello World Universe ", helloWorldUniverse, result);
  }


  public void testAddPseudoNull()
  {

    String key = "key";
    String[] helloWorld = new String[] { "Hello", "World" };
    String[] universe = new String[] { "Universe", null };
    String[] helloWorldUniverse = new String[] { "Hello", "World", "Universe", null };
    modifiable.add(key, helloWorld);
    assertEquals("checking Hello World: ", helloWorld, modifiable.getParams(key));

    modifiable.add(key, universe);
    String[] result = modifiable.getParams(key);
    compareArrays("checking Hello World Universe ", helloWorldUniverse, result);
  }


  private void compareArrays(String prefix,
                             String[] expected,
                             String[] actual)
  {
    assertEquals(prefix + "length: ", expected.length, actual.length);
    for (int i = 0; i < expected.length; ++i)
    {
      assertEquals(prefix + " index  " + i, expected[i], actual[i]);
    }
  }

  private ModifiableSolrParams modifiable;
}
