package dbms.replication;
import java.util.List;
import dbms.recovery.WALManager;

public class ReplicationManager {
    public String replicationMode;
    public List<ClusterNode> replicas;

    public void replicate(Object record) {}
    public void synchronize() {}
    public void electLeader() {}
}