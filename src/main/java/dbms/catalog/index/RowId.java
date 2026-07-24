package dbms.catalog.index;

import java.util.UUID;

public class RowId {
    public UUID pageId;
    public int slotNumber;

    public RowId() {
    }

    public RowId(UUID pageId, int slotNumber) {
        this.pageId = pageId;
        this.slotNumber = slotNumber;
    }
}
