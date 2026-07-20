package dbms.server;

import org.junit.jupiter.api.Test;

import dbms.catalog.Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DatabaseManagerTest {

    @Test
    public void shouldCreateDatabase() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        // Act
        Database database = manager.createDatabase("StudentDB", "admin");
        // Assert
        assertNotNull(database);
        assertEquals("StudentDB", database.getName());
        assertEquals(1, manager.listAllDatabases().size());
    }

    @Test
    public void shouldDropDatabase() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        Database database = manager.createDatabase("StudentDB", "admin");
        String databaseId = database.getDatabaseId();
        assertEquals(1, manager.listAllDatabases().size());
        // Act
        manager.dropDatabase(databaseId);
        // Assert
        assertNull(manager.findDatabaseById(databaseId));
        assertEquals(0, manager.listAllDatabases().size());
    }

    @Test
    public void shouldRenameDatabase() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        Database database = manager.createDatabase("StudentDB", "admin");
        String databaseId = database.getDatabaseId();
        // Act
        manager.renameDatabase(databaseId, "SchoolDB");
        // Assert
        assertEquals("SchoolDB", database.getName());
    }

    @Test
    public void shouldGetDatabaseByName() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        Database database = manager.createDatabase("StudentDB", "admin");
        // Act
        Database foundDatabase = manager.findDatabaseByName("StudentDB");
        // Assert
        assertNotNull(foundDatabase);
        assertEquals("StudentDB", foundDatabase.getName());
    }

    @Test
    public void shouldListAllDatabases() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();

        manager.createDatabase("StudentDB", "admin");
        manager.createDatabase("SchoolDB", "admin");
        // Act
        List<Database> databases = manager.listAllDatabases();
        // Assert
        assertEquals(2, databases.size());

        assertTrue(databases.stream()
                .anyMatch(db -> db.getName().equals("StudentDB")));

        assertTrue(databases.stream()
                .anyMatch(db -> db.getName().equals("SchoolDB")));
    }

    @Test
    public void shouldRejectDuplicateDatabaseName() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        manager.createDatabase("StudentDB", "admin");

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> {
            manager.createDatabase("StudentDB", "admin");
        });
        assertEquals(1, manager.listAllDatabases().size());
    }

    @Test
    public void shouldRejectUnknownDatabase() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        String unknownId = "unknown-db-id";

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> {
            manager.dropDatabase(unknownId);
        });
    }

    @Test
    public void shouldIncreaseDatabaseCountAfterCreation() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        assertEquals(0, manager.listAllDatabases().size());
        // Act
        manager.createDatabase("StudentDB", "admin");
        // Assert
        assertEquals(1, manager.listAllDatabases().size());
    }

    @Test
    public void shouldDecreaseDatabaseCountAfterDrop() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        Database database = manager.createDatabase("StudentDB", "admin");
        String databaseId = database.getDatabaseId();
        assertEquals(1, manager.listAllDatabases().size());

        // Act
        manager.dropDatabase(databaseId);

        // Assert
        assertEquals(0, manager.listAllDatabases().size());
    }

    @Test
    public void shouldAssignUniqueDatabaseId() {
        // Arrange
        DatabaseManager manager = new DatabaseManager();
        // Act
        Database database1 = manager.createDatabase("StudentDB", "admin");
        Database database2 = manager.createDatabase("SchoolDB", "admin");
        String database1Id = database1.getDatabaseId();
        String database2Id = database2.getDatabaseId();
        // Assert
        assertNotEquals(database1Id, database2Id);
    }

}