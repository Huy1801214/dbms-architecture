package dbms.metadata.domain;

public enum FileState {
    ONLINE,
    OFFLINE,
    READONLY,
    RECOVERING,
    CORRUPTED
}
