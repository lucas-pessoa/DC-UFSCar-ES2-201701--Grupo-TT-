package org.jabref.logic.exporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author alver
 */
public class OpenDocumentSpreadsheetCreator extends ExportFormat {

    private static final Log LOGGER = LogFactory.getLog(OpenDocumentSpreadsheetCreator.class);


    /**
     * Creates a new instance of OpenOfficeDocumentCreator
     */
    public OpenDocumentSpreadsheetCreator() {
        super(Localization.lang("OpenDocument spreadsheet"), "ods", null, null, ".ods");
    }

    @Override
    public void performExport(final BibDatabaseContext databaseContext, final String file,
            final Charset encoding, List<BibEntry> entries) throws IOException {
        Objects.requireNonNull(databaseContext);
        Objects.requireNonNull(entries);
        if (!entries.isEmpty()) { // Only export if entries exists
            OpenDocumentSpreadsheetCreator.exportOpenDocumentSpreadsheet(new File(file), databaseContext.getDatabase(), entries);
        }
    }

    private static void storeOpenDocumentSpreadsheetFile(File file, InputStream source) throws IOException {

        try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            //addResourceFile("mimetype", "/resource/ods/mimetype", out);
            ZipEntry ze = new ZipEntry("mimetype");
            String mime = "application/vnd.oasis.opendocument.spreadsheet";
            ze.setMethod(ZipEntry.STORED);
            ze.setSize(mime.length());
            CRC32 crc = new CRC32();
            crc.update(mime.getBytes());
            ze.setCrc(crc.getValue());
            out.putNextEntry(ze);
            for (int i = 0; i < mime.length(); i++) {
                out.write(mime.charAt(i));
            }
            out.closeEntry();

            ZipEntry zipEntry = new ZipEntry("content.xml");
            //zipEntry.setMethod(ZipEntry.DEFLATED);
            out.putNextEntry(zipEntry);
            int c;
            while ((c = source.read()) >= 0) {
                out.write(c);
            }
            out.closeEntry();

            // Add manifest (required for OOo 2.0) and "meta.xml": These are in the
            // resource/ods directory, and are copied verbatim into the zip file.
            OpenDocumentSpreadsheetCreator.addResourceFile("meta.xml", "/resource/ods/meta.xml", out);

            OpenDocumentSpreadsheetCreator.addResourceFile("META-INF/manifest.xml", "/resource/ods/manifest.xml", out);
        }
    }

    private static void exportOpenDocumentSpreadsheet(File file, BibDatabase database, List<BibEntry> entries)
            throws IOException {

        // First store the xml formatted content to a temporary file.
        File tmpFile = File.createTempFile("opendocument", null);
        OpenDocumentSpreadsheetCreator.exportOpenDocumentSpreadsheetXML(tmpFile, database, entries);

        // Then add the content to the zip file:
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(tmpFile))) {
            OpenDocumentSpreadsheetCreator.storeOpenDocumentSpreadsheetFile(file, in);
        }
        // Delete the temporary file:
        if (!tmpFile.delete()) {
            LOGGER.info("Cannot delete temporary export file");
        }
    }

    private static void exportOpenDocumentSpreadsheetXML(File tmpFile, BibDatabase database, List<BibEntry> entries) {
        OpenDocumentRepresentation od = new OpenDocumentRepresentation(database, entries);

        try (Writer ps = new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8)) {

            DOMSource source = new DOMSource(od.getDOMrepresentation());
            StreamResult result = new StreamResult(ps);
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.transform(source, result);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static void addResourceFile(String name, String resource, ZipOutputStream out) throws IOException {
        ZipEntry zipEntry = new ZipEntry(name);
        out.putNextEntry(zipEntry);
        OpenDocumentSpreadsheetCreator.addFromResource(resource, out);
        out.closeEntry();
    }

    private static void addFromResource(String resource, OutputStream out) {
        URL url = OpenDocumentSpreadsheetCreator.class.getResource(resource);
        try (InputStream in = url.openStream()) {
            byte[] buffer = new byte[256];
            synchronized (out) {
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            LOGGER.warn("Cannot get resource", e);
        }
    }
}
