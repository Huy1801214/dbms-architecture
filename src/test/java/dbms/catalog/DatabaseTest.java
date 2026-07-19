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
        Database database = new Database("db-001", "HuyDB`", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act
        database.rename("AnhHuyDB");
        // Assert
        assertEquals("AnhHuyDB", database.getName());
    }

    @Test
    public void shouldSetDatabaseOwner() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB`", "admin", DatabaseStatus.ONLINE, createdAt);
        // Act
        database.setOwner("user");
        // Assert
        assertEquals("user", database.getOwner());
    }

    @Test
    public void shouldRejectOperationWhenClosed() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 19, 10, 0);
        Database database = new Database("db-001", "HuyDB`", "admin", DatabaseStatus.OFFLINE, createdAt);
        // Act
        assertThrows(
                UnsupportedOperationException.class,
                () -> database.executeOperation());
    }

}