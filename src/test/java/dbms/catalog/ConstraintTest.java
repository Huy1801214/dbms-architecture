package dbms.catalog;

import org.junit.jupiter.api.Test;

import dbms.catalog.constraint.*;

import dbms.catalog.table.Column;
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
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        PrimaryKeyRequest request = new PrimaryKeyRequest("pk_id", List.of("id"));
        PrimaryKey primaryKey = factory.createPrimaryKey(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
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
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        PrimaryKeyRequest request = new PrimaryKeyRequest("pk_id", List.of("id"));
        PrimaryKey primaryKey = factory.createPrimaryKey(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addConstraint(primaryKey);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of(1);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> table.validate(row));
    }

    @Test
    public void shouldValidateForeignKey() {
        // Arrange
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        ForeignKeyRequest request = new ForeignKeyRequest("fk_user", List.of("user_id"), "users", "id");
        ForeignKey foreignKey = factory.createForeignKey(request);
        Table table = new Table("tbl-002", "orders", "InnoDB");
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
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        ForeignKeyRequest request = new ForeignKeyRequest("fk_user", List.of("user_id"), "users", "id");
        ForeignKey foreignKey = factory.createForeignKey(request);
        Table table = new Table("tbl-002", "orders", "InnoDB");
        table.addConstraint(foreignKey);
        Row childRow = new Row();
        childRow.rowId = "row-002";
        childRow.values = List.of("unknown-parent");

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> table.validate(childRow));
    }

    @Test
    public void shouldValidateUniqueConstraint() {
        // Arrange
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        UniqueConstraintRequest request = new UniqueConstraintRequest("uq_email", List.of("email"));
        UniqueConstraint uniqueConstraint = factory.createUnique(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
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
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        UniqueConstraintRequest request = new UniqueConstraintRequest("uq_email", List.of("email"));
        UniqueConstraint uniqueConstraint = factory.createUnique(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addConstraint(uniqueConstraint);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of("user@example.com");

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> table.validate(row));
    }

    @Test
    public void shouldValidateCheckConstraint() {
        // Arrange
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        CheckConstraintRequest request = new CheckConstraintRequest("chk_age", List.of("age"), "age >= 18");
        CheckConstraint checkConstraint = factory.createCheck(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
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
        DefaultConstraintFactory factory = new DefaultConstraintFactory();
        CheckConstraintRequest request = new CheckConstraintRequest("chk_age", List.of("age"), "age >= 18");
        CheckConstraint checkConstraint = factory.createCheck(request);
        Table table = new Table("tbl-001", "users", "InnoDB");
        table.addConstraint(checkConstraint);
        Row row = new Row();
        row.rowId = "row-001";
        row.values = List.of(15);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> table.validate(row));
    }

}

