package com.example.demo.SensorData;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Sensor;
import com.example.demo.SimulatingStructure.SensorService;
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
    SensorDataService sensorDataService;

    private static final SimpleDateFormat dateFormat =  new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 50000)
    public void reportSensorDataToServer() {
        System.out.println("Time now:" + dateFormat.format(new Date()));
        sensorDataService.updateSensorDateSchedule();
    }
}
