package dbms.catalog;

public class Sequence extends DatabaseObject {
    public long start;
    public long increment;

    public Sequence() {}

    public Sequence(String seqId, String name, long start, long increment) {
        this.objectId = seqId;
        this.name = name;
        this.start = start;
        this.increment = increment;
    }

    public long nextValue() {
        return 0;
    }

    @Override
    public void create() {}

    @Override
    public void drop() {}

    @Override
    public void rename(String newName) {
        this.name = newName;
    }
}