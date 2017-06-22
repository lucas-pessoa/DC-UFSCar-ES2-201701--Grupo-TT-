package org.jabref.logic.importer;

import org.jabref.logic.importer.fetcher.*;
import org.jabref.model.entry.FieldName;
import org.jabref.model.entry.identifier.DOI;
import org.jabref.model.entry.identifier.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WebFetchers {

    private WebFetchers() {
    }

    public static Optional<IdBasedFetcher> getIdBasedFetcherForField(String field, ImportFormatPreferences preferences) {
        IdBasedFetcher fetcher;
        switch (field) {
            case FieldName.DOI:
                fetcher = new DoiFetcher(preferences);
                break;
            case FieldName.ISBN:
                fetcher = new IsbnFetcher(preferences);
                break;
            case FieldName.EPRINT:
                fetcher = new ArXiv(preferences);
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(fetcher);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Identifier> IdFetcher<T> getIdFetcherForIdentifier(Class<T> clazz) {
        if (clazz == DOI.class) {
            return (IdFetcher<T>) new CrossRef();
        } else {
            throw new IllegalArgumentException("No fetcher found for identifier" + clazz.getCanonicalName());
        }
    }

    public static Optional<IdFetcher<? extends Identifier>> getIdFetcherForField(String fieldName) {
        switch (fieldName) {
            case FieldName.DOI:
                return Optional.of(new CrossRef());
        }
        return Optional.empty();
    }

    public static List<SearchBasedFetcher> getSearchBasedFetchers(ImportFormatPreferences importFormatPreferences) {
        ArrayList<SearchBasedFetcher> list = new ArrayList<>();
        list.add(new ArXiv(importFormatPreferences));
        list.add(new GvkFetcher());
        list.add(new MedlineFetcher());
        list.add(new AstrophysicsDataSystem(importFormatPreferences));
        list.add(new MathSciNet(importFormatPreferences));
        list.add(new zbMATH(importFormatPreferences));
        list.add(new GoogleScholar(importFormatPreferences));
        list.add(new DBLPFetcher(importFormatPreferences));
        list.add(new CrossRef());
        list.sort(Comparator.comparing(WebFetcher::getName));
        return list;
    }

    public static List<IdBasedFetcher> getIdBasedFetchers(ImportFormatPreferences importFormatPreferences) {
        ArrayList<IdBasedFetcher> list = new ArrayList<>();
        list.add(new ArXiv(importFormatPreferences));
        list.add(new AstrophysicsDataSystem(importFormatPreferences));
        list.add(new IsbnFetcher(importFormatPreferences));
        list.add(new DiVA(importFormatPreferences));
        list.add(new DoiFetcher(importFormatPreferences));
        list.add(new MedlineFetcher());
        list.add(new TitleFetcher(importFormatPreferences));
        list.add(new MathSciNet(importFormatPreferences));
        list.add(new CrossRef());
        list.add(new LibraryOfCongress());
        list.sort(Comparator.comparing(WebFetcher::getName));
        return list;
    }

    public static List<EntryBasedFetcher> getEntryBasedFetchers(ImportFormatPreferences importFormatPreferences) {
        ArrayList<EntryBasedFetcher> list = new ArrayList<>();
        list.add(new AstrophysicsDataSystem(importFormatPreferences));
        list.add(new DoiFetcher(importFormatPreferences));
        list.add(new MathSciNet(importFormatPreferences));
        list.add(new CrossRef());
        list.sort(Comparator.comparing(WebFetcher::getName));
        return list;
    }

    public static List<IdFetcher> getIdFetchers(ImportFormatPreferences importFormatPreferences) {
        ArrayList<IdFetcher> list = new ArrayList<>();
        list.add(new CrossRef());
        list.add(new ArXiv(importFormatPreferences));
        list.sort(Comparator.comparing(WebFetcher::getName));
        return list;
    }
}
