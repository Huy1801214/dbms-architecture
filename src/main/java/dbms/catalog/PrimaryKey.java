package dbms.catalog;
import java.util.List;

public class PrimaryKey extends Constraint {
    public List<String> columns;

    @Override
    public boolean validate(Row row) {
        return false;
    }
}