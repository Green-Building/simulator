package simulator;

import net.minidev.json.JSONObject;

import java.util.*;

public class Node{
	private int nodeID;
	private int clusterID = -1;
	private int sensorNumber = 0;
	private List<Sensor> sensorList = new LinkedList<>();

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public List<Sensor> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	public boolean addSensor(Sensor sensor) {
		this.sensorNumber++;
		return sensorList.add(sensor);
	}
	
	public boolean deleteSensor(int sensorID) {
		return true;
	}
	
	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public Set<SensorData> sendOutSensorData() {
		// read from files
		return null;
	}

	public Node buildNodeFromJSONObject(JSONObject nodeObj) {
		return null;
	}
	
	public JSONObject toJSONObject() {
		return null;
	}
}
