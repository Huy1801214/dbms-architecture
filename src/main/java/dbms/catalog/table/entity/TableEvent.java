package dbms.catalog.table.entity;

import dbms.catalog.table.enums.TriggerEventType;
import dbms.catalog.table.enums.TriggerTime;

public class TableEvent {
    private final TriggerEventType eventType;
    private final TriggerTime triggerTime;
    private final Row oldRow;
    private final Row newRow;

    public TableEvent(TriggerEventType eventType, TriggerTime triggerTime, Row oldRow, Row newRow) {
        this.eventType = eventType;
        this.triggerTime = triggerTime;
        this.oldRow = oldRow;
        this.newRow = newRow;
    }

    public TriggerEventType getEventType() {
        return eventType;
    }

    public TriggerTime getTriggerTime() {
        return triggerTime;
    }

    public Row getOldRow() {
        return oldRow;
    }

    public Row getNewRow() {
        return newRow;
    }
}
