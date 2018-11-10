package com.example.demo.SensorData;

import org.springframework.stereotype.Component;

@Component
public class ScheduledUploadData {
    /**
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
        Iterable<Cluster> clusters = clusterRepository.findAll();
        for (Cluster cluster: clusters) {
            if (cluster.getStatus().equals("active")) {
                Long clusterId = cluster.getCluster_id();
                Iterable<SensorData> sensorDatas = sensorDataRepository.findSensorDataByClusterId(clusterId);

                for (SensorData sensorData: sensorDatas) {
                    Long sensorId = sensorData.getSensorId();
                    Sensor sensor = sensorRepository.findById(sensorId).get();
                    if (sensor.getStatus().equals("active")) {
                        sensorData.setId(sensor.getId() + 1000000000);
                        sensorData.setDate(new Date());
                        sensorData.setSensorData(Math.random()*100);
                    }
                }
                String url = "http://localhost:3000/add/sensordata";
                String result = restTemplate.postForObject(url, sensorDatas, String.class);
            }
        }
    }
    **/
}
