package simulator;

import net.minidev.json.JSONObject;

import java.util.*;

public class Cluster {

	private int clusterID;
	private GeoLocation geoLocation;
	private List<Integer> nodeNameList = new LinkedList<>();
	private List<Node> nodeList = new LinkedList<>();
	private List<SensorData> sensorDataList = new LinkedList<>(); 
	
	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	public boolean addNode (Node node) {
		this.nodeNameList.add(node.getNodeID());
		return this.nodeList.add(node);
	}
	
	public boolean deleteNode (int nodeID) {
		return true;
	}

	public void receiveData() {

	}
	
	public List<SensorData> sendDataToBackEnd() {
		return sensorDataList;
	}
	
	public Cluster buildClusterFromJSONObject(JSONObject clusterObj) {
		return null;
	}
	
	public JSONObject toJSONObject() {
		return null;
	}

}
