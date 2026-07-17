package dbms.recovery;
import java.util.List;

public class WALManager {
    public long currentLSN;
    public List<Object> logFiles;

    public void append(LogRecord record) {}
    public void flush() {}
    public void replay() {}
}