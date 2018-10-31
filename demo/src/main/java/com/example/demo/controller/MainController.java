package com.example.demo.controller;

import com.example.demo.model.Cluster;
import com.example.demo.model.Node;
import com.example.demo.model.Sensor;
import com.example.demo.model.SensorData;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.SensorDataRepository;
import com.example.demo.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Get add cluster html.
     * @param model new Cluster();
     * @return addCluster.html;
     */
    @GetMapping("/add/cluster")
    public String addClusterForm (Model model) {
        model.addAttribute("cluster", new Cluster());
        return "addCluster";
    }

    @PostMapping("/add/cluster")
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
    @GetMapping("/add/node")
    public String addNodeForm (Model model) {
        model.addAttribute("node", new Node());
        model.addAttribute("clusters", clusterRepository.findAll());
        return "addNode";
    }

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
    @GetMapping("/add/sensor")
    public String addSensorForm (Model model) {
        model.addAttribute("sensor", new Sensor());
        model.addAttribute("clusters",clusterRepository.findAll());
        model.addAttribute("nodes", nodeRepository.findAll());
        return "addSensor";
    }

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
        sensorDataRepository.save(sensorData);
        return "resultSensor";
    }

    @GetMapping(value = "/delete/cluster/{cluster_id}")
    public @ResponseBody Iterable<Cluster> deleteClusterByClusterId (@PathVariable final String cluster_id){
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.deleteById(clusterId);
        nodeRepository.deleteNodeByClusterId(clusterId);
        sensorRepository.deleteSensorByClusterId(clusterId);
        sensorDataRepository.deleteSensorDataByClusterId(clusterId);
        return clusterRepository.findAll();
    }

    @GetMapping(value = "delete/node/{node_id}")
    public @ResponseBody Iterable<Node> deleteNodeByNodeId(@PathVariable final String node_id) {
        Long nodeId = Long.valueOf(node_id).longValue();
        nodeRepository.deleteById(nodeId);
        sensorRepository.deleteSensorByNodeId(nodeId);
        sensorDataRepository.deleteSensorDataByNodeId(nodeId);
        return nodeRepository.findAll();
    }

    @GetMapping(value = "delete/sensor/{sensor_id}")
    public @ResponseBody Iterable<Sensor> deleteSensorBySensorId(@PathVariable final String sensor_id) {
        Long sensorId = Long.valueOf(sensor_id).longValue();
        sensorRepository.deleteById(sensorId);
        sensorDataRepository.deleteSensorDataBySensorId(sensorId);
        return sensorRepository.findAll();
    }

    @GetMapping(value = "deactive/cluster/{cluster_id}")
    public @ResponseBody Cluster deactiveClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.updateClusterStatusById("deactive", clusterId);
        nodeRepository.updateNodeStatusByClusterId("deactive",clusterId);
        sensorRepository.updateSensorStatusByClusterId("deactive", clusterId);
        sensorDataRepository.updateSensorDataValueByClusterId(99999.99, clusterId);
        return clusterRepository.findById(clusterId).get();
    }

    @GetMapping(value = "active/cluster/{cluster_id}")
    public @ResponseBody Cluster activeClusterByClusterId(@PathVariable final String cluster_id) {
        Long clusterId = Long.valueOf(cluster_id).longValue();
        clusterRepository.updateClusterStatusById("active", clusterId);
        nodeRepository.updateNodeStatusByClusterId("active",clusterId);
        sensorRepository.updateSensorStatusByClusterId("active", clusterId);
        sensorDataRepository.updateSensorDataValueByClusterId(0.00, clusterId);
        return clusterRepository.findById(clusterId).get();
    }

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

    @GetMapping(path = "/clusters")
    public @ResponseBody Iterable<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }

    @GetMapping(path = "/nodes")
    public @ResponseBody Iterable<Node> getAllNodes() {
        return nodeRepository.findAll();
    }

    @GetMapping(path = "/sensors")
    public @ResponseBody Iterable<Sensor> getAllSensors() {
        return  sensorRepository.findAll();
    }

    @GetMapping(path = "/sensors-data")
    public @ResponseBody Iterable<SensorData> getAllSensorsData() {
        return  sensorDataRepository.findAll();
    }

}
