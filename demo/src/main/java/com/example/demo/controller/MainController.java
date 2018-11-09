package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.sun.xml.internal.rngom.dt.builtin.BuiltinDatatypeLibrary;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MainController {

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
    @PostMapping("/buildings")
    public @ResponseBody Building addBuilding(@RequestBody Building building) {
        buildingRepository.save(building);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        //String url = "http://localhost:3010/buildings";
        //String result = restTemplate.postForObject(url, building, String.class);
        return building;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/geocode")
    //public @ResponseBody Iterable<Building> searchBuidlingByLatLng(@RequestParam final String longitude, @RequestParam final String latitude, @RequestParam String radius)
    public @ResponseBody Iterable<Building> searchBuidlingByLatLng(@RequestParam final String longitude, @RequestParam final String latitude)
    {
        Double lat = Double.valueOf(latitude);
        Double lng = Double.valueOf(longitude);
        //Double distanceLimit = Double.valueOf(radius);
        Double distanceLimit = Double.valueOf("5");
        Double distanceRange;

        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();

        for (Building building : buildings) {
            double pow1 = Math.pow(69.1 * (building.getLatitude() - lat), 2);
            double pow2 = Math.pow(69.1 * (lng - building.getLongitude()) * Math.cos(building.getLatitude() / 57.3), 2);
            distanceRange = pow1 + pow2;

            if( distanceRange.compareTo(distanceLimit) < 0 ) {
                res.add(building);
            }
        }
        return res;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/location")
    public @ResponseBody Iterable<Building> searchBuidlingByLocation(@RequestParam final String city, @RequestParam final String state, @RequestParam final String zipcode)
    {
        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();

        for ( Building building : buildings) {
            if ( building.getZipcode().equalsIgnoreCase(zipcode) ) {
                res.add(building);
            }
        }

        if(res.size() == 0) {
            for(Building building : buildings) {
                if( building.getCity().equalsIgnoreCase(city)) {
                    res.add(building);
                }
            }
        }

        return res;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}")
    public @ResponseBody Building getbuidlingById (@RequestParam final String building_id)
    {
        Long buidlingId = Long.valueOf(building_id).longValue();
        return buildingRepository.findById(buidlingId).get();
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/floors")
    public @ResponseBody Floor addFloor(@RequestBody Floor floor)
    {
        floorRepository.save(floor);
        return floor;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/rooms")
    public @ResponseBody Room addRoom(@RequestBody Room room)
    {
        roomRepository.save(room);
        return room;
    }


    @CrossOrigin(origins = "*")
    @PostMapping(value="/clusters")
    public @ResponseBody Cluster addCluster(@ModelAttribute Cluster cluster) {
        clusterRepository.save(cluster);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        //String url = "http://localhost:3010/clusters";
        //String result = restTemplate.postForObject(url, cluster, String.class);

        return cluster;
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

