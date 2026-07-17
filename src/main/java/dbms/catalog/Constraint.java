package dbms.catalog;

public abstract class Constraint {
    public abstract boolean validate(Row row);
}