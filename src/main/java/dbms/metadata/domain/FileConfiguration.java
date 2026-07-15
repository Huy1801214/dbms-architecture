package dbms.metadata.domain;

public class FileConfiguration {
    private final Long initialSize;
    private final Long maxSize;
    private final Boolean autoGrowthEnabled;
    private final Long growthIncrement;

    public FileConfiguration(Long initialSize, Long maxSize, Boolean autoGrowthEnabled, Long growthIncrement) {
        this.initialSize = initialSize;
        this.maxSize = maxSize;
        this.autoGrowthEnabled = autoGrowthEnabled;
        this.growthIncrement = growthIncrement;
    }

    public Long getInitialSize() {
        return initialSize;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public Boolean isAutoGrowthEnable() {
        return autoGrowthEnabled;
    }

    public Long getGrowthIncrement() {
        return growthIncrement;
    }

}