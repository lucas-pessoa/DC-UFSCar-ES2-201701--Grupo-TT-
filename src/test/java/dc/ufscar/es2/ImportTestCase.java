package dc.ufscar.es2;
import net.sf.jabref.logic.bibtex.BibEntryAssert;
import net.sf.jabref.logic.importer.fileformat.*;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;
import net.sf.jabref.pdfimport.PdfImporter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ImportTestCase {

    @Parameter
    public String fileName;

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> filenames(){
        // The test folder contains pairs of PDFs and BibTeX files. We check each pair.
        // A pasta de testes
        // This method returns the basenames of the available pairs

        Object[][] data = new Object[][] {
                // minimal PDF, not encrypted
                {"LNCS-minimal"},
                // minimal PDF, write-protected, thus encrypted
                {"LNCS-minimal-protected"}};
        return Arrays.asList(data);
    }

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void importPDFTest() throws URISyntaxException {

        PdfXmpImporter importer = new PdfXmpImporter(JabRefPreferences.getInstance().getXMPPreferences());
        Path pdfFile = Paths.get(PdfXmpImporterTest.class.getResource("blank.pdf").toURI());
        List<BibEntry> bibEntries = importer.importDatabase(pdfFile, StandardCharsets.UTF_8).getDatabase().getEntries();

        assertEquals(1, bibEntries.size());

        BibEntry be0 = bibEntries.get(0);
        assertEquals(Optional.empty(), be0.getField("abstract"));
        assertEquals(Optional.empty(), be0.getField("author"));
        assertEquals(Optional.empty(), be0.getField("keywords"));
        assertEquals(Optional.of("Blank PDF Document"), be0.getField("title"));
    }

    @Test
    public void importBibtextTest() throws IOException, URISyntaxException{
        BibtexImporter importer = new BibtexImporter(JabRefPreferences.getInstance().getImportFormatPreferences());
        Path file = Paths.get(BibtexImporterTest.class.getResource("testbib.bib").toURI());
        List<BibEntry> bibEntries = importer.importDatabase(file, StandardCharsets.UTF_8).getDatabase().getEntries();

        assertEquals(2, bibEntries.size());

        for (BibEntry entry : bibEntries){
            if (entry.getCiteKeyOptional().get().equals("small")) {
                assertEquals(Optional.of("Freely, I.P."), entry.getField("author"));
                assertEquals(Optional.of("A small paper"), entry.getField("title"));
                assertEquals(Optional.of("The journal of small papers"), entry.getField("journal"));
                assertEquals(Optional.of("1997"), entry.getField("year"));
            } else {
                assertEquals(Optional.of("Jass, Hugh"), entry.getField("author"));
                assertEquals(Optional.of("A big paper"), entry.getField("title"));
                assertEquals(Optional.of("The journal of big papers"), entry.getField("journal"));
                assertEquals(Optional.of("MCMXCVII"), entry.getField("volume"));
            }
        }
    }

    //teste de regressão por duplicação de código
    @After
    public void importPDFTest2() throws URISyntaxException {

        PdfXmpImporter importer = new PdfXmpImporter(JabRefPreferences.getInstance().getXMPPreferences());
        Path pdfFile = Paths.get(PdfXmpImporterTest.class.getResource("blank.pdf").toURI());
        List<BibEntry> bibEntries = importer.importDatabase(pdfFile, StandardCharsets.UTF_8).getDatabase().getEntries();

        assertEquals(1, bibEntries.size());

        BibEntry be0 = bibEntries.get(0);
        assertEquals(Optional.empty(), be0.getField("abstract"));
        assertEquals(Optional.empty(), be0.getField("author"));
        assertEquals(Optional.empty(), be0.getField("keywords"));
        assertEquals(Optional.of("Blank PDF Document"), be0.getField("title"));
    }

    @After
    public void importBibtextTest2() throws IOException, URISyntaxException{
        BibtexImporter importer = new BibtexImporter(JabRefPreferences.getInstance().getImportFormatPreferences());
        Path file = Paths.get(BibtexImporterTest.class.getResource("testbib.bib").toURI());
        List<BibEntry> bibEntries = importer.importDatabase(file, StandardCharsets.UTF_8).getDatabase().getEntries();

        assertEquals(2, bibEntries.size());

        for (BibEntry entry : bibEntries){
            if (entry.getCiteKeyOptional().get().equals("small")) {
                assertEquals(Optional.of("Freely, I.P."), entry.getField("author"));
                assertEquals(Optional.of("A small paper"), entry.getField("title"));
                assertEquals(Optional.of("The journal of small papers"), entry.getField("journal"));
                assertEquals(Optional.of("1997"), entry.getField("year"));
            } else {
                assertEquals(Optional.of("Jass, Hugh"), entry.getField("author"));
                assertEquals(Optional.of("A big paper"), entry.getField("title"));
                assertEquals(Optional.of("The journal of big papers"), entry.getField("journal"));
                assertEquals(Optional.of("MCMXCVII"), entry.getField("volume"));
            }
        }
    }
}
