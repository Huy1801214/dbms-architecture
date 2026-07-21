package dbms.catalog;

public interface DatabaseObjectFactory {
    Table createTable(TableCreateRequest request);
    View createView(ViewCreateRequest request);
    StoredProcedure createProcedure(ProcedureCreateRequest request);
    Sequence createSequence(SequenceCreateRequest request);
}
