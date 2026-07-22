package dbms.catalog;

import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class ForeignKey extends Constraint {
    public String referenceTable;
    public String referenceColumns;

    public ForeignKey(String constraintName, List<String> columnNames, String referenceTable, String referenceColumns) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
        this.validated = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        if (columnNames != null) {
            this.columns = columnNames.stream().map(name -> {
                Column col = new Column();
                col.name = name;
                return col;
            }).collect(Collectors.toList());
        }
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
    }

    public ForeignKey(String constraintName, List<Column> columns, UUID tableId, String referenceTable, String referenceColumns) {
        this.constraintId = UUID.randomUUID();
        this.constraintName = constraintName;
        this.constraintType = ConstraintType.FOREIGN_KEY;
        this.columns = columns;
        this.tableId = tableId;
        this.status = ConstraintStatus.ACTIVE;
        this.enabled = true;
        this.validated = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
    }

    @Override
    public void validate(Row row, Table table) {

    }
}