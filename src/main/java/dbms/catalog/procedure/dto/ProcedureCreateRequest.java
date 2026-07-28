package dbms.catalog.procedure.dto;


public class ProcedureCreateRequest {
    public String name;
    public String code;

    public ProcedureCreateRequest() {}

    public ProcedureCreateRequest(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
