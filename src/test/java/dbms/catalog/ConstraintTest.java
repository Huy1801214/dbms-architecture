package dbms.catalog;

import org.junit.jupiter.api.Test;

import dbms.catalog.constraint.*;
import dbms.catalog.table.Column;
import dbms.catalog.table.DataType;
import dbms.catalog.table.Row;
import dbms.catalog.table.Table;

import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ConstraintTest {

    @Test
    public void shouldInitializeConstraintAttributes() {
        // Arrange
        UUID tableId = UUID.randomUUID();
        Column col = new Column();
        col.name = "id";

        // Act
        PrimaryKey pk = new PrimaryKey("pk_users", List.of(col), tableId);

        // Assert
        assertNotNull(pk.constraintId);
        assertEquals("pk_users", pk.constraintName);
        assertEquals(ConstraintType.PRIMARY_KEY, pk.constraintType);
        assertEquals(tableId, pk.tableId);
        assertEquals(1, pk.columns.size());
        assertEquals("id", pk.columns.get(0).name);
        assertEquals(ConstraintStatus.ACTIVE, pk.status);
        assertTrue(pk.enabled);
        assertTrue(pk.validated);
        assertNotNull(pk.createdAt);
        assertNotNull(pk.modifiedAt);
    }

    @Test
    public void shouldValidatePrimaryKey() {
        // Arrange
        Column idCol = new Column("id", DataType.INT, false);
        PrimaryKey primaryKey = new PrimaryKey("pk_id", List.of(idCol), UUID.randomUUID());
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(idCol);
        table.addConstraint(primaryKey);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of(1);

        // Act + Assert
        assertDoesNotThrow(() -> table.validate(row));
    }

    @Test
    public void shouldRejectDuplicatePrimaryKey() {
        // Arrange
        Column idCol = new Column("id", DataType.INT, false);
        PrimaryKey primaryKey = new PrimaryKey("pk_id", List.of(idCol), UUID.randomUUID());
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(idCol);
        table.addConstraint(primaryKey);
        Row row1 = new Row();
        row1.rowId = "row-001";
        row1.values = List.of(1);
        table.insert(row1);

        Row row2 = new Row();
        row2.rowId = "row-002";
        row2.values = List.of(1);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> table.validate(row2));
    }

    @Test
    public void shouldValidateForeignKey() {
        // Arrange
        Column userIdCol = new Column("user_id", DataType.INT, false);
        ForeignKey foreignKey = new ForeignKey("fk_user", List.of(userIdCol), UUID.randomUUID(), "users", "id");
        Table table = new Table("tbl-002", "orders", "InnoDB");
        table.addColumn(userIdCol);
        table.addConstraint(foreignKey);
        Row childRow = new Row();
        childRow.rowId = "row-002";
        childRow.values = List.of("parent-001");

        // Act + Assert
        assertDoesNotThrow(() -> table.validate(childRow));
    }

    @Test
    public void shouldRejectBrokenForeignKey() {
        // Arrange
        Table.clearAllTablesRegistry();
        Column userIdCol = new Column("user_id", DataType.INT, false);
        ForeignKey foreignKey = new ForeignKey("fk_user", List.of(userIdCol), UUID.randomUUID(), "users", "id");
        Table table = new Table("tbl-002", "orders", "InnoDB");
        table.addColumn(userIdCol);
        table.addConstraint(foreignKey);
        
        // Also construct parent table "users" with id primary key
        Table parentTable = new Table("tbl-001", "users", "InnoDB");
        Column parentIdCol = new Column("id", DataType.INT, false);
        parentTable.addColumn(parentIdCol);
        PrimaryKey parentPk = new PrimaryKey("pk_id", List.of(parentIdCol), UUID.randomUUID());
        parentTable.addConstraint(parentPk);
        
        Row parentRow = new Row();
        parentRow.rowId = "row-001";
        parentRow.values = List.of("parent-001");
        parentTable.insert(parentRow);

        Row childRow = new Row();
        childRow.rowId = "row-002";
        childRow.values = List.of("unknown-parent");

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> table.validate(childRow));
    }

    @Test
    public void shouldValidateUniqueConstraint() {
        // Arrange
        Column emailCol = new Column("email", DataType.VARCHAR, true);
        UniqueConstraint uniqueConstraint = new UniqueConstraint("uq_email", List.of(emailCol), UUID.randomUUID());
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(emailCol);
        table.addConstraint(uniqueConstraint);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of("user@example.com");

        // Act + Assert
        assertDoesNotThrow(() -> table.validate(row));
    }

    @Test
    public void shouldRejectDuplicateUniqueValue() {
        // Arrange
        Column emailCol = new Column("email", DataType.VARCHAR, true);
        UniqueConstraint uniqueConstraint = new UniqueConstraint("uq_email", List.of(emailCol), UUID.randomUUID());
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(emailCol);
        table.addConstraint(uniqueConstraint);
        
        Row row1 = new Row();
        row1.rowId = "row-001";
        row1.values = List.of("user@example.com");
        table.insert(row1);

        Row row2 = new Row();
        row2.rowId = "row-002";
        row2.values = List.of("user@example.com");

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> table.validate(row2));
    }

    @Test
    public void shouldValidateCheckConstraint() {
        // Arrange
        Column ageCol = new Column("age", DataType.INT, true);
        CheckConstraint checkConstraint = new CheckConstraint("chk_age", List.of(ageCol), UUID.randomUUID(), "age >= 18");
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(ageCol);
        table.addConstraint(checkConstraint);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of(20);

        // Act + Assert
        assertDoesNotThrow(() -> table.validate(row));
    }

    @Test
    public void shouldRejectInvalidCheckConstraint() {
        // Arrange
        Column ageCol = new Column("age", DataType.INT, true);
        CheckConstraint checkConstraint = new CheckConstraint("chk_age", List.of(ageCol), UUID.randomUUID(), "age >= 18");
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addColumn(ageCol);
        table.addConstraint(checkConstraint);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of(15);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> table.validate(row));
    }
}


