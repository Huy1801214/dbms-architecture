package dbms.server;

import dbms.catalog.Database;

public abstract class DatabaseFactory {
    public abstract Database createDatabase(DatabaseCreateRequest request);
}
