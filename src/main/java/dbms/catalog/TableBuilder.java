package dbms.catalog;

public interface TableBuilder {
    TableBuilder setName(String name);
    TableBuilder setEngine(String engine);

    TableBuilder addColumn(Column column);
    TableBuilder addConstraint(Constraint constraint);
    TableBuilder addIndex(Index index);
    TableBuilder addPartition(Partition partition);
    TableBuilder addTrigger(Trigger trigger);

    Table build();
}
