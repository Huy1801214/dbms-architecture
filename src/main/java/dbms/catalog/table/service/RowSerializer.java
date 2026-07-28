package dbms.catalog.table.service;

import dbms.catalog.table.entity.Row;

import java.nio.charset.StandardCharsets;

public class RowSerializer {

    public static byte[] serializeRow(Row row) {
        if (row == null || row.rowId == null) {
            return new byte[0];
        }
        return row.rowId.getBytes(StandardCharsets.UTF_8);
    }

    public static Row deserializeRow(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        Row row = new Row();
        row.rowId = new String(data, StandardCharsets.UTF_8);
        return row;
    }
}
