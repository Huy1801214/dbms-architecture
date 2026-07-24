package dbms.catalog.index;

import java.util.List;

public class IndexKey {
    public List<Object> values;

    public IndexKey() {
    }

    public IndexKey(List<Object> values) {
        this.values = values;
    }
}
