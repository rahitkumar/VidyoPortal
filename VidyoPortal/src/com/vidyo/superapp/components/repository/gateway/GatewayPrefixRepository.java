/**
 * 
 */
package com.vidyo.superapp.components.repository.gateway;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidyo.superapp.components.bo.gateway.GatewayPrefix;

/**
 * @author ganesh
 *
 */
public interface GatewayPrefixRepository extends JpaRepository<GatewayPrefix, Integer> {
	

}
