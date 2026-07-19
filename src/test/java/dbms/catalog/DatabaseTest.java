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

        // Act

        // Assert
    }

    @Test
    public void shouldRenameDatabase() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldSetDatabaseOwner() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldUpdateDatabaseStatus() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void shouldRejectOperationWhenClosed() {
        // Arrange

        // Act

        // Assert
    }

}