package dbms.catalog;

public interface DatabaseObjectVisitor {
    void visit(Table table);
    void visit(View view);
    void visit(StoredProcedure procedure);
    void visit(Sequence sequence);
}
