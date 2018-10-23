package simulator;

import net.minidev.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Simulator {

    private List<Integer> clustersName = new LinkedList<>();
    private List<Cluster> clusters = new LinkedList<>();

    public Simulator() {

    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Node> getNodes(JSONObject clusterInfo) {
        Cluster cluster = parseToCluster(clusterInfo);
        for(int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).getClusterID() == cluster.getClusterID()) {
                return clusters.get(i).getNodeList();
            }
        }
        return null;
    }

    public void addCluster(JSONObject clusterInfo) {
        clusters.add(parseToCluster(clusterInfo));
        clustersName.add(parseToCluster(clusterInfo).getClusterID());
    }

    public void addNode(JSONObject nodeInfo) {
        Node node = parseToNode(nodeInfo);

        for(int i = 0; i < clusters.size(); i++) {
            if (node.getClusterID() == clusters.get(i).getClusterID()) {
                clusters.get(i).addNode(node);
            }
        }
    }

    public void addSensor(JSONObject sensorInfo) {
        Sensor sensor = parseToSensor(sensorInfo);

        for(int i = 0; i < clusters.size(); i++) {
            if(clusters.get(i).getClusterID() == sensor.getClusterID()) {
                for (int j = 0; j < clusters.get(i).getNodeList().size(); j++) {
                    if(clusters.get(i).getNodeList().get(j).getNodeID() == sensor.getNodeID()) {
                        clusters.get(i).getNodeList().get(j).addSensor(sensor);
                    }
                }
            }
        }
    }

    public void deleteCluster(JSONObject clusterInfo) {


    }

    public void deleteNode(JSONObject nodeInfo) {

    }

    public void deleteSensor(JSONObject sensorInfo) {

    }

    public void sensorDataCollect(JSONObject clusterInfo) {
        List<SensorData> sensorDataList = new LinkedList<>();
        JSONObject errReport = new JSONObject();
        Cluster cluster = parseToCluster(clusterInfo);
        for (int i = 0; i < clusters.size(); i++) {
            if(clusters.get(i).getClusterID() == cluster.getClusterID()) {
                for(int j = 0; j < clusters.get(i).getNodeList().size(); j++) {
                    for (int k = 0; k < clusters.get(i).getNodeList().get(j).getSensorList().size(); k++) {
                        ((LinkedList<SensorData>) sensorDataList).addLast(clusters.get(i).getNodeList().get(j).getSensorList().get(k).getSensorData());
                    }
                }
            }
        }
    }

    private Cluster parseToCluster(JSONObject clusterInfo) {
        Cluster cluster = new Cluster();
        return cluster;
    }

    private Node parseToNode(JSONObject nodeInfo) {
        Node node = new Node();
        return node;
    }

    private Sensor parseToSensor(JSONObject sensorInfo) {
        Sensor sensor = new Sensor();
        return sensor;
    }

}
