package dbms.metadata.domain;

public class FileStatistics {
    private final Long usedSize, freeSize;

    public FileStatistics(Long usedSize, Long freeSize) {
        this.usedSize = usedSize;
        this.freeSize = freeSize;
    }

    public Long getUsedSize() {
        return this.usedSize;
    }

    public Long getFreeSize() {
        return this.freeSize;
    }
}
