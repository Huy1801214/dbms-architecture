package dbms.catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SchemaTest {

    @Test
    public void shouldCreateTable() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";

        Table table = new Table("tbl-001", "users", "InnoDB");

        // Act
        schema.createTable(table);

        // Assert
        Table retrieved = schema.getTable("tbl-001");
        assertNotNull(retrieved);
        assertEquals("users", retrieved.getName());
        assertEquals("tbl-001", retrieved.getTableId());
        assertEquals("InnoDB", retrieved.getEngine());
    }

    @Test
    public void shouldDropTable() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";

        Table table = new Table("tbl-001", "users", "InnoDB");
        schema.createTable(table);

        // Act
        schema.dropTable("tbl-001");

        // Assert
        assertNull(schema.getTable("tbl-001"));
    }

    @Test
    public void shouldRenameTable() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";

        Table table = new Table("tbl-001", "users", "InnoDB");
        schema.createTable(table);

        // Act
        schema.renameTable("users", "customers");

        // Assert
        Table retrieved = schema.getTable("tbl-001");
        assertNotNull(retrieved);
        assertEquals("customers", retrieved.getName());
    }

    @Test
    public void shouldFindTableByName() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        Table table = new Table("tbl-001", "users", "InnoDB");
        schema.createTable(table);

        // Act
        Table retrieved = schema.findTableByName("users");

        // Assert
        assertNotNull(retrieved);
        assertEquals("users", retrieved.getName());
    }

    @Test
    public void shouldListAllTables() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        Table table1 = new Table("tbl-001", "users", "InnoDB");
        Table table2 = new Table("tbl-002", "orders", "InnoDB");
        schema.createTable(table1);
        schema.createTable(table2);

        // Act
        java.util.List<Table> tables = schema.listAllTables();

        // Assert
        assertNotNull(tables);
        assertEquals(2, tables.size());
    }

    @Test
    public void shouldRejectDuplicateTableName() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        Table table = new Table("tbl-001", "users", "InnoDB");
        schema.createTable(table);

        Table duplicateTable = new Table("tbl-002", "users", "InnoDB");

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> schema.createTable(duplicateTable));
    }

    @Test
    public void shouldRejectUnknownTable() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> schema.dropTable("unknown-tbl-id"));
    }

    @Test
    public void shouldCreateView() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        View view = new View();
        view.queryDefinition = "SELECT * FROM users";

        // Act
        schema.createView(view);

        // Assert
        View retrieved = schema.getView("view-001");
        assertNotNull(retrieved);
    }

    @Test
    public void shouldDropView() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        View view = new View();
        view.queryDefinition = "SELECT * FROM users";
        schema.createView(view);

        // Act
        schema.dropView("view-001");

        // Assert
        assertNull(schema.getView("view-001"));
    }

    @Test
    public void shouldCreateStoredProcedure() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        StoredProcedure procedure = new StoredProcedure();

        // Act
        schema.createProcedure(procedure);

        // Assert
        StoredProcedure retrieved = schema.getProcedure("proc-001");
        assertNotNull(retrieved);
    }

    @Test
    public void shouldDropStoredProcedure() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        StoredProcedure procedure = new StoredProcedure();
        schema.createProcedure(procedure);

        // Act
        schema.dropProcedure("proc-001");

        // Assert
        assertNull(schema.getProcedure("proc-001"));
    }

    @Test
    public void shouldCreateSequence() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        Sequence sequence = new Sequence();

        // Act
        schema.createSequence(sequence);

        // Assert
        Sequence retrieved = schema.getSequence("seq-001");
        assertNotNull(retrieved);
    }

    @Test
    public void shouldDropSequence() {
        // Arrange
        Schema schema = new Schema();
        schema.schemaId = "schema-001";
        schema.name = "StudentSchema";
        schema.owner = "admin";
        Sequence sequence = new Sequence();
        schema.createSequence(sequence);

        // Act
        schema.dropSequence("seq-001");

        // Assert
        assertNull(schema.getSequence("seq-001"));
    }
}