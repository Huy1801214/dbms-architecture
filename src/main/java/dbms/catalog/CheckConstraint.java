package dbms.catalog;

public class CheckConstraint extends Constraint {
    @Override
    public boolean validate(Row row) {
        return false;
    }
}