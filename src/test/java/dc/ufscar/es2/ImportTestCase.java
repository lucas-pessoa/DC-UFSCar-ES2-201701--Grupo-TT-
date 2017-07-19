package dc.ufscar.es2;
import net.sf.jabref.logic.importer.fileformat.BibtexImporter;
import net.sf.jabref.logic.importer.fileformat.PdfContentImporter;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ImportTestCase {

    private BibtexImporter BTimporter;
    private PdfContentImporter PDFimporter;

    @Before
    public void setUp(){
        BTimporter = new BibtexImporter(JabRefPreferences.getInstance().getImportFormatPreferences());
        PDFimporter = new PdfContentImporter(JabRefPreferences.getInstance().getImportFormatPreferences());
    }

}
