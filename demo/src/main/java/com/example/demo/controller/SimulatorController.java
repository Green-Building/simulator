package com.example.demo.controller;

import com.example.demo.Infrastructure.Building;
import com.example.demo.Infrastructure.Floor;
import com.example.demo.SensorData.SensorData;
import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


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
    private BuildingRepository buildingRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private RoomRepository roomRepository;

    @CrossOrigin(origins = "*")
    @PostMapping(value="/clusters")
    public @ResponseBody
    Cluster addCluster(@RequestBody String json) {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        long buildingid = 0;
        long floorid = 0;
        String name = "";
        String type = "";
        String series_number = "";
        Date install_time = new Date();
        String status = null;

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            buildingid = jsonObject.getLong("building_id");
            floorid = jsonObject.getLong("floor_id");
            name = jsonObject.getString("name");
            type = jsonObject.getString("type");
            series_number = jsonObject.getString("series_number");
            status = jsonObject.getString("status");
            Building building = buildingRepository.findById(buildingid).get();
            Cluster cluster = new Cluster(building, floorid, name, type, series_number, install_time, status);
            building.getClusters().add(cluster);
            buildingRepository.save(building);
            Set<Cluster> clusters = buildingRepository.findById(buildingid).get().getClusters();
            long maxID = 0;
            for (Cluster cluster0: clusters) {
                long currentId = cluster0.getCluster_id();
                if( currentId > maxID ) {
                    maxID = currentId;
                }
            }
            return clusterRepository.findById(maxID).get();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
        //String url = "http://localhost:3010/clusters";
        //String result = restTemplate.postForObject(url, cluster, String.class);
    }

    /**
     * Get add node html.
     * @param model new Node();
     * @return addNode.html;
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/add/node")
    public String addNodeForm (Model model) {
        model.addAttribute("node", new Node());
        model.addAttribute("clusters", clusterRepository.findAll());
        return "addNode";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/node")
    public String addnodeSubmit(@ModelAttribute Node node) {
        node.setStatus("active");
        nodeRepository.save(node);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/add/node";
        String result = restTemplate.postForObject(url, node, String.class);

        return "resultNode";
    }

    /**
     * Get add sensor html.
     * @param model new Sensor();
     * @return addSensor.html;
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/add/sensor")
    public String addSensorForm (Model model) {
        model.addAttribute("sensor", new Sensor());
        model.addAttribute("clusters",clusterRepository.findAll());
        model.addAttribute("nodes", nodeRepository.findAll());
        return "addSensor";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/sensor")
    public String addsensorSubmit(@ModelAttribute Sensor sensor) {
        Long nodeId = sensor.getNodeId();
        /**
         * Do not forget .get();
         */
        Node node = nodeRepository.findById(nodeId).get();
        sensor.setClusterId(node.getClusterId());
        sensor.setStatus("active");
        sensorRepository.save(sensor);

        SensorData sensorData = new SensorData();
        sensorData.setNodeId(sensor.getNodeId());
        sensorData.setSensorId(sensor.getId());
        sensorData.setSensorType(sensor.getType());
        sensorData.setSensorData(0.00);
        sensorData.setClusterId(sensor.getClusterId());
        sensorData.setDate(new Date());
        sensorData.setUnit("Default");
        sensorDataRepository.save(sensorData);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/add/sensor";
        String result = restTemplate.postForObject(url, sensor, String.class);

        url = "http://localhost:3010/add/sensordata";
        result = restTemplate.postForObject(url, sensorData, String.class);

        return "resultSensor";
    }


    @CrossOrigin(origins = "*")
    @GetMapping(value = "/delete/cluster/{cluster_id}")
    public @ResponseBody Iterable<Cluster> deleteClusterByClusterId (@PathVariable final String cluster_id){
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.deleteById(clusterId);
        nodeRepository.deleteNodeByClusterId(clusterId);
        sensorRepository.deleteSensorByClusterId(clusterId);
        sensorDataRepository.deleteSensorDataByClusterId(clusterId);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/delete/cluster/{cluster_id}";
        String result = restTemplate.postForObject(url, cluster_id, String.class);

        return clusterRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "delete/node/{node_id}")
    public @ResponseBody Iterable<Node> deleteNodeByNodeId(@PathVariable final String node_id) {
        Long nodeId = Long.valueOf(node_id).longValue();
        nodeRepository.deleteById(nodeId);
        sensorRepository.deleteSensorByNodeId(nodeId);
        sensorDataRepository.deleteSensorDataByNodeId(nodeId);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/delete/node/{node_id}";
        String result = restTemplate.postForObject(url, node_id, String.class);


        return nodeRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "delete/sensor/{sensor_id}")
    public @ResponseBody Iterable<Sensor> deleteSensorBySensorId(@PathVariable final String sensor_id) {
        Long sensorId = Long.valueOf(sensor_id).longValue();
        sensorRepository.deleteById(sensorId);
        sensorDataRepository.deleteSensorDataBySensorId(sensorId);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/delete/cluster/{sensor_id}";
        String result = restTemplate.postForObject(url, sensor_id, String.class);

        return sensorRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "deactive/cluster/{cluster_id}")
    public @ResponseBody Cluster deactiveClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.updateClusterStatusById("deactive", clusterId);
        nodeRepository.updateNodeStatusByClusterId("deactive",clusterId);
        sensorRepository.updateSensorStatusByClusterId("deactive", clusterId);
        sensorDataRepository.updateSensorDataValueByClusterId(99999.99, clusterId);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3010/deactive/cluster/{cluster_id}";
        String result = restTemplate.postForObject(url, cluster_id, String.class);


        return clusterRepository.findById(clusterId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "active/cluster/{cluster_id}")
    public @ResponseBody Cluster activeClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.updateClusterStatusById("active", clusterId);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3010/active/cluster/{cluster_id}";
        String result = restTemplate.postForObject(url, cluster_id, String.class);

        return clusterRepository.findById(clusterId).get();
    }


    /**
     * ************************************ the test unit ***********************************************
     * @return the all the clusters.
     */
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/cluster/all")
    public @ResponseBody Iterable<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/cluster/{cluster_id}")
    public @ResponseBody Cluster getClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        return clusterRepository.findById(clusterId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "get/node/all")
    public @ResponseBody Iterable<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/node/{node_id}")
    public @ResponseBody Node getNodeByNodeId(@PathVariable final String node_id) {
        Long nodeId = Long.valueOf(node_id).longValue();
        return nodeRepository.findById(nodeId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/sensor/all")
    public @ResponseBody Iterable<Sensor> getAllSensors() {
        return  sensorRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/sensor/{sensor_id}")
    public @ResponseBody Sensor getSensorBySensorId(@PathVariable final String sensor_id) {
        Long sensorId = Long.valueOf(sensor_id).longValue();
        return sensorRepository.findById(sensorId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/sensordata/all")
    public @ResponseBody Iterable<SensorData> getAllSensorsData() {
        return  sensorDataRepository.findAll();
    }

    @CrossOrigin(origins =  "*")
    @GetMapping(path ="/get/sensordata/{cluster_id}")
    public @ResponseBody Iterable<SensorData> getSensorDataByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        return sensorDataRepository.findSensorDataByClusterId(clusterId);
    }
}

