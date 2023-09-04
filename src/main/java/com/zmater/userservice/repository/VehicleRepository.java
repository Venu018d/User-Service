package com.zmater.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zmater.userservice.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>
{

}
