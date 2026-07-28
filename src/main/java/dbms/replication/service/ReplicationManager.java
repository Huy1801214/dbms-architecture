package dbms.replication.service;

import dbms.recovery.service.WALManager;
import dbms.replication.entity.ClusterNode;
import java.util.List;
import dbms.recovery.service.WALManager;

public class ReplicationManager {
    public String replicationMode;
    public List<ClusterNode> replicas;

    public void replicate(Object record) {}
    public void synchronize() {}
    public void electLeader() {}
}
