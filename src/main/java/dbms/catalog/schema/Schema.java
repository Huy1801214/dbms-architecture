package dbms.catalog.schema;

import dbms.catalog.base.*;
import dbms.catalog.table.*;
import dbms.catalog.view.*;
import dbms.catalog.procedure.*;
import dbms.catalog.sequence.*;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Schema implements DatabaseComponent {
    public String schemaId;
    public String name;
    public String owner;

    private List<DatabaseObject> objects = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private List<StoredProcedure> procedures = new ArrayList<>();
    private List<Sequence> sequences = new ArrayList<>();
    private DatabaseObjectFactory factory = new DefaultDatabaseObjectFactory();

    public Schema() {
    }

    public Schema(String schemaId, String name, String owner) {
        this.schemaId = schemaId;
        this.name = name;
        this.owner = owner;
    }

    public String getSchemaId() {
        return schemaId;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }

    public void removeObject(UUID objectId) {
    }

    public Table createTable(TableCreateRequest request) {
        Table table = new Table(java.util.UUID.randomUUID().toString(), request != null ? request.name : null,
                request != null ? request.engine : null);
        tables.add(table);
        objects.add(table);
        return table;
    }

    public View createView(ViewCreateRequest request) {
        View view = new View(java.util.UUID.randomUUID().toString(), request != null ? request.name : null,
                request != null ? request.queryDefinition : null);
        views.add(view);
        objects.add(view);
        return view;
    }

    public StoredProcedure createProcedure(ProcedureCreateRequest request) {
        StoredProcedure proc = new StoredProcedure(java.util.UUID.randomUUID().toString(),
                request != null ? request.name : null, request != null ? request.code : null);
        procedures.add(proc);
        objects.add(proc);
        return proc;
    }

    public Sequence createSequence(SequenceCreateRequest request) {
        Sequence seq = new Sequence(java.util.UUID.randomUUID().toString(), request != null ? request.name : null,
                request != null ? request.start : 1, request != null ? request.increment : 1);
        sequences.add(seq);
        objects.add(seq);
        return seq;
    }

    public void dropObject(String objectId) {
        DatabaseObject obj = findObjectById(objectId);
        if (obj != null) {
            objects.remove(obj);
        }
    }

    public DatabaseObject findObject(String name) {
        if (name == null)
            return null;
        for (DatabaseObject obj : objects) {
            if (name.equals(obj.getName()))
                return obj;
        }
        return null;
    }

    public List<DatabaseObject> listObjects() {
        return new ArrayList<>(objects);
    }

    public DatabaseObjectIterator iterator() {
        return new SchemaObjectIterator(objects);
    }

    public void accept(DatabaseObjectVisitor visitor) {
        if (visitor != null) {
            for (DatabaseObject obj : objects) {
                obj.accept(visitor);
            }
        }
    }

    public void addObject(DatabaseObject obj) {
        if (obj != null)
            objects.add(obj);
    }

    public void removeObject(String objectId) {
        dropObject(objectId);
    }

    private DatabaseObject findObjectById(String objectId) {
        if (objectId == null)
            return null;
        for (DatabaseObject obj : objects) {
            if (objectId.equals(obj.getObjectId()))
                return obj;
        }
        return null;
    }

    public void createTable(Table table) {
        if (table != null) {
            tables.add(table);
            objects.add(table);
        }
    }

    public Table getTable(String tableId) {
        if (tableId == null)
            return null;
        for (Table t : tables) {
            if (tableId.equals(t.getTableId()) || tableId.equals(t.getObjectId()))
                return t;
        }
        return null;
    }

    public Table findTableByName(String name) {
        if (name == null)
            return null;
        for (Table t : tables) {
            if (name.equals(t.name) || name.equals(t.getName()))
                return t;
        }
        return null;
    }

    public List<Table> listAllTables() {
        return new ArrayList<>(tables);
    }

    public void dropTable(String tableId) {
        Table t = getTable(tableId);
        if (t != null) {
            tables.remove(t);
            objects.remove(t);
        }
    }

    public void renameTable(String oldName, String newName) {
        Table t = findTableByName(oldName);
        if (t != null)
            t.setName(newName);
    }

    public void createView(View view) {
        if (view != null) {
            views.add(view);
            objects.add(view);
        }
    }

    public void dropView(String viewId) {
        View v = getView(viewId);
        if (v != null) {
            views.remove(v);
            objects.remove(v);
        }
    }

    public View getView(String viewId) {
        if (viewId == null)
            return null;
        for (View v : views) {
            if (viewId.equals(v.getObjectId()) || viewId.equals(v.getName()))
                return v;
        }
        return null;
    }

    public void createProcedure(StoredProcedure procedure) {
        if (procedure != null) {
            procedures.add(procedure);
            objects.add(procedure);
        }
    }

    public void dropProcedure(String procId) {
        StoredProcedure p = getProcedure(procId);
        if (p != null) {
            procedures.remove(p);
            objects.remove(p);
        }
    }

    public StoredProcedure getProcedure(String procId) {
        if (procId == null)
            return null;
        for (StoredProcedure p : procedures) {
            if (procId.equals(p.getObjectId()) || procId.equals(p.getName()))
                return p;
        }
        return null;
    }

    public void createSequence(Sequence sequence) {
        if (sequence != null) {
            sequences.add(sequence);
            objects.add(sequence);
        }
    }

    public void dropSequence(String seqId) {
        Sequence s = getSequence(seqId);
        if (s != null) {
            sequences.remove(s);
            objects.remove(s);
        }
    }

    public Sequence getSequence(String seqId) {
        if (seqId == null)
            return null;
        for (Sequence s : sequences) {
            if (seqId.equals(s.getObjectId()) || seqId.equals(s.getName()))
                return s;
        }
        return null;
    }
}
