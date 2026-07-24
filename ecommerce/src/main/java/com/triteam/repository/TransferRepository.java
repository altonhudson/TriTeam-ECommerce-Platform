package com.triteam.repository;

import com.triteam.entity.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>{
}
