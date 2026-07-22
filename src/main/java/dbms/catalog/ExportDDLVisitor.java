package dbms.catalog;

public class ExportDDLVisitor implements DatabaseObjectVisitor {
    private StringBuilder result;

    @Override
    public void visit(Table table) {
    }

    @Override
    public void visit(View view) {
    }

    @Override
    public void visit(StoredProcedure procedure) {
    }

    @Override
    public void visit(Sequence sequence) {
    }

    public String getResult() {
        return null;
    }
}
