package dbms.catalog.constraint;

import dbms.catalog.table.Column;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;
import java.util.List;
import java.util.UUID;

public class ForeignKey extends Constraint {
    public String referenceTable;
    public String referenceColumns;

    public ForeignKey() {
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.enabled = true;
    }

    public ForeignKey(ForeignKeyRequest request) {
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.enabled = true;
        if (request != null) {
            this.constraintName = request.name;
            this.referenceTable = request.referenceTable;
            this.referenceColumns = request.referenceColumns;
        }
    }

    public ForeignKey(String constraintName, List<String> columnNames, String referenceTable, String referenceColumns) {
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
        this.enabled = true;
    }

    public ForeignKey(String constraintName, List<Column> columns, UUID tableId, String referenceTable, String referenceColumns) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
        this.enabled = true;
    }

    @Override
    public void validate(Row row, Table table) {
    }
}
