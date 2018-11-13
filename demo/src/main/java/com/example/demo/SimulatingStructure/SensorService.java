package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Room;
import com.example.demo.SensorData.SensorData;
import com.example.demo.repository.SensorDataRepository;
import com.example.demo.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    SensorDataRepository sensorDataRepository;

    public void saveSensorToDB (Sensor sensor) {
        sensorRepository.save(sensor);
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
