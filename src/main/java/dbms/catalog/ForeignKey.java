package dbms.catalog;

import java.util.List;

public class ForeignKey extends Constraint {
    public String referenceTable;
    public String referenceColumns;

    public ForeignKey(String constraintName, List<String> columns, String referenceTable, String referenceColumns) {
        this.constraintName = constraintName;
        this.columns = columns;
        this.referenceTable = referenceTable;
        this.referenceColumns = referenceColumns;
    }

    @Override
    public void validate(Row row, Table table) {

    }
}