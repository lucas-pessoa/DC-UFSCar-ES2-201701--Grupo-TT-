package org.jabref.model.entry.specialfields;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class SpecialFieldTest {


    @Test
    public void getSpecialFieldInstanceFromFieldNameValid() {
        assertEquals(Optional.of(SpecialField.RANKING),
                SpecialField.getSpecialFieldInstanceFromFieldName("ranking"));
    }

    @Test
    public void getSpecialFieldInstanceFromFieldNameEmptyForInvalidField() {
        assertEquals(Optional.empty(), SpecialField.getSpecialFieldInstanceFromFieldName("title"));
    }

    @Test
    public void isSpecialFieldTrueForValidField() {
        assertTrue(SpecialField.isSpecialField("ranking"));
    }

    @Test
    public void isSpecialFieldFalseForInvalidField() {
        assertFalse(SpecialField.isSpecialField("title"));
    }
}
