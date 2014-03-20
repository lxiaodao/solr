/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 *
 * 
 */
package com.huaweisymantec.search.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.SentenceTokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;


/**
 * Document SmartChineseTokenizerFactory
 * <p />
 *
 * @author s00108907
 */
public class SmartChineseTokenizerFactory extends BaseTokenizerFactory {

    /** 
     * {@inheritDoc}
     */
    @Override
    public Tokenizer create(Reader in) {
        return new SentenceTokenizer(in);
    }

}
