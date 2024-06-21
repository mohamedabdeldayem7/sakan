package com.sakan.sakan.repository;

import com.sakan.sakan.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, UUID> {

    List<House> findAllByLocation(String location);

}
