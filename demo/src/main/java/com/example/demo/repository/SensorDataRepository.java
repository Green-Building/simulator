package com.example.demo.repository;

import com.example.demo.SensorData.SensorData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SensorDataRepository extends CrudRepository<SensorData, Long> {

    @Query("select sd from SensorData sd where sd.cluster_id = :cluster_id")
    List<SensorData> findSensorDataByClusterId(@Param("cluster_id")Long cluster_id);

    @Query("select sd from SensorData sd where sd.sensor_id = :sensor_id")
    SensorData findSensorDataBySensorId(@Param("sensor_id")Long sensor_id);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.cluster_id = :cluster_id")
    void deleteSensorDataByClusterId(@Param("cluster_id")Long cluster_id);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.node_id = :node_id")
    void deleteSensorDataByNodeId(@Param("node_id")Long node_id);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.sensor_id = :sensor_id")
    void deleteSensorDataBySensorId(@Param("sensor_id")Long sensor_id);

    /**
    @Transactional
    @Modifying
    @Query("UPDATE SensorData sensorData SET sensorData.sensorData = :sensorData where sensorData.cluster_id = :cluster_id")
    int updateSensorDataValueByClusterId(@Param("sensorData") Double sensorData, @Param("cluster_id")Long cluster_id);

    @Transactional
    @Modifying
    @Query("UPDATE SensorData sensorData SET sensorData.sensorData = :sensorData where sensorData.sensordata_id = :sensordata_id")
    int updateSensorDataValueBySensorDataId(@Param("sensorData") Double sensorData, @Param("sensordata_id")Long sensordata_id);
    **/

}
