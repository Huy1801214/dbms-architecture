package dbms.catalog;
import java.time.LocalDateTime;

public class Database {
    public String databaseId;
    public String name;
    public String owner;
    public String status;
    public LocalDateTime createdAt;

    public void open() {}
    public void close() {}
}