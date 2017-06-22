package org.jabref.model.entry;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class IEEETranEntryTypesTest {

    @Test
    public void ctlTypeContainsYesNoFields() {
        List<String> ctlFields = IEEETranEntryTypes.IEEETRANBSTCTL.getAllFields();
        List<String> ynFields = InternalBibtexFields.getIEEETranBSTctlYesNoFields();

        Assert.assertTrue(ctlFields.containsAll(ynFields));
    }

}
