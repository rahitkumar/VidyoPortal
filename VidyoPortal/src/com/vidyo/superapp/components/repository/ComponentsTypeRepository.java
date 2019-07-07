package com.vidyo.superapp.components.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidyo.superapp.components.bo.ComponentType;


public interface ComponentsTypeRepository extends JpaRepository<ComponentType, Integer>{

	public List<ComponentType> findByName(String name);
	
}
