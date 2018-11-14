package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        FloorNested floorNested = new FloorNested(floor, cluster, roomList, nodeList, sensorList);
        return floorNested.toString();
    }

    public long getFloorIdByFloorNumber(Integer floor_number, long building_id) {
        Iterable<Floor> floors = floorRepository.findAll();
        for (Floor floor : floors) {
            if (floor.getFloor_number() == floor_number && floor.getBuilding_id() == building_id) {
                return floor.getId();
            }
        }
        return -20;
    }

    public Map<Integer, Integer> getAlltheRoomNodeSensorsMatchedResult(String building_id, String floor_number)
    {
        Map<Integer,Integer> res = new HashMap<>();
        long buildingId = Long.valueOf(building_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Iterable<Floor> floors = floorRepository.findAll();
        long floor_id = 0;
        for(Floor floor: floors) {
            if (floor.getBuilding_id() == buildingId && floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
            }
        }
        if(floor_id > 0) {
            Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
            for(Room room: rooms) {
                Node node = nodeRepository.findNodeByRoomId(room.getId());
                if (node != null) {
                    Iterable<Sensor> sensors = sensorRepository.findSensorByNodeId(node.getId());
                    res.put(room.getRoom_number(), ((List<Sensor>) sensors).size());
                } else {
                    res.put(room.getRoom_number(), -1);
                }
            }
        }
        return res;
    }

    public Map<Integer, Boolean> getAlltheRoomAndNodeMatchedResult(String building_id, String floor_number) {
        Map<Integer,Boolean> res = new HashMap<>();
        long buildingId = Long.valueOf(building_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Iterable<Floor> floors = floorRepository.findAll();

        long floor_id = 0;
        for(Floor floor: floors) {
            if (floor.getBuilding_id() == buildingId && floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
            }
        }

        if (floor_id > 0) {
            Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
            boolean isFound = false;
            for(Room room: rooms) {
                isFound = false;
                if (nodeRepository.findNodeByRoomId(room.getId()) != null) {
                    isFound = true;
                    res.put(room.getRoom_number(), true);
                }
                if (!isFound) {
                    res.put(room.getRoom_number(),false);
                }
            }
        }
        return res;
    }
}
