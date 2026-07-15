package dbms.metadata.request;

import dbms.metadata.domain.FileType;

public class CreateDatabaseMetadataRequest {
    private String databaseName, filePath;
    private FileType fileType;
    private Integer pageSize;
    private Long initialSize, maxSize;
    private Boolean autoGrowthEnabled;
    private Long growthIncrement;

    public CreateDatabaseMetadataRequest() {
    }

    public CreateDatabaseMetadataRequest(String databaseName, String filePath, FileType fileType, Integer pageSize,
            Long initialSize, Long maxSize, Boolean autoGrowthEnabled, Long growthIncrement) {
        this.databaseName = databaseName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.pageSize = pageSize;
        this.initialSize = initialSize;
        this.maxSize = maxSize;
        this.autoGrowthEnabled = autoGrowthEnabled;
        this.growthIncrement = growthIncrement;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Long initialSize) {
        this.initialSize = initialSize;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public Boolean getAutoGrowthEnabled() {
        return autoGrowthEnabled;
    }

    public void setAutoGrowthEnabled(Boolean autoGrowthEnabled) {
        this.autoGrowthEnabled = autoGrowthEnabled;
    }

    public Long getGrowthIncrement() {
        return growthIncrement;
    }

    public void setGrowthIncrement(Long growthIncrement) {
        this.growthIncrement = growthIncrement;
    }

}
