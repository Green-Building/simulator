package com.example.demo.controller;

import com.example.demo.SensorData.SensorDataService;
import com.example.demo.SimulatingStructure.*;
import com.example.demo.repository.*;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SimulatorController {
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

    @CrossOrigin(origins = "*")
    @PostMapping("/sensors")
    public @ResponseBody
    String addsensorSubmit(@RequestBody Sensor sensor) {
        sensor.setInstall_time(new Date());
        sensorService.saveSensorToDB(sensor);
        sensorDataService.createSensorData(sensor);
        return sensorService.createSensorNestedBySensorId(sensor.getId()).toString();
        /**
         ClientHttpRequestFactory requestFactory = new
         HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
         RestTemplate restTemplate = new RestTemplate(requestFactory);
         String url = "http://localhost:3010/sensors";
         String result = restTemplate.postForObject(url, sensor, String.class);
         **/
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/nodes")
    public @ResponseBody
    Node addnodeSubmit(@RequestBody Node node) {
        node.setInstall_time(new Date());
        nodeService.saveNodeToDB(node);
        /**
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/add/node";
        String result = restTemplate.postForObject(url, node, String.class);
         **/
        return node;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/clusters/add", produces = "application/json;charset=utf-8;")
    public @ResponseBody
    String addClusterSubmit(@RequestBody(required = true) Map<String, LinkedHashMap<String,String>> map)
    {
        return clusterService.saveClusterToDB(map);
        /**
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
         String url = "http://localhost:3010/clusters";
        String result = restTemplate.postForObject(url, cluster, String.class);
         **/
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/clusters/{cluster_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    String deleteClusterByClusterId (@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.deleteById(clusterId);
        nodeRepository.deleteNodeByClusterId(clusterId);
        sensorRepository.deleteSensorByClusterId(clusterId);
        sensorDataRepository.deleteSensorDataByClusterId(clusterId);
        /**
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3010/clusters/{cluster_id}";
        String result = restTemplate.postForObject(url, cluster_id, String.class);
         **/
        return "success...";
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/nodes/{node_id}")
    public @ResponseBody
    String deleteNodeByNodeId(@PathVariable final String node_id) {
        Long nodeId = Long.valueOf(node_id).longValue();
        nodeRepository.deleteById(nodeId);
        sensorRepository.deleteSensorByNodeId(nodeId);
        sensorDataRepository.deleteSensorDataByNodeId(nodeId);
        /**
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/node/{node_id}";
        String result = restTemplate.postForObject(url, node_id, String.class);
         **/
        return "success...";
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/sensors/{sensor_id}")
    public @ResponseBody
    String deleteSensorBySensorId(@PathVariable final String sensor_id) {
        Long sensorId = Long.valueOf(sensor_id).longValue();
        sensorRepository.deleteById(sensorId);
        sensorDataRepository.deleteSensorDataBySensorId(sensorId);
        /**
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/delete/cluster/{sensor_id}";
        String result = restTemplate.postForObject(url, sensor_id, String.class);
         **/
        return "success...";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/clusters/{cluster_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    String getClusterNestedByClusterID(
            @PathVariable("cluster_id") final String cluster_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return clusterService.getClusterByClusterId(cluster_id);
        } else {
            return clusterService.getClusterNestedByClusterId(cluster_id, nestedRequirement);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/{node_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    String getNodeNestedByNodeID(
            @PathVariable("node_id") final String node_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return nodeService.getNodeByNodeId(node_id);
        } else {
            return nodeService.getNodeNestedByNodeId(node_id, nestedRequirement);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/sensors/{sensor_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    String getSensorNestedBySensorID(
            @PathVariable("sensor_id") final String sensor_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return sensorService.getSensorBySensorId(sensor_id);
        } else {
            return sensorService.getSensorNestedBySensorId(sensor_id, nestedRequirement);
        }
    }
}

