package dbms.metadata.domain;

public class FileHeader {
    private final Long magicNumber;
    private final Integer version, pageSize;
    private final Long pageCount;

    public FileHeader(Long magicNumber, Integer version, Integer pageSize, Long pageCount) {
        this.magicNumber = magicNumber;
        this.version = version;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
    }

    public Long getMagicNumber() {
        return magicNumber;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getPageCount() {
        return pageCount;
    }
}
