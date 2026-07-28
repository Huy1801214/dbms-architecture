package dbms.catalog.table.validator;

import dbms.catalog.table.entity.Column;
import dbms.catalog.table.entity.Row;
import dbms.catalog.table.entity.Table;

public class NullabilityValidator extends RowValidationHandler {
    @Override
    protected void check(Row row, Table table) {
        if (table != null && table.getColumns() != null && row != null) {
            for (int i = 0; i < table.getColumns().size(); i++) {
                Column col = table.getColumns().get(i);
                if (col.nullable != null && !col.nullable) {
                    if (row.values != null && i < row.values.size()) {
                        Object val = row.values.get(i);
                        if (val == null) {
                            throw new IllegalArgumentException("Column " + col.name + " cannot be null");
                        }
                    }
                }
            }
        }
    }
}
