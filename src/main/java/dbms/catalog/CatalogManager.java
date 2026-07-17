package dbms.catalog;
import java.util.Map;

public class CatalogManager {
    public Map<String, Object> metadataCache;

    public void registerTable(Table table) {}
    public Table getTable(String tableName) { return null; }
    public Schema getSchema(String schemaName) { return null; }
    public void refreshMetadata() {}
}