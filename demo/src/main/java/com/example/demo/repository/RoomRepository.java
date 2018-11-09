package com.example.demo.repository;

        import com.example.demo.model.Building;
        import com.example.demo.model.Cluster;
        import com.example.demo.model.Room;
        import org.springframework.data.jpa.repository.Modifying;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.transaction.annotation.Transactional;

public interface RoomRepository extends CrudRepository<Room, Long> {

}
