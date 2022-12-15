package com.emily.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emily.entity.Trip;

@Repository
public interface TripDao extends JpaRepository<Trip, Integer>{

}
