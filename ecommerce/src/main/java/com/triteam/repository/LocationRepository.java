package com.triteam.repository;

import com.triteam.entity.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
}
