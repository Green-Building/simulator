package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Floor;
import com.example.demo.Infrastructure.Room;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public String saveClusterToDB(Map<String, LinkedHashMap<String,String>> map) {
        Map<String, String> res = map.get("data");
        Cluster cluster = new Cluster();
        long building_id = Long.valueOf(res.get("building_id")).longValue();
        cluster.setBuilding_id(building_id);
        int floor_number = Integer.valueOf(res.get("floor_number"));
        long floor_id = 0;
        Iterable<Floor> floors = floorRepository.findAll();
        for(Floor floor : floors) {
            if (floor.getBuilding_id() == building_id && floor.getFloor_number() == floor_number) {
                floor_id = floor.getId();
                break;
            }
        }
        cluster.setFloor_id(floor_id);
        cluster.setName(res.get("name"));
        cluster.setStatus(res.get("status"));
        cluster.setInstall_time(new Date());
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
}
