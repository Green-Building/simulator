package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Floor;
import com.example.demo.Infrastructure.Room;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;

    public long deleteNode(String building_id, String floor_number, String room_number)
    {
        long buildingId = Long.valueOf(building_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Integer roomNumber = Integer.valueOf(room_number);
        Iterable<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);
        long floor_id = 0;
        for(Floor floor: floors) {
            if(floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
            }
        }
        long room_id = 0;
        Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
        for(Room room: rooms) {
            if(room.getRoom_number() == roomNumber) {
                room_id = room.getId();
                break;
            }
        }

        long node_id = nodeRepository.findNodeByRoomId(room_id).getId();
        nodeRepository.deleteById(node_id);
        sensorRepository.deleteSensorByNodeId(node_id);
        sensorDataRepository.deleteSensorDataByNodeId(node_id);
        return node_id;
    }

    public void saveNodeToDB (Node node, String building_id, String floor_number, String room_number)
    {
        long buildingId = Long.valueOf(building_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Integer roomNumber = Integer.valueOf(room_number);
        Iterable<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);
        long floor_id = 0;
        for(Floor floor: floors) {
            if(floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
            }
        }
        long cluster_id = clusterRepository.findClusterByFloorId(floor_id).getId();
        node.setCluster_id(cluster_id);

        long room_id = 0;
        Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
        for(Room room: rooms) {
            if(room.getRoom_number() == roomNumber) {
                room_id = room.getId();
                break;
            }
        }

        if (room_id > 0) {
            node.setRoom_id(room_id);
        }

        node.setInstall_time(new Date());
        nodeRepository.save(node);
    }

    public void saveNodeToDB(Node node) {
        nodeRepository.save(node);
    }

    public String getNodeByNodeId (String node_id) {
        long nodeid = Long.valueOf(node_id).longValue();
        Node node = nodeRepository.findById(nodeid).get();
        return node.toString();
    }

    public String getNodeNestedByNodeId(String node_id, String nestedRequirement) {
        Long nodeid = Long.valueOf(node_id).longValue();
        Node node = nodeRepository.findById(nodeid).get();
        List<Sensor> sensors = sensorRepository.findSensorByNodeId(nodeid);
        NodeNested nodeNested;
        switch(nestedRequirement) {
            case "sensor":
                nodeNested = new NodeNested(node,sensors);
                return nodeNested.toString();
            default:
                return nodeRepository.findById(nodeid).toString();
        }
    }
}
