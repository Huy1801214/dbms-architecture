package dbms.catalog.table;

public class Trigger implements TableEventListener {
    private String triggerName;
    private TriggerEventType eventType;
    private TriggerTime triggerTime;
    private String actionBody;

    public Trigger(String name, TriggerEventType eventType, TriggerTime time, String actionBody) {
        this.triggerName = name;
        this.eventType = eventType;
        this.triggerTime = time;
        this.actionBody = actionBody;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public TriggerEventType getEventType() {
        return eventType;
    }

    public TriggerTime getTriggerTime() {
        return triggerTime;
    }

    public String getActionBody() {
        return actionBody;
    }

    @Override
    public void onEvent(TableEvent event, Table table) {
        if (event != null && event.getEventType() == this.eventType && event.getTriggerTime() == this.triggerTime) {
            executeAction(event, table);
        }
    }

    private void executeAction(TableEvent event, Table table) {
        System.out.println("Trigger " + triggerName + " executed on " + (table != null ? table.getName() : "table"));
    }
}
