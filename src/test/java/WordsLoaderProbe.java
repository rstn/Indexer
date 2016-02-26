import com.simbirsoft.indexer.api.IndexStorage;
import com.simbirsoft.indexer.service.TrieIndexStorage;
import com.simbirsoft.indexer.service.WordsLoader;

/**
 * Created by boris on 24.02.2016.
 */
public class WordsLoaderProbe extends WordsLoader {
    public WordsLoaderProbe(TrieIndexStorage trieIndexStorage) {
        super(trieIndexStorage);
    }

    public IndexStorage getIndexStorage() {
        return indexStorage;
    }

}
