/**
 * 
 */
package com.vidyo.interceptors.vidyomanager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.db.ICDRCollection2Dao;
import com.vidyo.db.ICDRCollectionDao;

/**
 * Interceptor to do clean up before starting VidyoManager
 * 
 * @author Ganesh
 * 
 */
public class StartVidyoManagerInterceptor implements MethodInterceptor {

	/**
	 * 
	 */
	protected final Logger logger = LoggerFactory
			.getLogger(StartVidyoManagerInterceptor.class);

	/**
     * 
     */
	private ICDRCollectionDao collectionDao;
	
	/**
	 * CDR version2 DAO
	 */
	private ICDRCollection2Dao collection2Dao;
	
	/**
	 * 
	 */
	private static final String SERVER_RESTART = "SERVER_RESTART";
	
	/**
	 * 
	 */
	private static final String DEFAULT = "DEFAULT";	

	/**
	 * @param collectionDao
	 *            the collectionDao to set
	 */
	public void setCollectionDao(ICDRCollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}

	/**
	 * @param collection2Dao the collection2Dao to set
	 */
	public void setCollection2Dao(ICDRCollection2Dao collection2Dao) {
		this.collection2Dao = collection2Dao;
	}

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// Do clean up before the method invocation
		collectionDao.cleanConferences();
		collectionDao.resetVirtualEndpoints();
		collectionDao.resetEndpoints();
		collectionDao.resetRecorderEndpoints();
		collectionDao.updateCDRinProgressConferenceCall();
		collectionDao.updateCDRinProgressP2PCall();
		collectionDao.updateCDRinProgressConferenceCall2();
		collectionDao.cleanFederations();
		CDRinfo2 cdrInfo2 = generateServerStartInfo();
		collection2Dao.createServerRestartRecord(cdrInfo2);		
		logger.info("Done clean up before starting VidyoManager");
		// Proceed with the invocation and return the response
		return invocation.proceed();
	}
	
	/**
	 * 
	 * @return
	 */
	private CDRinfo2 generateServerStartInfo() {
		CDRinfo2 cdrInfo2 = new CDRinfo2();
		cdrInfo2.setConferenceName(SERVER_RESTART);
		cdrInfo2.setTenantName(DEFAULT);
		cdrInfo2.setConferenceType("C");
		cdrInfo2.setEndpointType("D");
		cdrInfo2.setCallerID(DEFAULT);
		cdrInfo2.setCallerName(DEFAULT);
		cdrInfo2.setCallState(SERVER_RESTART);
		cdrInfo2.setDirection("I");
		return cdrInfo2;
	}	

}