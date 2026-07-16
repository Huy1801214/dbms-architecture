package dbms.query.executor;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {

    private List<Object> rows;

    public ResultSet() {
        this.rows = new ArrayList<>();
    }

    public ResultSet(List<Object> rows) {
        this.rows = rows;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

}
