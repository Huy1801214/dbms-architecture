package dbms.server;

public class DatabaseCreateRequest {
    private String name;
    private String owner;

    public DatabaseCreateRequest(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
