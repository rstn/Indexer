/**
 * Created by boris on 24.02.2016.
 */
package com.simbirsoft.indexer.api;

import java.io.IOException;

/**
 * Исключение при ошибке чтения текстового файлв
 */
public class TextReadingException extends RuntimeException {

    public TextReadingException(IOException e) {
        super(e);
    }
}
