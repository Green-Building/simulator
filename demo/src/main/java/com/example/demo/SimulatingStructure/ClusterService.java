package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Room;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClusterService {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;

    public String saveClusterToDB(Cluster cluster) {
        clusterRepository.save(cluster);
        return cluster.toString();
    }

    public String getClusterByClusterId (String cluster_id) {
        long clusterid = Long.valueOf(cluster_id).longValue();
        Cluster cluster = clusterRepository.findById(clusterid).get();
        return cluster.toString();
    }

    public String getClusterNestedByClusterId(String cluster_id, String nestedRequirement) {
        Long clusterid = Long.valueOf(cluster_id).longValue();
        Cluster cluster = clusterRepository.findById(clusterid).get();
        List<Room> rooms = roomRepository.findRoomByFloorId(cluster.getFloor_id());
        List<Node> nodes = nodeRepository.findNodeByClusterId(clusterid);
        List<Sensor> sensors = sensorRepository.findSensorByClusterId(clusterid);
        ClusterNested clusterNested;
        switch(nestedRequirement) {
                case "room, node, sensor":
                    clusterNested = new ClusterNested(cluster,rooms,nodes,sensors);
                    clusterNested.toString();
                case "node,sensor":
                    clusterNested = new ClusterNested(cluster,nodes,sensors);
                    return clusterNested.toString();
                case "room":
                    clusterNested = new ClusterNested(cluster,rooms);
                    return clusterNested.toString();
            }
        return clusterRepository.findById(clusterid).get().toString();
    }

    public Boolean deleteClusterByBuidlingIdFloorNumber(String buidling_id, String floor_number) {
        Boolean res = false;
        Long buidlingId = Long.valueOf(buidling_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Iterable<Cluster> clusters = clusterRepository.findAll();
        for (Cluster cluster: clusters) {
            if (cluster.getBuilding_id() == buidlingId && cluster.getFloor_number() == floorNumber) {
                clusterRepository.deleteById(cluster.getId());
                nodeRepository.deleteNodeByClusterId(cluster.getId());
                sensorRepository.deleteSensorByClusterId(cluster.getId());
                sensorDataRepository.deleteSensorDataByClusterId(cluster.getId());
                res = true;
            }
        }
        return res;
    }
}
