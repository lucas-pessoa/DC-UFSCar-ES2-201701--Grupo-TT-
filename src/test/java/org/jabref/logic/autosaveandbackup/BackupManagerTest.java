package org.jabref.logic.autosaveandbackup;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupManagerTest {

    @Test
    public void backupFileNameIsCorrectlyGeneratedWithinTmpDirectory() {
        Path bibPath = Paths.get("tmp", "test.bib");
        Path savPath = BackupManager.getBackupPath(bibPath);
        Assert.assertEquals(Paths.get("tmp", "test.bib.sav"), savPath);
    }

}
