/**
 * ConfigurationAPIServiceSkeleton.java
 */
package com.vidyo.ws.configurationapi;

import com.vidyo.bo.NEConfiguration;
import com.vidyo.service.IServiceService;

import javax.activation.DataHandler;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 *  ConfigurationAPIServiceSkeleton java skeleton for the axisService
 */
public class ConfigurationAPIServiceSkeleton implements ConfigurationAPIServiceSkeletonInterface{
    
    protected final Logger logger = LoggerFactory.getLogger(ConfigurationAPIServiceSkeleton.class.getName());

    private IServiceService service;
    public void setService(IServiceService service) {
        this.service = service;
    }

    /**
     * Auto generated method signature
     * Enable or Disable a network component
     *      @param req
     *      @throws GeneralFaultException :
     */
    public EnableNetworkComponentResponse enableNetworkComponent(EnableNetworkComponentRequest req) throws GeneralFaultException{
        try {
            String identifier = req.getIdentifier();
            Action_type0 type0 = req.getAction();
            boolean enable = (type0==Action_type0.ENABLE);
            EnableNetworkComponentResponse resp = new EnableNetworkComponentResponse();
            resp.setAction(type0);
            resp.setIdentifier(identifier);

            if(!this.service.enableNEConfiguration(identifier, enable))
                resp.setSuccess(false);
            else
                resp.setSuccess(true);
            return resp;
        }
        catch(Exception anyEx){
            logger.error("Failed to enable/disable network component");
            throw new GeneralFaultException("Failed to enable/disable network component");
        }
    }


    /**
     * Auto generated method signature
     * List all network components
     *      @param reg
     *      @throws GeneralFaultException :
     */
    public ListNetworkComponentsResponse listNetworkComponents(ListNetworkComponentsRequest reg) throws GeneralFaultException{
        try {
            ComponentType_type0 type0 = reg.getComponentType();
            String type = type0.getValue();

            List<NEConfiguration> list = this.service.getNEConfigurations("%", type);

            ListNetworkComponentsResponse resp = new ListNetworkComponentsResponse();

            Iterator it = list.iterator();
            long now = new Date().getTime();
            while (it.hasNext()) {
                NEConfiguration nec = (NEConfiguration)it.next();
                SingleComponentDataType com = new SingleComponentDataType();

                com.setIdentifier(nec.getIdentifier());
                com.setDisplayName(nec.getDisplayName());
                com.setComponentType(type0);
                com.setIpAddress(nec.getIpAddress());
                com.setAlarm(nec.getAlarm());
                com.setSwVer(nec.getSwVer());
                com.setConfigData(new DataHandler(nec.getData(), "text/plain; charset=UTF-8"));

                if((type0 != ComponentType_type0.VidyoGateway) && (type0 != ComponentType_type0.VidyoReplayRecorder)){
                    com.setRunningVersion(nec.getRunningVersion());
                    com.setVersion(nec.getVersion());
                }

                if(nec.getStatus().equals("ACTIVE")){
                    if(nec.getHeartbeat() == null) {
                        com.setStatus(Status_type0.DOWN);
                    }
                    else {
                        long last = nec.getHeartbeat().getTime();
                        if((now - last) > 30*1000)
                            com.setStatus(Status_type0.DOWN);
                        else
                            com.setStatus(Status_type0.UP);
                    }
                }
                if(nec.getStatus().equals("NEW")){
                     com.setStatus(Status_type0.NEW);
                }
                else if(nec.getStatus().equals("INACTIVE")){
                    com.setStatus(Status_type0.DISABLED);
                }

                resp.addNetworkComponent(com);
            }
            return resp;
        }
        catch(Exception anyEx){
            logger.error("Failed to retrieve components list");
            throw new GeneralFaultException("Failed to retrieve components list");
        }
    }
}
    
