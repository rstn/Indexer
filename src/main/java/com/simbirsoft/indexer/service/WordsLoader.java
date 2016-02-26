/**
 * Created by boris on 20.02.2016.
 */

package com.simbirsoft.indexer.service;

import com.simbirsoft.indexer.api.IndexCaseMode;
import com.simbirsoft.indexer.api.IndexStorage;
import com.simbirsoft.indexer.api.TextReadingException;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Загружает слова из файла или потока в хранилище
 *
 * @see IndexStorage
 */
public class WordsLoader {

    protected IndexStorage indexStorage;

    // regex для поиска слов с unicode-символами
    private static Pattern pattern = Pattern.compile("(?U)\\w+");

    public WordsLoader(IndexStorage indexStorage) {
        this.indexStorage = indexStorage;
    }

    public void loadFile(String filename) {
        loadFile(filename, IndexCaseMode.SENSITIVE);
    }

    public void loadFile(String filename, IndexCaseMode caseSensitive) {
        try (InputStream fis = new FileInputStream(filename)) {
            loadFile(fis, caseSensitive);
        } catch (IOException e) {
            throw new TextReadingException(e);
        }
    }

    public void loadFile(InputStream inputStream, IndexCaseMode caseSensitive) {
        indexStorage.clear();
        try (InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            int lineOffset = 0;
            while (true) {
                line = br.readLine();
                if (line == null) break;
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String word = caseSensitive == IndexCaseMode.SENSITIVE ? matcher.group() : matcher.group().toLowerCase();
                    indexStorage.addWord(word, lineOffset + matcher.start());
                }
                lineOffset += line.length();
            }
        } catch (IOException e) {
            throw new TextReadingException(e);
        }
    }

}
