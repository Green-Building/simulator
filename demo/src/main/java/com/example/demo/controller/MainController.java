package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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


    /**
     *
     * @param model
     * @return
     */

    @CrossOrigin(origins = "*")
    @GetMapping("/add/building")
    public String addBuildingForm(Model model) {
        model.addAttribute("building", new Building());
        return "addBuilding";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/building")
    public String addBuildingSubmit(@ModelAttribute Building building) {
        buildingRepository.save(building);
        return "resultBuilding";
    }

    /**
     * Get add cluster html.
     * @param model new Cluster();
     * @return addCluster.html;
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/add/cluster")
    public String addClusterForm (Model model) {
        model.addAttribute("cluster", new Cluster());
        model.addAttribute("buildings", buildingRepository.findAll());
        return "addCluster";
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/add/cluster")
    public String addclusterSubmit(@ModelAttribute Cluster cluster) {
        cluster.setStatus("active");
        clusterRepository.save(cluster);
        return "resultCluster";
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
        return "resultSensor";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add/sensordata")
    public String addsensordataSubmit(@ModelAttribute SensorData sensorData) {
        System.out.println("receive data from client...");
        System.out.println(sensorData.toString());
        sensorDataRepository.save(sensorData);
        return "resultSensorData";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/delete/cluster/{cluster_id}")
    public @ResponseBody Iterable<Cluster> deleteClusterByClusterId (@PathVariable final String cluster_id){
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.deleteById(clusterId);
        nodeRepository.deleteNodeByClusterId(clusterId);
        sensorRepository.deleteSensorByClusterId(clusterId);
        sensorDataRepository.deleteSensorDataByClusterId(clusterId);
        return clusterRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "delete/node/{node_id}")
    public @ResponseBody Iterable<Node> deleteNodeByNodeId(@PathVariable final String node_id) {
        Long nodeId = Long.valueOf(node_id).longValue();
        nodeRepository.deleteById(nodeId);
        sensorRepository.deleteSensorByNodeId(nodeId);
        sensorDataRepository.deleteSensorDataByNodeId(nodeId);
        return nodeRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "delete/sensor/{sensor_id}")
    public @ResponseBody Iterable<Sensor> deleteSensorBySensorId(@PathVariable final String sensor_id) {
        Long sensorId = Long.valueOf(sensor_id).longValue();
        sensorRepository.deleteById(sensorId);
        sensorDataRepository.deleteSensorDataBySensorId(sensorId);
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
        return clusterRepository.findById(clusterId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "active/cluster/{cluster_id}")
    public @ResponseBody Cluster activeClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.updateClusterStatusById("active", clusterId);
        return clusterRepository.findById(clusterId).get();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "update/sensordata/{cluster_id}")
    public @ResponseBody Iterable<SensorData> updateSensorDataByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        List<SensorData> sensorDataList = sensorDataRepository.findSensorDataByClusterId(clusterId);

        for (int i = 0; i < sensorDataList.size(); i++) {
            sensorDataList.get(i).updateSensorDataRandom();
            Double randomValue = sensorDataList.get(i).getSensorData();
            Long sensorDataId = sensorDataList.get(i).getId();
            sensorDataRepository.updateSensorDataValueBySensorDataId(randomValue, sensorDataId);
        }

        return sensorDataRepository.findSensorDataByClusterId(clusterId);
    }

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