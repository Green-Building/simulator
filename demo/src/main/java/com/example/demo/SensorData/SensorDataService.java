package com.example.demo.SensorData;

import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class SensorDataService {
    @Autowired
    SensorDataRepository sensorDataRepository;

    public void createSensorData(Sensor sensor) {
        SensorData sensorData = new SensorData();
        sensorData.setSensor_id(sensor.getId());
        sensorData.setNode_id(sensor.getNode_id());
        sensorData.setCluster_id(sensor.getCluster_id());
        sensorData.setUnit("Default");
        sensorData.setSensordata(0.00);
        sensorData.setDate(new Date());
        sensorDataRepository.save(sensorData);
    }

    public SensorData findSensorDataBySensorId(long sensor_id) {
        return sensorDataRepository.findSensorDataBySensorId(sensor_id);
    }

}
