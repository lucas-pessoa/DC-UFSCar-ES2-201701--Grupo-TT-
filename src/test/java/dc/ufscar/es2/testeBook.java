package dc.ufscar.es2;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import net.sf.jabref.logic.exporter.SavePreferences;
import net.sf.jabref.logic.formatter.casechanger.LowerCaseFormatter;
import net.sf.jabref.logic.importer.ImportFormatPreferences;
import net.sf.jabref.logic.importer.ParseException;
import net.sf.jabref.logic.importer.ParserResult;
import net.sf.jabref.logic.importer.fileformat.BibtexParser;
import net.sf.jabref.logic.util.OS;
import net.sf.jabref.model.bibtexkeypattern.AbstractBibtexKeyPattern;
import net.sf.jabref.model.bibtexkeypattern.DatabaseBibtexKeyPattern;
import net.sf.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern;
import net.sf.jabref.model.cleanup.FieldFormatterCleanup;
import net.sf.jabref.model.cleanup.FieldFormatterCleanups;
import net.sf.jabref.model.database.BibDatabaseMode;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexString;
import net.sf.jabref.model.entry.EntryType;
import net.sf.jabref.model.entry.FieldName;
import net.sf.jabref.model.groups.AllEntriesGroup;
import net.sf.jabref.model.groups.ExplicitGroup;
import net.sf.jabref.model.groups.GroupHierarchyType;
import net.sf.jabref.model.groups.GroupTreeNode;
import net.sf.jabref.model.groups.RegexKeywordGroup;
import net.sf.jabref.model.groups.WordKeywordGroup;
import net.sf.jabref.model.metadata.SaveOrderConfig;
import net.sf.jabref.preferences.JabRefPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testeBook{
    private ImportFormatPreferences importFormatPreferences;
    private BibtexParser parser;

    @Before
    public void setUp() {
        importFormatPreferences = JabRefPreferences.getInstance().getImportFormatPreferences();
        parser = new BibtexParser(importFormatPreferences);
    }

    @Test
    public void todosCampos() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{1, title = {São Marcos},\n" + "publisher = {Mauro Beting},\n year = {2012},\n author={Mauro},\n editor = {Abril} }");

        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setCiteKey("1");
        entradaValida.setField("title", "São Marcos");
        entradaValida.setField("publisher", "Mauro Beting");
        entradaValida.setField("year", "2012");
        entradaValida.setField("author", "Mauro");
        entradaValida.setField("editor", "Abril");
        assertEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void anoMaior() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{, year = {2018}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void anoMenor() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{, year = {1899}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void anoInferior() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{, year = {1900}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setField("year", "1900");
        assertEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void anoSuperior()  throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{, year = {2017}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setField("year", "2016");
        assertNotEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void bitTexKey1() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{a}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setCiteKey("a");
        assertEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void bitTexKeyNaoLetraInicio() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{0AAA}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setCiteKey("0AAA");
        assertEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void bitTexKey1Correta() throws ParseException{
        List<BibEntry> test = parser.parseEntries("@book{Aaa111}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setCiteKey("Aaa111");
        assertEquals(Collections.singletonList(entradaValida), test);
    }

    @Test
    public void tNull() throws ParseException{
        List<BibEntry> test2 = parser.parseEntries("@book{,}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        assertEquals(Collections.singletonList(entradaValida), test2);
    }

    @Test
    public void camposExtras() throws ParseException{
        List<BibEntry> test3 = parser.parseEntries("@book{2, title = {Na natureza Selvagem},\n" + "publisher = {Joseph},\n year = {2018},\n author={Michael Johnson},\n editor = {Abril},\n "+ "volume = {1},\n series = {2},\n edition = {6},\n note = {Livro legal}, number = {7},\n"+ "address = {UFSCar},\n month = {Fevereiro}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("book");
        entradaValida.setCiteKey("2");
        entradaValida.setField("title", "Na natureza Selvagem");
        entradaValida.setField("publisher", "Joseph");
        entradaValida.setField("year", "2018");
        entradaValida.setField("author", "Michael Johnson");
        entradaValida.setField("editor", "Abril");
        entradaValida.setField("volume", "1");
        entradaValida.setField("series", "2");
        entradaValida.setField("edition", "6");
        entradaValida.setField("note", "Livro legal");
        entradaValida.setField("number", "7");
        entradaValida.setField("address", "UFSCar");
        entradaValida.setField("month", "Fevereiro");
        assertEquals(Collections.singletonList(entradaValida), test3);
    }
}