package dbms.catalog.view;

public class ViewCreateRequest {
    public String name;
    public String queryDefinition;

    public ViewCreateRequest() {}

    public ViewCreateRequest(String name, String queryDefinition) {
        this.name = name;
        this.queryDefinition = queryDefinition;
    }
}
