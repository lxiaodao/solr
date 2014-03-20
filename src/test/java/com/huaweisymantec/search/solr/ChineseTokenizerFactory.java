/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 *
 * 
 */

package com.huaweisymantec.search.solr;

import java.io.Reader;
import java.util.Map;

import net.paoding.analysis.analyzer.PaodingTokenizer;
import net.paoding.analysis.analyzer.TokenCollector;
import net.paoding.analysis.analyzer.impl.MaxWordLengthTokenCollector;
import net.paoding.analysis.analyzer.impl.MostWordsTokenCollector;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;

/**
 * TODO Document ChineseTokenizerFactory
 * <p />
 * 
 * @author s00108907
 */
public class ChineseTokenizerFactory extends BaseTokenizerFactory {

    /**
     * 
     * 最多切分 默认模式
     */

    public static final String MOST_WORDS_MODE = "most-words";

    /**
     * 
     * 按最大切分
     */

    public static final String MAX_WORD_LENGTH_MODE = "max-word-length";
    
    /* 线程内共享 */
    private ThreadLocal<PaodingTokenizer> tokenizerLocal = new ThreadLocal<PaodingTokenizer>();

    private String mode = null;

    public void setMode(String mode) {
        if (mode == null || MOST_WORDS_MODE.equalsIgnoreCase(mode) || "default".equalsIgnoreCase(mode)) {
            this.mode = MOST_WORDS_MODE;
        } else if (MAX_WORD_LENGTH_MODE.equalsIgnoreCase(mode)) {
            this.mode = MAX_WORD_LENGTH_MODE;
        } else {
            throw new IllegalArgumentException("不合法的分析器Mode参数设置:" + mode);
        }
    }

    @Override
    public void init(Map<String, String> args) {
        super.init(args);
        setMode(args.get("mode"));
    }

   /* public TokenStream create(Reader input) {
        PaodingTokenizer tokenizer = tokenizerLocal.get();
        
        if(tokenizer == null) {
            tokenizer = new PaodingTokenizer(input, PaodingMaker.make(), createTokenCollector());
            tokenizerLocal.set(tokenizer);
        } else {
            try {
                tokenizer.reset();
            } catch (IOException e) {
                tokenizer = new PaodingTokenizer(input, PaodingMaker.make(), createTokenCollector());
                tokenizerLocal.set(tokenizer);
            }
        }
        return tokenizer;
    }*/

    private TokenCollector createTokenCollector() {
        if (MOST_WORDS_MODE.equals(mode)) {
            return new MostWordsTokenCollector();
        }
        if (MAX_WORD_LENGTH_MODE.equals(mode)) {
            return new MaxWordLengthTokenCollector();
        }
        throw new Error("never happened");
    }

    @Override
    public Tokenizer create(Reader arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
