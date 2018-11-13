package com.example.demo.repository;

        import com.example.demo.Infrastructure.Floor;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

public interface FloorRepository extends CrudRepository<Floor, Long> {
        @Transactional
        @Query("select floor from Floor floor where floor.building_id = :building_id")
        List<Floor> findFloorByBuildingId(@Param("building_id")long building_id);
}




