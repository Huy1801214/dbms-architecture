package dbms.catalog;

public class UniqueConstraint extends Constraint {
    @Override
    public boolean validate(Row row) {
        return false;
    }
}