package dbms.catalog.table;

public class UniqueValidator extends RowValidationHandler {
    @Override
    protected void check(Row row, Table table) {
        System.out.println("UniqueValidator: Checking uniqueness constraints.");
    }
}
