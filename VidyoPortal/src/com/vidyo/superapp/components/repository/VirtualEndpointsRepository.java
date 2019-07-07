package com.vidyo.superapp.components.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidyo.superapp.components.bo.VirtualEndpoints;




public interface VirtualEndpointsRepository extends  JpaRepository<VirtualEndpoints, Integer> {
	

}
