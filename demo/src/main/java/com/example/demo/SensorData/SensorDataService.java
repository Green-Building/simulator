package com.example.demo.SensorData;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.SensorDataRepository;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @JsonPropertyOrder({
 *         "sensordata_id",
 *         "sensor_id",
 *         "node_id",
 *         "cluster_id",
 *         "type",
 *         "unit",
 *         "sensor_data",
 *         "date"
 * })
 */

@Service
public class SensorDataService {
    @Autowired
    SensorDataRepository sensorDataRepository;
    @Autowired
    ClusterRepository clusterRepository;

    public void updateSensorDateSchedule()
    {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "";
        String result = "";
        Iterable<Cluster> clusters = clusterRepository.findAll();
        for(Cluster cluster: clusters) {
            List<SensorDataWithDataManager> sensorDataResult = new LinkedList<>();
            Iterable<SensorData> sensorDataIterable = sensorDataRepository.findSensorDataByClusterId(cluster.getId());
            for(SensorData sensorData: sensorDataIterable) {
                sensorData.setDate(new Date());
                sensorData.setSensordata(Math.random()*100);
                SensorDataWithDataManager sensorDataWithDataManager = new SensorDataWithDataManager();
                sensorDataWithDataManager.setSensorId(sensorData.getSensor_id());
                sensorDataWithDataManager.setUnit(sensorData.getUnit());
                sensorDataWithDataManager.setData(sensorData.getSensordata());
                sensorDataWithDataManager.setType(sensorData.getType());
                sensorDataWithDataManager.setDate(sensorData.getDate());
                sensorDataRepository.save(sensorData);
                sensorDataResult.add(sensorDataWithDataManager);
                System.out.println(sensorDataWithDataManager.toString());
            }
            //To do.....
            //url = "http://localhost:8080/sensor_data";
            //restTemplate.postForObject(url, sensorDataResult, String.class);

            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSensorData(Sensor sensor) {
        SensorData sensorData = new SensorData();
        sensorData.setSensor_id(sensor.getId());
        sensorData.setNode_id(sensor.getNode_id());
        sensorData.setCluster_id(sensor.getCluster_id());
        sensorData.setType(sensor.getType());
        sensorData.setUnit("Default");
        sensorData.setSensordata(0.00);
        sensorData.setDate(new Date());
        sensorDataRepository.save(sensorData);
    }

    public SensorData findSensorDataBySensorId(long sensor_id) {
        return sensorDataRepository.findSensorDataBySensorId(sensor_id);
    }

}
