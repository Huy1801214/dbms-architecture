package dbms.catalog;

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
    public String collation;
    public CompressionType compression;
    public Boolean masked;
    public MaskingRule maskingRule;
    public Boolean encrypted;
    public EncryptionType encryptionType;
    public String comment;
    public ColumnStatus status;
}