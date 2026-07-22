package dbms.catalog;

import org.junit.jupiter.api.Test;

import dbms.catalog.constraint.*;

import dbms.catalog.table.Column;

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
        PrimaryKey pk = new PrimaryKey("pk_id", List.of("id"));
        assertEquals("pk_id", pk.getConstraintName());
        assertEquals(ConstraintType.PRIMARY_KEY, pk.constraintType);
    }

    @Test
    public void shouldRejectDuplicatePrimaryKey() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldValidateForeignKey() {
        ForeignKey fk = new ForeignKey("fk_table", List.of("user_id"), "users", "id");
        assertEquals(ConstraintType.FOREIGN_KEY, fk.constraintType);
        assertEquals("users", fk.referenceTable);
    }

    @Test
    public void shouldRejectBrokenForeignKey() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldValidateUniqueConstraint() {
        UniqueConstraint unique = new UniqueConstraint("uq_email", List.of("email"));
        assertEquals(ConstraintType.UNIQUE, unique.constraintType);
    }

    @Test
    public void shouldRejectDuplicateUniqueValue() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldValidateCheckConstraint() {
        CheckConstraint check = new CheckConstraint("chk_age", List.of("age"), "age >= 18");
        assertEquals(ConstraintType.CHECK, check.constraintType);
        assertEquals("age >= 18", check.expression);
    }

    @Test
    public void shouldRejectInvalidCheckConstraint() {
        // Arrange

        // Act

        // Assert
    }

}
