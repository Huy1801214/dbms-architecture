package dbms.catalog.table;

import java.util.List;
import java.util.UUID;

public class TableMetadataProxy extends Table {
    private boolean isLoaded = false;
    private final MetadataLoader metadataLoader;

    public TableMetadataProxy(UUID tableId, String name, String engine, MetadataLoader loader) {
        super(Table.builder().setTableId(tableId).setName(name != null ? name : "unnamed").setEngine(engine));
        this.metadataLoader = loader;
    }

    @Override
    public List<Column> getColumns() {
        lazyLoad();
        return super.getColumns();
    }

    private synchronized void lazyLoad() {
        if (!isLoaded) {
            if (metadataLoader != null && getTableId() != null) {
                try {
                    UUID id = UUID.fromString(getTableId());
                    List<Column> loadedColumns = metadataLoader.loadColumns(id);
                    for (Column col : loadedColumns) {
                        super.addColumn(col);
                    }
                } catch (IllegalArgumentException e) {
                    // Fallback if table ID is not a valid UUID string
                }
            }
            isLoaded = true;
            System.out.println("TableMetadataProxy: Column metadata loaded from disk and cached.");
        }
    }
}
