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
    private LifecycleStatus lifecycleStatus = LifecycleStatus.ACTIVE;

    private List<DatabaseObject> objects = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private List<StoredProcedure> procedures = new ArrayList<>();
    private List<Sequence> sequences = new ArrayList<>();
    private DatabaseObjectFactory factory = new DefaultDatabaseObjectFactory();

    public Schema() {
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
    }

    public Schema(String schemaId, String name, String owner) {
        this.schemaId = schemaId;
        this.name = name;
        this.owner = owner;
        this.lifecycleStatus = LifecycleStatus.ACTIVE;
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
        if (schemaId == null)
            return null;
        try {
            return UUID.fromString(schemaId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String getQualifiedName() {
        return "SCHEMA:" + name;
    }

    @Override
    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    @Override
    public void rename(String newName) {
        if (this.lifecycleStatus != LifecycleStatus.ACTIVE) {
            throw new IllegalStateException("Schema is not active");
        }
        this.name = newName;
    }

    @Override
    public void drop(DropMode mode) {
        if (this.lifecycleStatus != LifecycleStatus.ACTIVE) {
            throw new IllegalStateException("Schema is not active");
        }
        if (mode == DropMode.RESTRICT && !objects.isEmpty()) {
            throw new IllegalStateException("Cannot drop schema with active objects in RESTRICT mode");
        }
        this.lifecycleStatus = LifecycleStatus.DROPPING;
        if (mode == DropMode.CASCADE) {
            List<DatabaseObject> objectsToDrop = new ArrayList<>(objects);
            for (DatabaseObject obj : objectsToDrop) {
                obj.drop(mode);
            }
            objects.clear();
            tables.clear();
            views.clear();
            procedures.clear();
            sequences.clear();
        }
        this.lifecycleStatus = LifecycleStatus.DROPPED;
    }

    @Override
    public List<DatabaseComponent> getChildren() {
        return new ArrayList<>(objects);
    }

    public void removeObject(UUID objectId) {
        if (objectId == null)
            return;
        removeObject(objectId.toString());
    }

    public Table createTable(TableCreateRequest request) {
        if (request == null || request.name == null) {
            throw new IllegalArgumentException("Invalid table create request");
        }
        if (findTableByName(request.name) != null) {
            throw new IllegalStateException("Table already exists: " + request.name);
        }
        Table table = new Table(java.util.UUID.randomUUID().toString(), request.name, request.engine);
        if (request.columns != null) {
            for (Column col : request.columns) {
                table.addColumn(col);
            }
        }
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
            if (obj instanceof Table)
                tables.remove(obj);
            else if (obj instanceof View)
                views.remove(obj);
            else if (obj instanceof StoredProcedure)
                procedures.remove(obj);
            else if (obj instanceof Sequence)
                sequences.remove(obj);
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
        if (obj != null) {
            if (obj instanceof Table && findTableByName(obj.getName()) != null) {
                throw new IllegalStateException("Duplicate table name: " + obj.getName());
            }
            objects.add(obj);
            if (obj instanceof Table)
                tables.add((Table) obj);
            else if (obj instanceof View)
                views.add((View) obj);
            else if (obj instanceof StoredProcedure)
                procedures.add((StoredProcedure) obj);
            else if (obj instanceof Sequence)
                sequences.add((Sequence) obj);
        }
    }

    public void removeObject(String objectId) {
        dropObject(objectId);
    }

    private DatabaseObject findObjectById(String objectId) {
        if (objectId == null)
            return null;
        for (DatabaseObject obj : objects) {
            if (objectId.equals(obj.getObjectId()) || objectId.equals(obj.getName()))
                return obj;
        }
        return null;
    }

    public void createTable(Table table) {
        if (table != null) {
            if (findTableByName(table.getName()) != null) {
                throw new IllegalStateException("Duplicate table name: " + table.getName());
            }
            tables.add(table);
            objects.add(table);
        }
    }

    public Table getTable(String tableId) {
        if (tableId == null)
            return null;
        for (Table t : tables) {
            if (tableId.equals(t.getTableId()) || tableId.equals(t.getObjectId()) || tableId.equals(t.getName()))
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
        if (t == null) {
            throw new IllegalStateException("Table not found: " + tableId);
        }
        tables.remove(t);
        objects.remove(t);
    }

    public void renameTable(String oldName, String newName) {
        Table t = findTableByName(oldName);
        if (t != null)
            t.setName(newName);
    }

    public void createView(View view) {
        if (view != null) {
            if (view.objectId == null) {
                view.objectId = "view-" + String.format("%03d", views.size() + 1);
            }
            if (view.name == null) {
                view.name = view.objectId;
            }
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
            if (procedure.objectId == null) {
                procedure.objectId = "proc-" + String.format("%03d", procedures.size() + 1);
            }
            if (procedure.name == null) {
                procedure.name = procedure.objectId;
            }
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
            if (sequence.objectId == null) {
                sequence.objectId = "seq-" + String.format("%03d", sequences.size() + 1);
            }
            if (sequence.name == null) {
                sequence.name = sequence.objectId;
            }
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
