package org.jabref.shared.listener;

import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import org.jabref.shared.DBMSProcessor;
import org.jabref.shared.DBMSSynchronizer;

/**
 * A listener for PostgreSQL database notifications.
 */
public class PostgresSQLNotificationListener implements PGNotificationListener {

    private final DBMSSynchronizer dbmsSynchronizer;


    public PostgresSQLNotificationListener(DBMSSynchronizer dbmsSynchronizer) {
        this.dbmsSynchronizer = dbmsSynchronizer;
    }

    @Override
    public void notification(int processId, String channel, String payload) {
        if (!payload.equals(DBMSProcessor.PROCESSOR_ID)) {
            dbmsSynchronizer.pullChanges();
        }
    }

}
