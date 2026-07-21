package dbms.catalog;

public class DefaultDatabaseObjectFactory implements DatabaseObjectFactory {
    @Override
    public Table createTable(TableCreateRequest request) {
        return null;
    }

    @Override
    public View createView(ViewCreateRequest request) {
        return null;
    }

    @Override
    public StoredProcedure createProcedure(ProcedureCreateRequest request) {
        return null;
    }

    @Override
    public Sequence createSequence(SequenceCreateRequest request) {
        return null;
    }
}
