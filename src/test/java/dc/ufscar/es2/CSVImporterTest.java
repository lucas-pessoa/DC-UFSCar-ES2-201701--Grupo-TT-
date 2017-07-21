import net.sf.jabref.logic.importer.fileformat.CSVImporter;
import net.sf.jabref.Globals;
import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.preferences.JabRefPreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CSVImporterTest {

    private CSVImporter importer;

    @Before
    public void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        importer = new CSVImporter();
    }

    @Test
    public void testeNomeExtensao() {
        assertEquals("Comma Separated Values", importer.getName());
    }

    @Test
    public void testFormatoID() {
        assertEquals("Comma Separated Values", importer.getId());
    }


    @Test
    public void testsExtensao() {
        assertEquals(FileExtensions.CSV, importer.getExtensions());
    }

    //testes de regressão por duplicação de código
    @After
    public void testeNomeExtensao2() {
        assertEquals("Comma Separated Values", importer.getName());
    }

    @After
    public void testFormatoID2() {
        assertEquals("Comma Separated Values", importer.getId());
    }


    @After
    public void testsExtensao2() {
        assertEquals(FileExtensions.CSV, importer.getExtensions());
    }


}