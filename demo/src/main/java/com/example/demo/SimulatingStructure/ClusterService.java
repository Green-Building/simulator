package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Floor;
import com.example.demo.Infrastructure.Room;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Cluster saveClusterToDB(Cluster cluster, String building_id, String floor_number)
    {
        long buildingId = Long.valueOf(building_id).longValue();
        cluster.setBuilding_id(buildingId);

        Integer floorNumber = Integer.valueOf(floor_number);
        Iterable<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);
        long floor_id = 0;
        for(Floor floor: floors) {
            if(floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
                break;
            }
        }
        cluster.setFloor_id(floor_id);
        cluster.setInstall_time(new Date());
        cluster.setStatus("active");
        clusterRepository.save(cluster);
        return cluster;
    }

    public Cluster saveClusterToDB(Cluster cluster) {
        clusterRepository.save(cluster);
        return cluster;
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

    public long deleteClusterByBuidlingIdFloorNumber(String buidling_id, String floor_number) {
        Boolean res = false;
        Long buildingId = Long.valueOf(buidling_id).longValue();

        Integer floorNumber = Integer.valueOf(floor_number);
        Iterable<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);
        long floor_id = 0;
        for(Floor floor: floors) {
            if(floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
                break;
            }
        }

        Iterable<Cluster> clusters = clusterRepository.findAll();
        for (Cluster cluster: clusters) {
            if (cluster.getBuilding_id() == buildingId && cluster.getFloor_id() == floor_id) {
                clusterRepository.deleteById(cluster.getId());
                nodeRepository.deleteNodeByClusterId(cluster.getId());
                sensorRepository.deleteSensorByClusterId(cluster.getId());
                sensorDataRepository.deleteSensorDataByClusterId(cluster.getId());
                res = true;
                return cluster.getId();
            }
        }
        return 0;
    }
}
