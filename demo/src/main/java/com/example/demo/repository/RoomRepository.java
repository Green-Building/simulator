package com.example.demo.repository;

        import com.example.demo.Infrastructure.Room;
        import com.example.demo.SensorData.SensorData;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
        @Transactional
        @Query("select room from Room room where room.floor_id = :floor_id")
        List<Room> findRoomByFloorId(@Param("floor_id")Long floor_id);
}

