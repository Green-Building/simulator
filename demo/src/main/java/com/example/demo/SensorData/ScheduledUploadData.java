package com.example.demo.SensorData;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.SensorDataRepository;
import com.example.demo.repository.SensorRepository;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledUploadData {
    @Autowired
    SensorDataRepository sensorDataRepository;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    ClusterRepository clusterRepository;

    private static final SimpleDateFormat dateFormat =  new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportSensorDataToServer() {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        System.out.println("Time now:" + dateFormat.format(new Date()));

        /**
        Iterable<Cluster> clusters = clusterRepository.findAll();
        for (Cluster cluster: clusters) {
            if (cluster.getStatus().equals("active")) {
                Long clusterId = cluster.getId();
                Iterable<SensorData> sensorDatas = sensorDataRepository.findSensorDataByClusterId(clusterId);

                for (SensorData sensorData: sensorDatas) {
                    Long sensorId = sensorData.getSensor_id();
                    Sensor sensor = sensorRepository.findById(sensorId).get();
                    if (sensor.getStatus().equals("active")) {
                        //sensorData.setId(sensor.getId() + 1000000000);
                        sensorData.setDate(new Date());
                        sensorData.setSensordata(Math.random()*100);
                    }
                }
                String url = "http://localhost:3000/add/sensordata";
                String result = restTemplate.postForObject(url, sensorDatas, String.class);
            }
        }
        **/
    }
}
