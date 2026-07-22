package dbms.catalog.sequence;

public class SequenceCreateRequest {
    public String name;
    public long start;
    public long increment;

    public SequenceCreateRequest() {}

    public SequenceCreateRequest(String name, long start, long increment) {
        this.name = name;
        this.start = start;
        this.increment = increment;
    }
}
