package dc.ufscar.es2;

import net.sf.jabref.logic.importer.ImportFormatPreferences;
import net.sf.jabref.logic.importer.ParseException;
import net.sf.jabref.logic.importer.fileformat.BibtexParser;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class testeinBook {
    private ImportFormatPreferences importFormatPreferences;
    private BibtexParser parser;

    @Before
    public void setUp() {
        importFormatPreferences = JabRefPreferences.getInstance().getImportFormatPreferences();
        parser = new BibtexParser(importFormatPreferences);
    }

    // Teste da nova funcionalidade que verifica o campo numero de paginas
    @Test
    public void nPaginasNegativo() throws ParseException{
        List<BibEntry> teste = parser.parseEntries("@inbook{1, title = {A arte da Guerra},\n" + "pages = {-1}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setField("pages", "2");
        assertNotEquals(Collections.singletonList(entradaValida), teste);
    }

    @Test
    public void nPaginasNulo() throws ParseException{
        List<BibEntry> teste = parser.parseEntries("@inbook{2, title = {Sherlock},\n" + "pages = {}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("inbook");
        entradaValida.setField("pages", "3");
        assertEquals(Collections.singletonList(entradaValida), teste);
    }
}