package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;

    public String saveNodeToDB(Room room) {
        roomRepository.save(room);
        return room.toString();
    }

    public String getRoomByRoomId(String room_id) {
        long roomid = Long.valueOf(room_id).longValue();
        return roomRepository.findById(roomid).get().toString();
    }

    public String getRoomNestedByRoomId(String room_id, String requireMent) {
        long roomid = Long.valueOf(room_id).longValue();
        Room room = roomRepository.findById(roomid).get();
        Node node = nodeRepository.findNodeByRoomId(room.getId());
        List<Sensor> sensorSet = sensorRepository.findSensorByNodeId(node.getId());
        RoomNested roomNested = new RoomNested(room, node, sensorSet);
        return roomNested.toString();
    }
}
