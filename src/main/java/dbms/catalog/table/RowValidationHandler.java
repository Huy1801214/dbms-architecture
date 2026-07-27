package dbms.catalog.table;

public abstract class RowValidationHandler {
    private RowValidationHandler next;
    
    public RowValidationHandler setNext(RowValidationHandler next) {
        this.next = next;
        return next;
    }
    
    public void validate(Row row, Table table) {
        check(row, table);     
        checkNext(row, table);  
    }
    
    protected abstract void check(Row row, Table table);
    
    protected void checkNext(Row row, Table table) {
        if (next != null) {
            next.validate(row, table);
        }
    }
}
