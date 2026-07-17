package dbms.server;
import java.time.LocalDateTime;

public class DatabaseServer {
    public String serverId;
    public String version;
    public String status;
    public Object configuration;
    public LocalDateTime startTime;

    public void start() {}
    public void stop() {}
    public void restart() {}
}