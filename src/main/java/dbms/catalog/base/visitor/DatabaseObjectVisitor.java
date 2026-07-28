package dbms.catalog.base.visitor;

import dbms.catalog.procedure.entity.StoredProcedure;
import dbms.catalog.sequence.entity.Sequence;
import dbms.catalog.table.entity.Table;
import dbms.catalog.view.entity.View;

import dbms.catalog.table.entity.Table;
import dbms.catalog.view.entity.View;
import dbms.catalog.procedure.entity.StoredProcedure;
import dbms.catalog.sequence.entity.Sequence;

public interface DatabaseObjectVisitor {
    void visit(Table table);
    void visit(View view);
    void visit(StoredProcedure procedure);
    void visit(Sequence sequence);
}
