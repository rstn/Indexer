/**
 * Created by boris on 20.02.2016.
 */
package com.simbirsoft.indexer.service;

import com.simbirsoft.indexer.api.IndexStorage;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

/**
 * Реализация индексного хранилища с использованием Trie
 */
public class TrieIndexStorage implements IndexStorage {
    private Trie<String, WordIndexes> trie = new PatriciaTrie<>();

    public long getWordCount() {
        return trie.size();
    }

    @Override
    public void addWord(String word, Integer position) {
        WordIndexes stats = trie.get(word);
        if (null == stats) {
            stats = new WordIndexes();
            trie.put(word, stats);
        }
        stats.add(position);
    }

    @Override
    public WordIndexes getIndexes(String word) {
        return trie.get(word);
    }

    @Override
    public void clear() {
        trie.clear();
    }

}
