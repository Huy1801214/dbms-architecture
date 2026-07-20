package dbms.catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class DatabaseTest {

    @Test
    public void shouldOpenDatabase() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "ABC", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Act
        database.open();
        // Assert
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldCloseDatabase() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "ABC", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act
        database.close();
        // Assert
        assertEquals(DatabaseStatus.OFFLINE, database.getStatus());
    }

    @Test
    public void shouldRenameDatabase() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act
        database.rename("AnhHuyDB");
        // Assert
        assertEquals("AnhHuyDB", database.getName());
    }

    @Test
    public void shouldSetDatabaseOwner() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act
        database.setOwner("user");
        // Assert
        assertEquals("user", database.getOwner());
    }

    @Test
    public void shouldRejectOperationWhenClosed() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Act
        assertThrows(
                UnsupportedOperationException.class,
                () -> database.executeOperation());
    }

    @Test
    public void shouldRejectOpenWhenAlreadyOnline() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                database::open);
    }

    @Test
    public void shouldRejectCloseWhenAlreadyOffline() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                database::close);
    }

    @Test
    public void shouldRejectRenameWhenDatabaseIsOpening() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OPENING, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename("AnhHuyDB"));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.OPENING, database.getStatus());
    }

    @Test
    public void shouldRejectRenameWhenDatabaseIsClosing() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.CLOSING, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename("AnhHuyDB"));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.CLOSING, database.getStatus());
    }

    @Test
    public void shouldRejectEmptyDatabaseName() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename(""));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectNullOwner() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.setOwner(null));
        assertEquals("admin", database.getOwner());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectNullDatabaseName() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename(null));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectBlankDatabaseName() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename("     "));

        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectDatabaseNameWithSpecialCharacters() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename("Student@DB"));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectDatabaseNameExceedingMaxLength() {
        // Arrange
        String longDatabaseName = "A".repeat(65);
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename(longDatabaseName));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldRejectReservedDatabaseName() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.rename("system"));
        assertEquals("HuyDB", database.getName());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

    @Test
    public void shouldInitializeOfflineDatabase() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        // Act
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Assert
        assertEquals(DatabaseStatus.OFFLINE, database.getStatus());
    }

    @Test
    public void shouldMaintainStatusTransition() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Act
        database.open();
        // Assert
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
        // Act
        database.close();
        // Assert
        assertEquals(DatabaseStatus.OFFLINE, database.getStatus());
    }

    @Test
    public void shouldKeepCreatedTimeUnchanged() {
        // Arrange
        LocalDateTime originalCreatedAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.ONLINE, originalCreatedAt);
        // Act
        database.rename("NewName");
        database.setOwner("newOwner");
        database.close();
        // Assert
        assertEquals(originalCreatedAt, database.getCreatedAt());
    }

    @Test
    public void shouldRejectNullDatabaseStatus() {
        // Arrange
        LocalDateTime originalCreatedAt = LocalDateTime.of(2026, 7, 20, 10, 0);

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> new Database("db-001", "HuyDB", "admin", null, originalCreatedAt));
    }

    @Test
    public void shouldRejectInvalidStatusTransition() {
        // Arrange
        LocalDateTime originalCreatedAt = LocalDateTime.of(2026, 7, 20, 10, 0);
        Database database = new Database("db-001", "HuyDB", "admin", DatabaseStatus.OPENING, originalCreatedAt);
        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> database.open());
        assertEquals(DatabaseStatus.ONLINE, database.getStatus());
    }

}