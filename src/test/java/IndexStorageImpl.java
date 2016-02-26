/**
 * Created by boris on 22.02.2016.
 */

import com.simbirsoft.indexer.api.IndexStorage;
import com.simbirsoft.indexer.service.WordIndexes;

import java.util.Map;

/**
 * Абстрактный класс с общими функциями для хранения индекса.
 * Применяется в тесте производительности
 */
public abstract class IndexStorageImpl implements IndexStorage {

    protected Map<String, WordIndexes> map;

    IndexStorageImpl(Map<String, WordIndexes> map) {
        this.map = map;
    }

    @Override
    public void addWord(String word, Integer position) {
        WordIndexes stats = map.get(word);
        if (stats == null) {
            stats = new WordIndexes();
            map.put(word, stats);
        }
        stats.add(position);
    }

    @Override
    public WordIndexes getIndexes(String word) {
        return map.get(word);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
