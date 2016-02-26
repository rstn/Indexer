/**
 * Created by boris on 20.02.2016.
 */

import com.simbirsoft.indexer.api.IndexStorage;
import com.simbirsoft.indexer.service.TrieIndexStorage;
import com.simbirsoft.indexer.service.WordIndexes;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _2_CoreTest {

    @Test
    public void _1_testLoader() {
        WordsLoaderProbe wl = new WordsLoaderProbe(new TrieIndexStorage());
        wl.loadFile("src/test/resources/words1.txt");
        IndexStorage is = wl.getIndexStorage();
        assertNotNull(is);
        assertTrue(is instanceof TrieIndexStorage);
        TrieIndexStorage trie = (TrieIndexStorage) is;

        assertEquals(5, trie.getWordCount());
    }

    @Test
    public void _2_testTrie() {
        Trie<String, WordIndexes> trie = new PatriciaTrie<>();

        WordIndexes ws = new WordIndexes();
        ws.add(1);
        ws.add(2);
        trie.put("test", ws);

        ws = new WordIndexes();
        ws.add(5);
        ws.add(6);
        ws.add(7);
        trie.put("toast", ws);

        assertTrue(trie.get("toast").equals(ws));
        assertNull(trie.get("notoast"));
        assertNotNull(trie.get("toast"));
    }


}
