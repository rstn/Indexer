/**
 * Created by boris on 20.02.2016.
 */
package com.simbirsoft.indexer.api;

import com.simbirsoft.indexer.service.WordIndexes;

/**
 * Интерфейс индексного хранилища
 */
public interface IndexStorage {
    /**
     * Добавляет слово и его позицию в индекс
     */
    void addWord(String word, Integer position);

    /**
     * Возвращает список позиций для слова word,
     * или null если такого слова в индексе нет
     */
    WordIndexes getIndexes(String word);

    /**
     * Очищает индекс
     */
    void clear();
}
