import com.simbirsoft.indexer.api.TextReadingException;
import com.simbirsoft.indexer.api.IndexCaseMode;
import com.simbirsoft.indexer.api.WordIndex;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by boris on 20.02.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class _1_APITest {

    @Test
    public void _1_testLoader() {
        WordIndex wi = new WordIndex();
        wi.loadFile("src/test/resources/words1.txt");
        assertNotNull(wi.getIndexes4Word("раз"));
        assertEquals(3, wi.getIndexes4Word("раз").size());
        assertEquals(2, wi.getIndexes4Word("да").size());
        assertNull(wi.getIndexes4Word("д"));
        assertNull(wi.getIndexes4Word("а"));
        assertNull(wi.getIndexes4Word(""));

        assertNull(wi.getIndexes4Word("Java"));
        assertNotNull(wi.getIndexes4Word("раз"));
        wi.loadFile("src/test/resources/words2.txt");

        // после чтения другого файла
        assertNotNull(wi.getIndexes4Word("Java"));
        assertNull(wi.getIndexes4Word("много"));
    }


    @Test
    public void _2_testFault() {
        WordIndex wi = new WordIndex();
        try {
            wi.loadFile(null);
            fail("Should throw exception");
        } catch (NullPointerException e) {
            // предполагаем, что бросилось исключение
        }

        try {
            wi.loadFile("- no such file test -");
            fail("Should throw exception");
        } catch (TextReadingException e) {
            // предполагаем, что бросилось исключение
        }
        assertNull(wi.getIndexes4Word(null));
        assertNull(wi.getIndexes4Word("а"));
        assertNull(wi.getIndexes4Word(""));
    }

    @Test
    public void _3_testCaseSensitiveSearch() {
        WordIndex wi = new WordIndex();
        wi.loadFile("src/test/resources/hamlet.txt");
        assertEquals(113, wi.getIndexes4Word("Hamlet").size());
        assertNull(wi.getIndexes4Word("hamlet"));
    }

    @Test
    public void _4_testCaseInsensitiveSearch() {
        WordIndex wi = new WordIndex(IndexCaseMode.INSENSITIVE);
        wi.loadFile("src/test/resources/voynaimir.txt");
        assertNotNull(wi.getIndexes4Word("Он").size());
        assertEquals(7493, wi.getIndexes4Word("Он").size());
        assertEquals(2752, wi.getIndexes4Word("она").size());
        assertEquals(14, wi.getIndexes4Word("дуб").size());
        assertNull(wi.getIndexes4Word(""));
    }

    @Test
    public void _5_testResultPosition() {
        WordIndex wi = new WordIndex(IndexCaseMode.INSENSITIVE);
        wi.loadFile("src/test/resources/words1.txt");
        Set<Integer> result1 = wi.getIndexes4Word("РАЗ");
        assertNotNull(result1);

        Set<Integer> result2 = wi.getIndexes4Word("раз");
        assertNotNull(result2);
        assertSame(result1, result2);

        Set<Integer> expected = new HashSet<>();
        expected.add(3);
        expected.add(14);
        expected.add(37);
        assertEquals(expected, result1);
    }
}
