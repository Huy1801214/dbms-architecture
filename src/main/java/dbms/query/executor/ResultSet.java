package dbms.query.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultSet {

    private List<Map<String, Object>> rows;

    public ResultSet() {
        this.rows = new ArrayList<>();
    }

    public ResultSet(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

}
