package com.example.demo.controller;

import com.example.demo.Infrastructure.BuildingService;
import com.example.demo.Infrastructure.FloorService;
import com.example.demo.SensorData.SensorDataService;
import com.example.demo.SimulatingStructure.*;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.SensorDataRepository;
import com.example.demo.repository.SensorRepository;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class InfrastrutureInteractController {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;

    @CrossOrigin(origins = "*")
    @PutMapping(value="/clusters/{cluster_id}")
    public @ResponseBody Cluster updateClusterFromBackEnd(
            @PathVariable String cluster_id,
            @RequestBody Cluster cluster)
    {
        return clusterService.updateClusterByClusterId(cluster_id,cluster);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/nodes/{node_id}")
    public @ResponseBody Node updateNodeByNodeId(
            @PathVariable String node_id,
            @RequestBody Node node)
    {
       return nodeService.updateNodeByNodeId(node_id, node);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/sensors/{sensor_id}")
    public @ResponseBody Sensor updateSensorBySensorId(
            @PathVariable String sensor_id,
            @RequestBody Sensor sensor)
    {
        return sensorService.updateSensorBySensorId(sensor_id,sensor);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/clusters")
    public @ResponseBody String addClusterFromBackEnd(@RequestBody Cluster cluster)
    {
        Cluster newCluster = clusterService.saveClusterToDB(cluster);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/clusters";
        String result = restTemplate.postForObject(url, newCluster, String.class);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/nodes")
    public @ResponseBody String addNodeFromBackEnd(@RequestBody Node node)
    {
        Node newNode = nodeService.saveNodeToDB(node);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/nodes";
        String result = restTemplate.postForObject(url, newNode, String.class);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/sensors")
    public @ResponseBody String addSensorFromBackEnd(@RequestBody Sensor sensor)
    {
        Sensor newSensor = sensorService.saveSensorToDB(sensor);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/sensors";
        String result = restTemplate.postForObject(url, newSensor, String.class);
        sensorDataService.createSensorData(newSensor);
        return result;
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/clusters/{cluster_id}")
    public @ResponseBody void deleteClusterByClusterId(@PathVariable("cluster_id") final long cluster_id)
    {
        clusterService.deleteClusterByClusterId(cluster_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/nodes/{node_id}")
    public @ResponseBody void deleteNodeByNodeId(@PathVariable("node_id") final long node_id)
    {
        nodeService.deleteNodeByNodeId(node_id);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/sensors/{sensor_id}")
    public @ResponseBody void deleteSensorBySensorId(@PathVariable("sensor_id") final long sensor_id)
    {
        sensorService.deleteSensorBySensorId(sensor_id);
    }
}

