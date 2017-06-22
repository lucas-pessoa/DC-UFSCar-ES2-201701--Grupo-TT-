package org.jabref.logic.protectedterms;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class ProtectedTermsListTest {

    private ProtectedTermsList internalList;
    private ProtectedTermsList externalList;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        String tempFileName = temporaryFolder.newFile().getAbsolutePath();

        internalList = new ProtectedTermsList("Name", new ArrayList<>(Arrays.asList("AAA", "BBB")), "location", true);
        externalList = new ProtectedTermsList("Namely", new ArrayList<>(Arrays.asList("AAA", "BBB")), tempFileName);
    }

    @Test
    public void testProtectedTermsListStringListOfStringStringBoolean() {
        assertTrue(internalList.isInternalList());
    }

    @Test
    public void testProtectedTermsListStringListOfStringString() {
        assertFalse(externalList.isInternalList());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Name", internalList.getDescription());
    }

    @Test
    public void testGetTermList() {
        assertEquals(Arrays.asList("AAA", "BBB"), internalList.getTermList());
    }

    @Test
    public void testGetLocation() {
        assertEquals("location", internalList.getLocation());
    }

    @Test
    public void testGetTermListing() {
        assertEquals("AAA\nBBB", internalList.getTermListing());
    }

    @Test
    public void testCompareTo() {
        assertEquals(-2, internalList.compareTo(externalList));
    }

    @Test
    public void testSetEnabledIsEnabled() {
        assertFalse(internalList.isEnabled());
        internalList.setEnabled(true);
        assertTrue(internalList.isEnabled());
    }

    @Test
    public void testNotEnabledByDefault() {
        assertFalse(internalList.isEnabled());
    }

    @Test
    public void testCanNotAddTermToInternalList() {
        assertFalse(internalList.addProtectedTerm("CCC"));
    }

    @Test
    public void testTermNotAddedToInternalList() {
        internalList.addProtectedTerm("CCC");
        assertFalse(internalList.getTermList().contains("CCC"));
    }

    @Test
    public void testCanAddTermToExternalList() {
        assertTrue(externalList.addProtectedTerm("CCC"));
    }

    @Test
    public void testTermAddedToExternalList() {
        externalList.addProtectedTerm("CCC");
        assertTrue(externalList.getTermList().contains("CCC"));
    }

}
