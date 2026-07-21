package dbms.catalog;

public class ProcedureCreateRequest {
    private String procId;
    private String name;
    private String code;

    public ProcedureCreateRequest(String procId, String name, String code) {
        this.procId = procId;
        this.name = name;
        this.code = code;
    }

    public String getProcId() {
        return procId;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
