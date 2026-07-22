package dbms.catalog;

import dbms.catalog.schema.Schema;
import dbms.catalog.table.Table;
import java.util.Map;

public class CatalogManager {
    public Map<String, Object> metadataCache;

    public void registerTable(Table table) {}
    public Table getTable(String tableName) { return null; }
    public Schema getSchema(String schemaName) { return null; }
    public void refreshMetadata() {}
}
