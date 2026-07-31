package dbms.replication.service;

import dbms.recovery.WALManager;
import dbms.replication.entity.ClusterNode;
import java.util.List;

public class ReplicationManager {
    public String replicationMode;
    public List<ClusterNode> replicas;

    public void replicate(Object record) {}
    public void synchronize() {}
    public void electLeader() {}
}
