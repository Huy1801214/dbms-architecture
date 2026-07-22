package dbms.catalog.base;

import dbms.catalog.table.Table;
import dbms.catalog.view.View;
import dbms.catalog.procedure.StoredProcedure;
import dbms.catalog.sequence.Sequence;

public interface DatabaseObjectVisitor {
    void visit(Table table);
    void visit(View view);
    void visit(StoredProcedure procedure);
    void visit(Sequence sequence);
}
