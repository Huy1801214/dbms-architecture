package dbms.catalog;

public class ViewCreateRequest {
    private String viewId;
    private String name;
    private String queryDefinition;

    public ViewCreateRequest(String viewId, String name, String queryDefinition) {
        this.viewId = viewId;
        this.name = name;
        this.queryDefinition = queryDefinition;
    }

    public String getViewId() {
        return viewId;
    }

    public String getName() {
        return name;
    }

    public String getQueryDefinition() {
        return queryDefinition;
    }
}
