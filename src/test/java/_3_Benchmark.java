
/**
 * Created by boris on 21.02.2016.
 */

import com.simbirsoft.indexer.api.IndexStorage;
import com.simbirsoft.indexer.service.TrieIndexStorage;
import com.simbirsoft.indexer.service.WordIndexes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Сравнение производительности Trie и Map
 */
public class _3_Benchmark {

    private static Logger logger = LoggerFactory.getLogger(_3_Benchmark.class);

    private class HashMapIndexStorage extends IndexStorageImpl {
        public HashMapIndexStorage() {
            super(new HashMap<String, WordIndexes>());
        }
    }

    private class TreeMapIndexStorage extends IndexStorageImpl {
        public TreeMapIndexStorage() {
            super(new TreeMap<String, WordIndexes>());
        }
    }


    private String[] searchWords = {"",
            "strongest", "which", "zymogenes", "index", "in", "about", "the", "begin", "began", "begun",
            "before", "test", "zucchini", "zaatakowanym", "thought", "might",
            "elettroencefalografia", "incommensurabilissimae", "buitenboordkoelwatersysteem",
            "stop", "learning", "faction", "про", "become",
            "cannot", "where", "tulajdonságosztályokat", "guanin", "nejnevytříbenějším", "nieprzylepnej",
            "высокопревосходительства", "предгильбертовым", "wymigiwaniu", "niepięćdziesięciokilkoletnich",
            "expect", "niepięćdziesięciokilkoletniej", "проба", "aav", "vab", "bafafqw", "qwfqw", "qfq", "aaasfas",
            "sześćdziesięciopięcioletniemu", "trzydziestoczteroipółletniego", "niesiedemdziesięciomilimetrowe",
            "after", "niesiedemdziesięciomilimetrowi", "nietrzydziestoczteroipółletnią", "osiemdziesięciosiedmioletniego",
            "osiemdziesięciosiedmioletniemu", "siedemdziesięciosześcioletniej", "проверка",
            "ab", "abc", "abcd", "abcdd", "defgdsh", "hjiksdh", "oplqdsh", "vpvbbsd", "zxdasdh", "fgbdss", "dhdsaa", "bb",
            "siedemdziesięciosześcioletnimi", "niesześćdziesięciopięcioletnich", "morning",
            "nietrzydziestoczteroipółletnich", "siedemdziesięcioośmioipółletnią", "nietrzydziestoczteroipółletniego"
    };

    private Random rnd = new Random();

    private long runSearch(IndexStorage indexStorage) {
        long startTime = System.nanoTime();
        for (String word : searchWords) {
            indexStorage.getIndexes(word);
        }
        long stopTime = System.nanoTime();
        return (stopTime - startTime) / 1000;
    }

    @Test
    public void runBenchmark() {
        logger.info("   Words  |  Search time (ms) ");
        for (IndexStorage is : new IndexStorage[]{
                new TrieIndexStorage(),
                new HashMapIndexStorage(),
                new TreeMapIndexStorage()
        }) {
            logger.info("Class " + is.getClass().getName());
            int wordCount = 0;
            for (Integer testSize : new Integer[]{
                    10000,
                    30000,
                    60000,
                    100_000
            }) {
                System.gc();
                rnd.setSeed(1);
                wordCount += testSize;
                StringBuilder sb = new StringBuilder("");
                /**
                 * Добавляем случайные слова в словарь
                 */
                for (int w = 0; w < testSize; w++) {
                    String word = "";
                    do {
                        if (w > 0) {
                            int wordLength = 1 + rnd.nextInt(32);
                            sb.setLength(wordLength);
                            for (int ch = 0; ch < wordLength; ch++) {
                                sb.setCharAt(ch, new Character((char) (rnd.nextInt(26) + 'a')));
                            }
                            word = sb.toString();
                        }
                        if (is.getIndexes(word) == null) break;
                    } while (w > 0);
                    is.addWord(word, w);
                }
                long time = runSearch(is);
                logger.info((new Formatter()).format(" %7d  | %d",
                        wordCount, time).toString());
            }
            is.clear();
        }
    }

}
