package dbms.catalog.table;

import java.util.UUID;

public class Column {
    public UUID columnId;
    public String name;
    public DataType dataType;
    public Integer length;
    public Integer precision;
    public Integer scale;
    public Boolean nullable;
    public Object defaultValue;
    public Boolean identity;
    public Boolean generated;
    public ColumnStatus status;

    public Column() {
        this.columnId = UUID.randomUUID();
        this.nullable = true;
        this.identity = false;
        this.generated = false;
        this.status = ColumnStatus.ACTIVE;
    }

    public Column(String name, DataType dataType) {
        this();
        this.name = name;
        this.dataType = dataType;
    }

    public Column(String name, DataType dataType, Boolean nullable) {
        this(name, dataType);
        this.nullable = nullable;
    }
}
