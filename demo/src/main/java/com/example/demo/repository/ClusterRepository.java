package com.example.demo.repository;

import com.example.demo.Infrastructure.Floor;
import com.example.demo.SimulatingStructure.Cluster;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClusterRepository extends CrudRepository<Cluster, Long> {
    @Transactional
    @Query("UPDATE Cluster cluster SET cluster.status = :status where cluster.id = :id")
    int updateClusterStatusById(@Param("status") String Status, @Param("id")Long id);

    @Transactional
    @Query("select cluster from Cluster cluster where cluster.floor_id = :floor_id")
    Cluster findClusterByFloorId(@Param("floor_id")long floor_id);

    @Transactional
    @Modifying
    @Query("select cluster from Cluster cluster where cluster.building_id = :building_id")
    List<Cluster> findClusterByBuildingId(@Param("building_id")long building_id);
}
