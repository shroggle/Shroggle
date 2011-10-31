/*********************************************************************
 *                                                                   *
 * Copyright (c) 2002-2006 by Survey Software Services, Inc.         *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.statistics.searchengines;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class SearchEngine {
    private SearchEngine() {
    }

    public static class Words {
        String word = "";
        long count = 0;

        public String getWord() {
            return word;
        }

        public void setWord(String aWord) {
            this.word = aWord;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long aCount) {
            this.count = aCount;
        }
    }

    public static class Attributes {
        Map<String, Long> wordmap = new HashMap<String, Long>();
        String key = "";
        long visitors = 0;
        private AtomicLong google = new AtomicLong(0);
        private AtomicLong msn = new AtomicLong(0);
        private AtomicLong yahoo = new AtomicLong(0);
        private AtomicLong aol = new AtomicLong(0);
        private AtomicLong other = new AtomicLong(0);
        List<Words> wordList = new ArrayList<Words>();

        public void setWordList(List<Words> aList) {
            this.wordList = aList;
        }

        public List<Words> getWordList() {
            return wordList;
        }

        public void setKey(String aKey) {
            this.key = aKey;
        }

        public String getKey() {
            return key;
        }

        public long getVisitors() {
            return visitors;
        }

        public long getGoogleVisitors() {
            return getGoogle();
        }

        public long getMsnVisitors() {
            return getMsn();
        }

        public long getYahooVisitors() {
            return getYahoo();
        }

        public long getAolVisitors() {
            return getAol();
        }

        public long getOtherVisitors() {
            return getOther();
        }

        public Map<String, Long> getWordMap() {
            return wordmap;
        }

        public void visitorIncrement() {
            visitors++;
        }

        public void setNewWord(String word) {
            if (SearchEngineUtils.isStringEmpty(word)) return;
            Long count = wordmap.get(word);
            if (count == null) wordmap.put(word, 1L);
            else wordmap.put(word, count + 1);
        }

        public void sortWords() {
            Map<String, Long> hm = getWordMap();
            Comparator cmp = new Comparator() {
                public int compare(Object o1, Object o2) {
                    long l1 = ((Words) o1).getCount();
                    long l2 = ((Words) o2).getCount();
                    if (l1 < l2) return 1;
                    if (l1 == l2) return 0;
                    return -1;
                }
            };
            List<Words> wordList = new ArrayList<Words>();
            for (String key : hm.keySet()) {
                Long wCount = hm.get(key);
                Words w = new Words();
                w.setCount(wCount);
                w.setWord(key);
                wordList.add(w);
            }
            Collections.sort(wordList, cmp);
            setWordList(wordList);
        }

        public long getGoogle() {
            return google.get();
        }

        public long getMsn() {
            return msn.get();
        }

        public long getYahoo() {
            return yahoo.get();
        }

        public long getAol() {
            return aol.get();
        }

        public long getOther() {
            return other.get();
        }

        public void incrementAol() {
            aol.incrementAndGet();
        }

        public void incrementGoogle() {
            google.incrementAndGet();
        }

        public void incrementYahoo() {
            yahoo.incrementAndGet();
        }

        public void incrementOther() {
            other.incrementAndGet();
        }

        public void incrementMsn() {
            msn.incrementAndGet();
        }
    }

    static String getOrigin(String referrer) {
        if (referrer == null) return null;
        int ind = referrer.indexOf("?");
        if (ind <= 0) return referrer;
        return referrer.substring(0, ind - 1);
    }


}
