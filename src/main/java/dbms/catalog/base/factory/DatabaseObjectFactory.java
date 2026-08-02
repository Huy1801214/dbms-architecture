package dbms.catalog.base.factory;

import dbms.catalog.procedure.dto.ProcedureCreateRequest;
import dbms.catalog.procedure.entity.StoredProcedure;
import dbms.catalog.sequence.dto.SequenceCreateRequest;
import dbms.catalog.sequence.entity.Sequence;
import dbms.catalog.table.dto.TableCreateRequest;
import dbms.catalog.table.entity.Table;
import dbms.catalog.view.dto.ViewCreateRequest;
import dbms.catalog.view.entity.View;

public interface DatabaseObjectFactory {
    Table createTable(TableCreateRequest request);

    View createView(ViewCreateRequest request);

    StoredProcedure createProcedure(ProcedureCreateRequest request);

    Sequence createSequence(SequenceCreateRequest request);
}
