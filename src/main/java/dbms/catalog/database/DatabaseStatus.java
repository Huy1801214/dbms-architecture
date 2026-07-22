package dbms.catalog.database;

public enum DatabaseStatus {
    OFFLINE,
    ONLINE,
    OPENING,
    CLOSING,
    RECOVERING,
    SUSPECT
}
