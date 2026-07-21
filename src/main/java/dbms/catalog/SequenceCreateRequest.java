package dbms.catalog;

public class SequenceCreateRequest {
    private String seqId;
    private String name;
    private long start;
    private long increment;

    public SequenceCreateRequest(String seqId, String name, long start, long increment) {
        this.seqId = seqId;
        this.name = name;
        this.start = start;
        this.increment = increment;
    }

    public String getSeqId() {
        return seqId;
    }

    public String getName() {
        return name;
    }

    public long getStart() {
        return start;
    }

    public long getIncrement() {
        return increment;
    }
}
