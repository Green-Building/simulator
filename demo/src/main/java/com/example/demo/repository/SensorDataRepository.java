package com.example.demo.repository;

import com.example.demo.model.SensorData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SensorDataRepository extends CrudRepository<SensorData, Long> {

    @Query("select sd from SensorData sd where sd.clusterId = :clusterId")
    List<SensorData> findSensorDataByClusterId(@Param("clusterId")Long clusterId);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.clusterId = :clusterId")
    void deleteSensorDataByClusterId(@Param("clusterId")Long clusterId);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.nodeId = :nodeId")
    void deleteSensorDataByNodeId(@Param("nodeId")Long nodeId);

    @Transactional
    @Modifying
    @Query("delete from SensorData sensorData where sensorData.sensorId = :sensorId")
    void deleteSensorDataBySensorId(@Param("sensorId")Long sensorId);

    @Transactional
    @Modifying
    @Query("UPDATE SensorData sensorData SET sensorData.sensorData = :sensorData where sensorData.clusterId = :clusterId")
    int updateSensorDataValueByClusterId(@Param("sensorData") Double sensorData, @Param("clusterId")Long clusterId);

    @Transactional
    @Modifying
    @Query("UPDATE SensorData sensorData SET sensorData.sensorData = :sensorData where sensorData.id = :id")
    int updateSensorDataValueBySensorDataId(@Param("sensorData") Double sensorData, @Param("id")Long id);

}
