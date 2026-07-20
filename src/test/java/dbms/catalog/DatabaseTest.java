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
        // Act

        // Assert
    }

    @Test
    public void shouldInitializeOfflineDatabase() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldMaintainStatusTransition() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldKeepCreatedTimeUnchanged() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldRejectNullDatabaseStatus() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldRejectInvalidStatusTransition() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldCloseAndReopenDatabase() {
        // Arrange

        // Act

        // Assert
    }

}