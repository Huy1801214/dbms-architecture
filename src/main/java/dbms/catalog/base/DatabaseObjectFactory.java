package dbms.catalog.base;

import dbms.catalog.table.Table;
import dbms.catalog.table.TableCreateRequest;
import dbms.catalog.view.View;
import dbms.catalog.view.ViewCreateRequest;
import dbms.catalog.procedure.StoredProcedure;
import dbms.catalog.procedure.ProcedureCreateRequest;
import dbms.catalog.sequence.Sequence;
import dbms.catalog.sequence.SequenceCreateRequest;

public interface DatabaseObjectFactory {
    Table createTable(TableCreateRequest request);
    View createView(ViewCreateRequest request);
    StoredProcedure createProcedure(ProcedureCreateRequest request);
    Sequence createSequence(SequenceCreateRequest request);
}
