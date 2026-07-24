package dbms.catalog.index;

import java.util.List;

public class IndexKey {
    private List<Object> values;

    public IndexKey() {
    }

    public IndexKey(List<Object> values) {
        this.values = values;
    }

    public List<Object> getValues() {
        return values;
    }
}
