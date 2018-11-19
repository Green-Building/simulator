package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Floor;
import com.example.demo.Infrastructure.Room;
import com.example.demo.SensorData.SensorData;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    SensorDataRepository sensorDataRepository;
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    ClusterRepository clusterRepository;
    @Autowired
    FloorRepository floorRepository;
    @Autowired
    RoomRepository roomRepository;

    public Sensor updateSensorBySensorId(String sensorId, Sensor sensor)
    {
        long sensor_id = Long.valueOf(sensorId).longValue();
        Sensor sensorFromDB = sensorRepository.findById(sensor_id).get();

        if(!sensor.getName().equals(sensorFromDB.getName())) {
            sensorFromDB.setName(sensor.getName());
        }

        if(!sensor.getStatus().equals(sensorFromDB.getStatus())) {
            sensorFromDB.setStatus(sensor.getStatus());
        }

        sensorRepository.save(sensorFromDB);
        return sensorFromDB;
    }

    public void deleteSensorBySensorId(long sensor_id) {
        Sensor sensor = sensorRepository.findById(sensor_id).get();
        if (sensor == null) {
            return;
        } else {
            sensorRepository.deleteById(sensor_id);
            sensorDataRepository.deleteSensorDataBySensorId(sensor_id);

        }
    }

    public long deleteASensorFromARoom(String building_id, String floor_number, String room_number) {
        long buildingId = Long.valueOf(building_id).longValue();
        Integer floorNumber = Integer.valueOf(floor_number);
        Integer roomNumber = Integer.valueOf(room_number);
        Iterable<Floor> floors = floorRepository.findFloorByBuildingId(buildingId);

        long floor_id = 0;
        for(Floor floor: floors) {
            if(floor.getFloor_number() == floorNumber) {
                floor_id = floor.getId();
                break;
            }
        }

        if (floor_id > 0) {
            Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
            long room_id = 0;
            for(Room room : rooms) {
                if (room.getRoom_number() == roomNumber) {
                    room_id = room.getId();
                    break;
                }
            }

            if (room_id > 0) {
                Node node = nodeRepository.findNodeByRoomId(room_id);
                Iterable<Sensor> sensors = sensorRepository.findSensorByNodeId(node.getId());
                if (((List<Sensor>) sensors).size() > 0) {
                    long sensor_id = ((List<Sensor>) sensors).get(((List<Sensor>) sensors).size()-1).getId();
                    sensorRepository.deleteById(sensor_id);
                    return sensor_id;
                }
            }
        }
        return 0;
    }

    public Sensor saveSensorToDB (Sensor sensor, String building_id, String floor_number, String room_number)
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
        sensor.setCluster_id(cluster_id);

        long room_id = 0;
        Iterable<Room> rooms = roomRepository.findRoomByFloorId(floor_id);
        for(Room room: rooms) {
            if(room.getRoom_number() == roomNumber) {
                room_id = room.getId();
            }
        }
        long node_id = nodeRepository.findNodeByRoomId(room_id).getId();
        sensor.setNode_id(node_id);

        sensor.setInstall_time(new Date());
        sensorRepository.save(sensor);
        return sensor;
    }

    public Sensor saveSensorToDB (Sensor sensor) {
        sensor.setInstall_time(new Date());
        sensorRepository.save(sensor);
        return sensor;
    }

    public String getSensorBySensorId (String sensor_id) {
        long sensorid = Long.valueOf(sensor_id).longValue();
        Sensor sensor = sensorRepository.findById(sensorid).get();
        return sensor.toString();
    }

    public String getSensorNestedBySensorId(String sensor_id, String nestedRequirement) {
        Long sensorid = Long.valueOf(sensor_id).longValue();
        Sensor sensor = sensorRepository.findById(sensorid).get();
        SensorData sensorData = sensorDataRepository.findSensorDataBySensorId(sensorid);
        SensorNested sensorNested = new SensorNested(sensor,sensorData);
        return sensorNested.toString();
    }

    public SensorNested createSensorNestedBySensorId (long sensor_id) {
        Sensor sensor = sensorRepository.findById(sensor_id).get();
        SensorData sensorData = sensorDataRepository.findSensorDataBySensorId(sensor_id);
        SensorNested sensorNested = new SensorNested(sensor,sensorData);
        return  sensorNested;
    }

}
