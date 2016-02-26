/**
 * Created by boris on 20.02.2016.
 */
package com.simbirsoft.indexer.api;

import com.simbirsoft.indexer.service.TrieIndexStorage;
import com.simbirsoft.indexer.service.WordsLoader;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Класс-индекс. Позволяет по заданному слову находить все вхождения (позиции) его в файле.
 */
public class WordIndex {

    private IndexStorage indexStorage;
    private IndexCaseMode indexCaseMode;
    private WordsLoader loader;

    /**
     * По умолчанию создается структура Trie, и учитывается регистр слов.
     */
    public WordIndex() {
        this(IndexCaseMode.SENSITIVE);
    }

    /**
     * @param indexCaseMode - если true, учитывается регистр
     */
    public WordIndex(IndexCaseMode indexCaseMode) {
        this(new TrieIndexStorage(), checkNotNull(indexCaseMode));
    }

    /**
     * @param indexStorage  - хранилще для индекса
     * @param caseSensitive - если true, учитывается регистр
     */
    public WordIndex(IndexStorage indexStorage, IndexCaseMode caseSensitive) {
        this.indexCaseMode = checkNotNull(caseSensitive);
        this.indexStorage = checkNotNull(indexStorage);
        loader = new WordsLoader(indexStorage);
    }


    /**
     * Возвращает список позиций слова в файле.
     * Подсчет позиций не учитывает символы перевода строки.
     * Отсчет позиций начинается с нуля.
     * Если данного слова в файле нет, то возвращается null.
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        return indexStorage.getIndexes(
                indexCaseMode == IndexCaseMode.SENSITIVE ? searchWord : searchWord.toLowerCase());
    }


    /**
     * Загрузка данных из файла и построение индекса.
     *
     * @param filename путь к файлу с текстом
     */
    public void loadFile(String filename) {
        loader.loadFile(checkNotNull(filename), indexCaseMode);
    }

}
