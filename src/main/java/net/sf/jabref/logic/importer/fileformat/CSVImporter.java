package net.sf.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import net.sf.jabref.Globals;
import net.sf.jabref.logic.importer.Importer;
import net.sf.jabref.logic.importer.ParserResult;
import net.sf.jabref.logic.util.FileExtensions;
import net.sf.jabref.model.entry.AuthorList;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;




public class CSVImporter extends Importer {

    private static final Pattern RECOGNIZED = Pattern.compile("[^,\n]*(,[^,\n]*)*");

    @Override
    public String getName() {
        return "Comma Separated Values";
    }

    @Override
    public FileExtensions getExtensions() {
        return FileExtensions.CSV;
    }

    @Override
    public String getId() {
        return "Comma Separated Values";
    }



    @Override
    public String getDescription() {
        return "Importer for the CVS format.";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) throws IOException {

        String str;
        while ((str = reader.readLine()) != null) {
            String[] fields = str.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if ((fields.length % 2) == 0 || !RECOGNIZED.matcher(str).find()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Parse the entries in the source, and return a List of BibEntry
     * objects.
     */
    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException {

        List<BibEntry> bibitems = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        String str;
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append('\n');
        }


        String[] entries = sb.toString().split("\n");

        for (String entry1 : entries) {

            String type = "";
            String author = "";
            String editor = "";
            String comment = "";
            Map<String, String> hm = new HashMap<>();

            String[] fields = entry1.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            type = fields[0];

            String bibtexkey = fields[1];

            for (int j = 2; j < (fields.length - 1); j += 2) {
                String lab = fields[j];
                String val = fields[j + 1];

                if (val.endsWith("\"") && val.startsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }

                if ("title".equals(lab)) {
                    String oldVal = hm.get("title");
                    if (oldVal == null) {
                        hm.put("title", val);
                    } else {
                        if (oldVal.endsWith(":") || oldVal.endsWith(".") || oldVal.endsWith("?")) {
                            hm.put("title", oldVal + " " + val);
                        } else {
                            hm.put("title", oldVal + ": " + val);
                        }
                    }
                    hm.put("title", hm.get("title").replaceAll("\\s+", " ")); // Normalize whitespaces
                } else if ("booktitle".equals(lab)) {
                    hm.put("booktitle", val);
                } else if ("series".equals(lab)) {
                    hm.put("series", val);
                } else if ("author".equals(lab)) {
                    if ("".equals(author)) {
                        author = val;
                    } else {
                        author += " and " + val;
                    }
                } else if ("editor".equals(lab)) {
                    if ("".equals(editor)) {
                        editor = val;
                    } else {
                        editor += " and " + val;
                    }
                } else if ("booktitle".equals(lab) || "journal".equals(lab)) {
                    if ("inproceedings".equals(type)) {
                        hm.put("booktitle", val);
                    } else {
                        hm.put("journal", val);
                    }
                } else if ("pages".equals(lab)) {
                    hm.put("pages", val);
                } else if ("school".equals(lab) || "publisher".equals(lab)) {
                    if ("phdthesis".equals(type)) {
                        hm.put("school", val);
                    } else {
                        hm.put("publisher", val);
                    }
                } else if ("address".equals(lab)) {
                    hm.put("address", val);
                } else if ("issn".equals(lab)) {
                    hm.put("issn", val);
                } else if ("volume".equals(lab)) {
                    hm.put("volume", val);
                } else if ("number".equals(lab)) {
                    hm.put("number", val);
                } else if ("abstract".equals(lab)) {
                    String oldAb = hm.get("abstract");
                    if (oldAb == null) {
                        hm.put("abstract", val);
                    } else {
                        hm.put("abstract", oldAb + Globals.prefs.get(JabRefPreferences.NEWLINE) + val);
                    }
                } else if ("url".equals(lab)) {
                    hm.put("url", val);
                } else if ("year".equals(lab)) {
                    hm.put("year", val);
                } else if ("keywords".equals(lab)) {
                    if (hm.containsKey("keywords")) {
                        String kw = hm.get("keywords");
                        hm.put("keywords", kw + ", " + val);
                    } else {
                        hm.put("keywords", val);
                    }
                } else if ("comment".equals(lab)) {
                    if (!comment.isEmpty()) {
                        comment = comment + " ";
                    }
                    comment = comment + val;
                } else if ("refid".equals(lab)) {
                    hm.put("refid", val);
                } else if ("doi".equals(lab)) {
                    hm.put("doi", val);
                } else if ("note".equals(lab)) {
                    hm.put("note", val);
                } else {
                    hm.put(lab, val);
                }
                // fix authors
                if (!author.isEmpty()) {
                    author = AuthorList.fixAuthorLastNameFirst(author);
                    hm.put("author", author);
                }
                if (!editor.isEmpty()) {
                    editor = AuthorList.fixAuthorLastNameFirst(editor);
                    hm.put("editor", editor);
                }
                if (!comment.isEmpty()) {
                    hm.put("comment", comment);
                }
            }

            BibEntry entry = new BibEntry(type);

            // Remove empty fields:
            List<Object> toRemove = new ArrayList<>();
            for (Map.Entry<String, String> key : hm.entrySet()) {
                String content = key.getValue();
                if ((content == null) || content.trim().isEmpty()) {
                    toRemove.add(key.getKey());
                }
            }
            for (Object aToRemove : toRemove) {
                hm.remove(aToRemove);

            }

            // create one here
            entry.setField(hm);
            entry.setCiteKey(bibtexkey);
            bibitems.add(entry);

        }

        return new ParserResult(bibitems);

    }

}