package dbms.catalog;

import dbms.catalog.table.entity.Column;
import dbms.catalog.table.entity.Row;
import dbms.catalog.table.entity.Table;
import dbms.catalog.table.entity.TableEvent;
import dbms.catalog.table.entity.Trigger;
import dbms.catalog.table.enums.DataType;
import dbms.catalog.table.enums.TriggerEventType;
import dbms.catalog.table.enums.TriggerTime;
import dbms.catalog.table.service.MetadataLoader;
import dbms.catalog.table.service.TableMetadataProxy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    public void shouldInsertRow() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row = new Row();
        row.rowId = "row-001";
        row.values = java.util.Arrays.asList("John", 30);

        // Act
        table.insert(row);

        // Assert
        assertEquals(1, table.getRowCount());
    }

    @Test
    public void shouldUpdateRow() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row originalRow = new Row();
        originalRow.rowId = "row-001";
        originalRow.values = java.util.Arrays.asList("John", 30);
        table.insert(originalRow);

        Row newRow = new Row();
        newRow.rowId = "row-001";
        newRow.values = java.util.Arrays.asList("John", 31);

        // Act
        table.update("row-001", newRow);

        // Assert
        Row retrieved = table.findRowById("row-001");
        assertNotNull(retrieved);
        assertEquals(newRow, retrieved);
    }

    @Test
    public void shouldDeleteRow() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row = new Row();
        row.rowId = "row-001";
        row.values = java.util.Arrays.asList("John", 30);
        table.insert(row);

        // Act
        table.delete("row-001");

        // Assert
        assertNull(table.findRowById("row-001"));
    }

    @Test
    public void shouldTruncateTable() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row1 = new Row();
        row1.rowId = "row-001";
        row1.values = java.util.Arrays.asList("John", 30);
        Row row2 = new Row();
        row2.rowId = "row-002";
        row2.values = java.util.Arrays.asList("Jane", 25);
        table.insert(row1);
        table.insert(row2);

        // Act
        table.truncate();

        // Assert
        assertEquals(0, table.getRowCount());
    }

    @Test
    public void shouldFindRowById() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row = new Row();
        row.rowId = "row-001";
        row.values = java.util.Arrays.asList("John", 30);
        table.insert(row);

        // Act
        Row retrieved = table.findRowById("row-001");

        // Assert
        assertNotNull(retrieved);
        assertEquals("row-001", retrieved.rowId);
    }

    @Test
    public void shouldListAllRows() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row1 = new Row();
        row1.rowId = "row-001";
        row1.values = java.util.Arrays.asList("John", 30);
        Row row2 = new Row();
        row2.rowId = "row-002";
        row2.values = java.util.Arrays.asList("Jane", 25);
        table.insert(row1);
        table.insert(row2);

        // Act
        java.util.List<Row> rows = table.listAllRows();

        // Assert
        assertNotNull(rows);
        assertEquals(2, rows.size());
    }

    @Test
    public void shouldRejectDuplicateRow() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row row = new Row();
        row.rowId = "row-001";
        row.values = java.util.Arrays.asList("John", 30);
        table.insert(row);

        Row duplicateRow = new Row();
        duplicateRow.rowId = "row-001";
        duplicateRow.values = java.util.Arrays.asList("Duplicate", 99);

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> table.insert(duplicateRow));
    }

    @Test
    public void shouldRejectUnknownRow() {
        // Arrange
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Row newRow = new Row();
        newRow.rowId = "row-999";
        newRow.values = java.util.Arrays.asList("Unknown", 99);

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> table.update("row-999", newRow));
    }

    @Test
    public void shouldAddAndGetColumns() {
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Column col1 = new Column("id", DataType.INT, false);
        Column col2 = new Column("name", DataType.VARCHAR, true);

        table.addColumn(col1);
        table.addColumn(col2);

        assertEquals(2, table.getColumns().size());
        assertEquals("id", table.getColumns().get(0).name);
    }

    @Test
    public void shouldReturnEngine() {
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        assertEquals("InnoDB", table.getEngine());
    }

    @Test
    public void shouldLazyLoadColumnsOnFirstAccess() {
        java.util.UUID tableId = java.util.UUID.randomUUID();
        MetadataLoader loader = new MetadataLoader();
        Table proxyTable = new TableMetadataProxy(tableId, "lazy_users", "InnoDB", loader);

        java.util.List<Column> cols = proxyTable.getColumns();

        assertNotNull(cols);
        assertEquals(2, cols.size());
        assertEquals("id", cols.get(0).name);
    }

    @Test
    public void shouldFailValidationWhenColumnIsNullAndNotNullable() {
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        Column col1 = new Column("id", DataType.INT, false);
        table.addColumn(col1);

        Row row = new Row();
        row.rowId = "row-001";
        row.values = new java.util.ArrayList<>();
        row.values.add(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> table.validateConstraints(row));
    }

    @Test
    public void shouldNotifyRegisteredTriggersOnRowInsert() {
        Table table = Table.builder().setName("users").setEngine("InnoDB").build();
        java.util.concurrent.atomic.AtomicInteger executedCount = new java.util.concurrent.atomic.AtomicInteger(0);

        Trigger auditTrigger = new Trigger("trg_audit", TriggerEventType.INSERT, TriggerTime.AFTER, "log audit") {
            @Override
            public void onEvent(TableEvent event, Table t) {
                if (event != null && event.getEventType() == TriggerEventType.INSERT && event.getTriggerTime() == TriggerTime.AFTER) {
                    executedCount.incrementAndGet();
                }
            }
        };

        table.registerTrigger(auditTrigger);

        Row row = new Row();
        row.rowId = "row-100";
        row.values = new java.util.ArrayList<>();
        table.insert(row);

        assertEquals(1, executedCount.get());
    }
}
