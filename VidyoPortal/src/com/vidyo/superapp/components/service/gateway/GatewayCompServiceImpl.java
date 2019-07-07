/**
 * 
 */
package com.vidyo.superapp.components.service.gateway;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.bo.gateway.GatewayPrefix;
import com.vidyo.superapp.components.repository.VirtualEndpointsRepository;
import com.vidyo.superapp.components.repository.gateway.GatewayPrefixRepository;
import com.vidyo.superapp.components.repository.gateway.GatewayRepository;

/**
 * @author ganesh
 *
 */
@Service("GatewayCompService")
public class GatewayCompServiceImpl implements GatewayCompService {

	protected static final Logger logger = LoggerFactory
			.getLogger(GatewayCompServiceImpl.class);

	@Autowired
	private GatewayRepository gatewayRepository;

	@Autowired
	private GatewayPrefixRepository gatewayPrefixRepository;

	@Autowired
	private VirtualEndpointsRepository virtualEndpointsRepository;

	/**
	 * Returns VidyoGateway details by ComponentId
	 * 
	 * @param compId
	 * @return
	 */
	@Override
	public VidyoGateway getGatewayByCompId(int compId) {
		VidyoGateway gateway = null;
		try {
			gateway = gatewayRepository.findGatewayByCompId(compId);			
		} catch (DataAccessException e) {
			logger.error("DataAccessExceptionw while getting the Gateway by CompId "
					+ compId + " " + e.getMessage());
		}
		return gateway;
	}

	/**
	 * Saves VidyoGateway 
	 * 
	 * @param vidyoGateway
	 * @return
	 */
	@Transactional
	@Override
	public VidyoGateway saveGateway(VidyoGateway vidyoGateway) {
		Date date = Calendar.getInstance().getTime();
		vidyoGateway.setCreationTime(date);
		vidyoGateway.setUpdateTime(date);
		return gatewayRepository.save(vidyoGateway);
	}

	@Override
	public List<GatewayPrefix> getGatewayPrefixesById(int gatewayId) {
		VidyoGateway vidyoGateway = gatewayRepository.findOne(gatewayId);
		if(vidyoGateway != null) {
			return vidyoGateway.getGatewayPrefixs();
		}
		return null;
	}

}
