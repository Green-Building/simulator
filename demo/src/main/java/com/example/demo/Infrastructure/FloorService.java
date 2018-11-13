package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService {
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private RoomRepository roomRepository;

    public String saveFloorToDB(Floor floor) {
        floorRepository.save(floor);
        return floor.toString();
    }

    public String getFloorByFloorId(String floor_id) {
        long floorid = Long.valueOf(floor_id).longValue();
        return floorRepository.findById(floorid).get().toString();
    }

    public String getFloorNestedByFloorId(String floor_id, String requirement) {
        long floorid = Long.valueOf(floor_id).longValue();
        Floor floor = floorRepository.findById(floorid).get();
        Cluster cluster = clusterRepository.findClusterByFloorId(floor.getId());
        List<Room> roomList = roomRepository.findRoomByFloorId(floorid);
        List<Node> nodeList = nodeRepository.findNodeByClusterId(cluster.getId());
        List<Sensor> sensorList = sensorRepository.findSensorByClusterId(cluster.getId());
        FloorNested floorNested = new FloorNested(floor,cluster,roomList,nodeList,sensorList);
        return floorNested.toString();
    }
}
