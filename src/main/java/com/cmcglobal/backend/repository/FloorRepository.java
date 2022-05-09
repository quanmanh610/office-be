package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.Building;
import com.cmcglobal.backend.entity.Dot;
import com.cmcglobal.backend.entity.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {
    Page<Floor> findAllByFloorNameContaining(String floorName, Pageable pageable);
    List<Floor> findAllByIsEnableIsTrue();
    List<Floor> findAllByIsEnableIsTrueAndBuilding(Building building);
    boolean existsAllByFloorNameAndBuilding(String floorName, Building buildingId);
    boolean existsAllByFloorNameAndBuildingAndIdIsNot(String floorName, Building buildingId, Integer floorId);
    Floor getFloorByDotListIn(List<Dot> dotList);

    @Query(value = "SELECT id from floor WHERE building_id = :buildingId", nativeQuery = true)
    List<Integer> findFloorIdByBuildingId(@Param("buildingId") Integer buildingId);

    Integer countFloorsByBuilding(Building building);

    @Query(value = "SELECT SUM(number_of_room_dot) FROM floor WHERE building_id = :buildingId", nativeQuery = true)
    Integer countRoomDotsInBuilding(Integer buildingId);

    @Query(value = "SELECT SUM(number_of_seat_dot) FROM floor WHERE building_id = :buildingId", nativeQuery = true)
    Integer countSeatDotsInBuilding(Integer buildingId);

    @Query(value = "SELECT SUM(cost) FROM floor WHERE building_id = :buildingId", nativeQuery = true)
    Float sumTotalBuildingCost(int buildingId);

    @Query(value = "SELECT SUM(dot_price_per_month * number_of_seat_dot) FROM floor WHERE building_id = :buildingId", nativeQuery = true)
    Float sumTotalBuildingPrice(int buildingId);
}
