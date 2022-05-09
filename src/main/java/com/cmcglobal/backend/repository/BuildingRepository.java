package com.cmcglobal.backend.repository;

import com.cmcglobal.backend.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Page<Building> findAll(Pageable pageable);
    Page<Building> findAllByBuildingNameContaining(String buildingName, Pageable pageable);
    List<Building> findAllByIsEnableIsTrueOrderByBuildingNameAsc();
    boolean existsAllByBuildingAddress(String buildingAddress);
    boolean existsAllByBuildingName(String buildingName);
    boolean existsAllByBuildingAddressAndIdIsNot(String buildingAddress, int buildingId);
    boolean existsAllByBuildingNameAndIdIsNot(String buildingName, int buildingId);
}
