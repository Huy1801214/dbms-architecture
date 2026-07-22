package dbms.catalog.base;

import dbms.catalog.table.Table;
import dbms.catalog.view.View;
import dbms.catalog.procedure.StoredProcedure;
import dbms.catalog.sequence.Sequence;

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
