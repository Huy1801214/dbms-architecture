package dbms.catalog;

public class ForeignKey extends Constraint {
    public String referenceTable;

    @Override
    public boolean validate(Row row) {
        return false;
    }
}