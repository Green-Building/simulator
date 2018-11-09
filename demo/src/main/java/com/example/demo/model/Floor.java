package com.example.demo.model;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long building_id;
    private Integer floor_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Long building_id) {
        this.building_id = building_id;
    }

    public Integer getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(Integer floor_number) {
        this.floor_number = floor_number;
    }

}

