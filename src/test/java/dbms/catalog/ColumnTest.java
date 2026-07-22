package dbms.catalog;

import org.junit.jupiter.api.Test;

import dbms.catalog.table.Column;
import dbms.catalog.table.ColumnStatus;
import dbms.catalog.table.DataType;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnTest {

    @Test
    public void shouldCreateColumn() {
        Column col = new Column("user_id", DataType.INT);
        assertNotNull(col.columnId);
        assertEquals("user_id", col.name);
        assertEquals(DataType.INT, col.dataType);
        assertTrue(col.nullable);
        assertEquals(ColumnStatus.ACTIVE, col.status);
    }

    @Test
    public void shouldValidateColumnDefinition() {
        Column col = new Column("email", DataType.VARCHAR, false);
        col.length = 255;
        col.defaultValue = "user@example.com";

        assertEquals("email", col.name);
        assertEquals(DataType.VARCHAR, col.dataType);
        assertFalse(col.nullable);
        assertEquals(255, col.length);
        assertEquals("user@example.com", col.defaultValue);
    }

    @Test
    public void shouldAcceptNullableColumn() {
        Column col = new Column("middle_name", DataType.VARCHAR, true);
        assertTrue(col.nullable);
    }

    @Test
    public void shouldUpdateColumnMetadata() {
        Column col = new Column("salary", DataType.DOUBLE);
        col.precision = 10;
        col.scale = 2;

        assertEquals(10, col.precision);
        assertEquals(2, col.scale);
    }

    @Test
    public void shouldChangeDefaultValue() {
        Column col = new Column("is_active", DataType.BOOLEAN);
        col.defaultValue = true;

        assertEquals(true, col.defaultValue);
    }

}