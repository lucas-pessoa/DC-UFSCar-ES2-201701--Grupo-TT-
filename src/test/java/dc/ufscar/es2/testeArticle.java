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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testeArticle {
    private ImportFormatPreferences importFormatPreferences;
    private BibtexParser parser;

    @Before
    public void setUp() {
        importFormatPreferences = JabRefPreferences.getInstance().getImportFormatPreferences();
        parser = new BibtexParser(importFormatPreferences);
    }

    @Test
    public void todosCampos() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{1, author={Gustavo},\n"+ "title = {A descoberta dos dados},\n journal = {json},\n year = {2017} }");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("1");
        entradaValida.setField("author", "Gustavo");
        entradaValida.setField("title", "A descoberta dos dados");
        entradaValida.setField("journal", "json");
        entradaValida.setField("year", "2017");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void anoMaior() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {2018}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void anoMenor() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {1899}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void anoInferior() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {1900}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "1900");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void anoSuperior() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {2017}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void bitTexKey1() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{a}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("a");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void bitTexKeyNaoLetraInicio() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{0Aaa}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("0Aaa");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void bitTexKey1Correta()throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{Aaa111}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("Aaa111");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @Test
    public void tNull() throws ParseException{
        List<BibEntry> teste2 = parser.parseEntries("@article{,}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        assertEquals(Collections.singletonList(entradaValida), teste2);
    }

    @Test
    public void camposExtras() throws ParseException{
        List<BibEntry> teste3 = parser.parseEntries("@article{2, author={Lucas},\n"+ "title = {Engenharia de Software do futuro},\n journal = {json},\n year = {2017},\n"+ "volume = {1},\n number = {1},\n pages = {fifth},\n month = {february},\n note = {lalala} }");

        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("2");
        entradaValida.setField("author", "Lucas");
        entradaValida.setField("title", "Engenharia de Software do futuro");
        entradaValida.setField("journal", "json");
        entradaValida.setField("year", "2017");
        entradaValida.setField("volume", "1");
        entradaValida.setField("number", "1");
        entradaValida.setField("pages", "fifth");
        entradaValida.setField("month", "february");
        entradaValida.setField("note", "lalala");
        assertEquals(Collections.singletonList(entradaValida), teste3);

    }

    //testes de regressão por repetição de código

    @After
    public void todosCampos2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{1, author={Gustavo},\n"+ "title = {A descoberta dos dados},\n journal = {json},\n year = {2017} }");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("1");
        entradaValida.setField("author", "Gustavo");
        entradaValida.setField("title", "A descoberta dos dados");
        entradaValida.setField("journal", "json");
        entradaValida.setField("year", "2017");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void anoMaior2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {2018}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void anoMenor2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {1899}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertNotEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void anoInferior2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {1900}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "1900");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void anoSuperior2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{, year = {2017}}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setField("year", "2017");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void bitTexKey1_2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{a}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("a");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void bitTexKeyNaoLetraInicio2() throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{0Aaa}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("0Aaa");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void bitTexKey1Correta2()throws ParseException{
        List<BibEntry> teste1 = parser.parseEntries("@article{Aaa111}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("Aaa111");
        assertEquals(Collections.singletonList(entradaValida), teste1);
    }

    @After
    public void tNull2() throws ParseException{
        List<BibEntry> teste2 = parser.parseEntries("@article{,}");
        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        assertEquals(Collections.singletonList(entradaValida), teste2);
    }

    @After
    public void camposExtras2() throws ParseException{
        List<BibEntry> teste3 = parser.parseEntries("@article{2, author={Lucas},\n"+ "title = {Engenharia de Software do futuro},\n journal = {json},\n year = {2017},\n"+ "volume = {1},\n number = {1},\n pages = {fifth},\n month = {february},\n note = {lalala} }");

        BibEntry entradaValida = new BibEntry();
        entradaValida.setType("article");
        entradaValida.setCiteKey("2");
        entradaValida.setField("author", "Lucas");
        entradaValida.setField("title", "Engenharia de Software do futuro");
        entradaValida.setField("journal", "json");
        entradaValida.setField("year", "2017");
        entradaValida.setField("volume", "1");
        entradaValida.setField("number", "1");
        entradaValida.setField("pages", "fifth");
        entradaValida.setField("month", "february");
        entradaValida.setField("note", "lalala");
        assertEquals(Collections.singletonList(entradaValida), teste3);

    }