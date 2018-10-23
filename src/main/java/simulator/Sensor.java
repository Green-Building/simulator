package simulator;

import net.minidev.json.JSONObject;

public class Sensor {
	private int sensorID;
	private int clusterID;
	private int nodeID;
	private SensorData sensorData;

	public Sensor() {

	}

	public int getSensorID() {
		return sensorID;
	}

	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}

	public SensorData getSensorData() {
		return sensorData;
	}

	public void setSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}

	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public Sensor buildSensorFromJSONObject(JSONObject sensorObj) {
		return null;
	}
	
	public JSONObject toJSONObject() {
		return null;
	}
}
