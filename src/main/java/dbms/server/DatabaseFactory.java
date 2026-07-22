package dbms.server;

import dbms.catalog.database.Database;

public abstract class DatabaseFactory {
    public abstract Database createDatabase(DatabaseCreateRequest request);
}
