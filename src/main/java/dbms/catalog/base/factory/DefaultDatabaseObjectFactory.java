package dbms.catalog.base.factory;

import dbms.catalog.procedure.dto.ProcedureCreateRequest;
import dbms.catalog.procedure.entity.StoredProcedure;
import dbms.catalog.sequence.dto.SequenceCreateRequest;
import dbms.catalog.sequence.entity.Sequence;
import dbms.catalog.table.dto.TableCreateRequest;
import dbms.catalog.table.entity.Table;
import dbms.catalog.view.dto.ViewCreateRequest;
import dbms.catalog.view.entity.View;

import java.util.UUID;

public class DefaultDatabaseObjectFactory implements DatabaseObjectFactory {
    @Override
    public Table createTable(TableCreateRequest request) {
        String name = request != null && request.name != null ? request.name : "unnamed";
        String engine = request != null && request.engine != null ? request.engine : "InnoDB";
        return Table.builder().setName(name).setEngine(engine).build();
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
        return new Sequence(id, request != null ? request.name : null, request != null ? request.start : 1,
                request != null ? request.increment : 1);
    }
}
