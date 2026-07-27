package dbms.catalog.table;

public class ForeignKeyValidator extends RowValidationHandler {
    @Override
    protected void check(Row row, Table table) {
        System.out.println("ForeignKeyValidator: Checking foreign key references.");
    }
}
