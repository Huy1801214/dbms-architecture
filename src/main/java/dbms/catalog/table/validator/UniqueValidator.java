package dbms.catalog.table.validator;

import dbms.catalog.table.entity.Row;
import dbms.catalog.table.entity.Table;

public class UniqueValidator extends RowValidationHandler {
    @Override
    protected void check(Row row, Table table) {
        System.out.println("UniqueValidator: Checking uniqueness constraints.");
    }
}
