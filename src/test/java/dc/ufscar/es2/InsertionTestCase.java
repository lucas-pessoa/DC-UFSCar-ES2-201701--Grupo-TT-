package dc.ufscar.es2;

import net.sf.jabref.model.entry.BibEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class InsertionTestCase {
    private BibEntry entry;
    private BibEntry emptyEntry;

    @Before
    public void setUp(){
        entry = new BibEntry();

        // Empty entry for some tests
        emptyEntry = new BibEntry();
        emptyEntry.setType("article");
        emptyEntry.setChanged(false);
    }

    @After
    public void tearDown(){
        entry = null;
    }

    @Test
    public void nullBibEntry(){
        entry.setField("","");
        Assert.assertTrue(entry.getKeywords(',').isEmpty());
    }

    @Test
    public void invalidIdBibEntry(){
        String id = "99999999999999999999999999999999999999";
        String expected = "99999999999999999999999999999999999999";
        entry.setId(id);
        id = entry.getId();
        Assert.assertEquals(expected, id);
    }



    @Test
    public void invalidCiteKey(){
        String citeKeyInvalid = ""; //entrada nula
        String expected = "2";
        entry.setCiteKey(citeKeyInvalid);
        citeKeyInvalid = entry.getCiteKey();
        Assert.assertNotEquals(expected,citeKeyInvalid);
    }

    @Test
    public void validCiteKey(){
        String citeKeyValid = "a1"; //entrada nula
        String expected1 = "a1";
        entry.setCiteKey(citeKeyValid);
        citeKeyValid = entry.getCiteKey();
        Assert.assertEquals(expected1,citeKeyValid);
    }


    //testes de regressão por duplicação de testes
    @After
    public void nullBibEntry2(){
        entry.setField("","");
        Assert.assertTrue(entry.getKeywords(',').isEmpty());
    }

    @After
    public void invalidIdBibEntry2(){
        String id = "99999999999999999999999999999999999999";
        String expected = "99999999999999999999999999999999999999";
        entry.setId(id);
        id = entry.getId();
        Assert.assertEquals(expected, id);
    }


    @After
    public void invalidCiteKey2(){
        String citeKeyInvalid = ""; //entrada nula
        String expected = "2";
        entry.setCiteKey(citeKeyInvalid);
        citeKeyInvalid = entry.getCiteKey();
        Assert.assertNotEquals(expected,citeKeyInvalid);
    }

    @After
    public void validCiteKey2(){
        String citeKeyValid = "a1"; //entrada nula
        String expected1 = "a1";
        entry.setCiteKey(citeKeyValid);
        citeKeyValid = entry.getCiteKey();
        Assert.assertEquals(expected1,citeKeyValid);
    }

}