package dbms.catalog.base;

import dbms.catalog.table.Table;
import dbms.catalog.table.TableCreateRequest;
import dbms.catalog.view.View;
import dbms.catalog.view.ViewCreateRequest;
import dbms.catalog.procedure.StoredProcedure;
import dbms.catalog.procedure.ProcedureCreateRequest;
import dbms.catalog.sequence.Sequence;
import dbms.catalog.sequence.SequenceCreateRequest;
import java.util.UUID;

public class DefaultDatabaseObjectFactory implements DatabaseObjectFactory {
    @Override
    public Table createTable(TableCreateRequest request) {
        String id = request != null && request.name != null ? request.name : UUID.randomUUID().toString();
        return new Table(id, request != null ? request.name : null, request != null ? request.engine : null);
    }

    @Override
    public View createView(ViewCreateRequest request) {
        String id = request != null && request.name != null ? request.name : UUID.randomUUID().toString();
        return new View(id, request != null ? request.name : null, request != null ? request.queryDefinition : null);
    }

    @Override
    public StoredProcedure createProcedure(ProcedureCreateRequest request) {
        String id = request != null && request.name != null ? request.name : UUID.randomUUID().toString();
        return new StoredProcedure(id, request != null ? request.name : null, request != null ? request.code : null);
    }

    @Override
    public Sequence createSequence(SequenceCreateRequest request) {
        String id = request != null && request.name != null ? request.name : UUID.randomUUID().toString();
        return new Sequence(id, request != null ? request.name : null, request != null ? request.start : 1, request != null ? request.increment : 1);
    }
}
