package dbms.metadata.domain;

public class DatabaseFile {
    private String filePath;
    private FileType fileType;
    private FileConfiguration configuration;
    private FileStatistics statistic;
    private FileHeader header;
    private FileState state;
    private FileIdentifier identifier;

    public DatabaseFile(String filePath, FileType fileType, FileConfiguration configuration, FileStatistics statistic,
            FileHeader header, FileState state, FileIdentifier identifier) {
        this.filePath = filePath;
        this.fileType = fileType;
        this.configuration = configuration;
        this.statistic = statistic;
        this.header = header;
        this.state = state;
        this.identifier = identifier;
    }

    public String getFilePath() {
        return filePath;
    }

    public FileType getFileType() {
        return fileType;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public FileStatistics getStatistic() {
        return statistic;
    }

    public FileHeader getHeader() {
        return header;
    }

    public FileState getState() {
        return state;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setStatistic(FileStatistics statistic) {
        this.statistic = statistic;
    }

    public void setHeader(FileHeader header) {
        this.header = header;
    }

    public void setState(FileState state) {
        this.state = state;
    }

    public void setIdentifier(FileIdentifier identifier) {
        this.identifier = identifier;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
