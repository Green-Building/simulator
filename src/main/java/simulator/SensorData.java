package simulator;

import net.minidev.json.JSONObject;

public abstract class SensorData {
	private int sensorID;
	private int nodeID;
	private int clusterID;
	private String type;
	private String unit;
	private String data;
	private String date;
	private String timeStamp;

	public SensorData sensordataCollection(){
		return null;
	}

	public SensorData buildSensorDataFromJSONObject(JSONObject SensorData) {
		return null;
	}
	
	public JSONObject toJSONObject() {
		return null;
	}
	
}
