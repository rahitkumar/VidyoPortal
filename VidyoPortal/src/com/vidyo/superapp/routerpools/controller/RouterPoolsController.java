package com.vidyo.superapp.routerpools.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.Location;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.service.IServiceService;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.routerpools.bo.*;
import com.vidyo.superapp.routerpools.service.RouterPoolsService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class RouterPoolsController {

    private static final Logger logger = LoggerFactory.getLogger(RouterPoolsController.class);

    @Autowired
    RouterPoolsService routerPoolService;

    @Autowired
    IServiceService service;

    @RequestMapping(value = "getpoollist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<Pool> getPoolList(@RequestParam String status) throws ServiceException {
        logger.info("Start geting All Pools.");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<Pool> pools = new ArrayList<Pool>();

        if (null != cloudConfigID) {

            pools = routerPoolService.getPoolList(cloudConfigID);
        }

        return pools;
    }

    @RequestMapping(value = "getconnectionlist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<PoolToPool> getConnectionList(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Pool to Pool information.");

        List<PoolToPool> connectionList = routerPoolService.getConnectionList(status);

        return connectionList;
    }

    @RequestMapping(value = "getconnectionlistbypool.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<PoolToPool> getConnectionListByPool(@RequestParam String poolId, @RequestParam String status) throws ServiceException {
        logger.info("Start getting Pool to Pool information by Pool Id.");
        List<PoolToPool> connectionList = null;
        try {
            int poolId1 = Integer.parseInt(poolId);
            connectionList = routerPoolService.getConnectionListByPool(poolId1, status);
            for (PoolToPool connection : connectionList) {
                if (poolId1 == connection.getPoolToPoolPK().getPool2()) {
                    int direction = connection.getDirection();
                    connection.setDirection((direction * 2) % 3);
                    connection.getPoolToPoolPK().setPool2(connection.getPoolToPoolPK().getPool1());
                    connection.getPoolToPoolPK().setPool1(poolId1);
                }


            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.debug(ex.getMessage(), ex);
        }

        return connectionList;
    }

    @RequestMapping(value = "getavailablerouters.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<VidyoRouter> getAvailableRouters(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Available Routers");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<VidyoRouter> routersList = new ArrayList<VidyoRouter>();

        Date systemDate = Calendar.getInstance().getTime();

        if (null != cloudConfigID) {
            routersList = routerPoolService.getAvailableRouters(cloudConfigID);
            for (int i = 0; i < routersList.size(); i++) {
                Component components = routersList.get(i).getComponents();
                Date HeartBeatDate = components.getHeartbeatTime();
                int secondsBetween = (int) ((systemDate.getTime() - HeartBeatDate
                        .getTime()) / DateUtils.MILLIS_PER_SECOND);

                if (secondsBetween > 30) {
                    components.setCompStatus("DOWN");
                } else {
                    components.setCompStatus("UP");
                }
            }
        }

        return routersList;
    }

    @RequestMapping(value = "getactiverouters.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<VidyoRouter> getActiveRouters(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Available Routers");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<VidyoRouter> routersList = new ArrayList<VidyoRouter>();

        Date systemDate = new Date();

        if (null != cloudConfigID) {
            routersList = routerPoolService.getActiveRouters(cloudConfigID);
            for (int i = 0; i < routersList.size(); i++) {
                Component components = routersList.get(i).getComponents();
                Date HeartBeatDate = components.getHeartbeatTime();
                int secondsBetween = (int) ((systemDate.getTime() - HeartBeatDate
                        .getTime()) / DateUtils.MILLIS_PER_SECOND);

                if (secondsBetween > 30) {
                    components.setCompStatus("DOWN");
                } else {
                    components.setCompStatus("UP");
                }
            }
        }

        return routersList;
    }

    @RequestMapping(value = "getprioritylist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<PoolPriorityList> getPriorityList(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Pool Priority list");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<PoolPriorityList> poolPriorityList = new ArrayList<PoolPriorityList>();

        if (null != cloudConfigID) {
            poolPriorityList = routerPoolService.getAllPriorityLists(cloudConfigID);
        }
        return poolPriorityList;
    }

    @RequestMapping(value = "getpoolprioritylist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<PoolPriorityList> getPoolPriorityList(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Pool Priority list");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<PoolPriorityList> poolPriorityList = new ArrayList<PoolPriorityList>();

        if (null != cloudConfigID) {
            poolPriorityList = routerPoolService.getPriorityListsWithPools(cloudConfigID);
        }
        return poolPriorityList;
    }

    @RequestMapping(value = "savepool.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Pool addPool(@RequestBody String[] data) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start saving Pool");

        String deleteIDs[] = data[1].split(",");

        Pool poolObj = new ObjectMapper().readValue(data[0], Pool.class);


        Pool dbPool = null;
        if (poolObj != null && poolObj.getName() != null && poolObj.getName().trim().length() != 0)
            dbPool = routerPoolService.saveRouterPool(deleteIDs, poolObj);

        return dbPool;
    }

    @RequestMapping(value = "deletepool.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deletePools(@RequestBody String[] data) throws ServiceException {
        logger.info("Start deleting Pool");
        Map<String, String> map = new HashMap<String, String>();
        String[] id = data[0].split(",");
        String statusString = data[1];
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < id.length; i++) {
            deleteIds.add(Integer.parseInt(id[i]));
        }
        int success = routerPoolService.deletePool(deleteIds, statusString);
        if (success == 1) {
            map.put("success", "1");
            map.put("message", "Deleted successfully.");
        } else if (success == 0) {
            map.put("success", "0");
            map.put("message", "VidyoRouter pools which are used by some Rules cannot be deleted.");
        } else if (success == 2) {
            map.put("success", "2");
            map.put("message", "VidyoRouter pools which are used by Inter-Pool Configuration cannot be deleted.");
        }
        return map;
    }

    @RequestMapping(value = "savepoolprioritylist.ajax", method = RequestMethod.POST)
    public @ResponseBody
    PoolPriorityList addPoolPriorityList(@RequestBody String[] data) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start saving Pool Priority List");

        String statusString = data[2];

        List<Integer> deleteIds = new ArrayList<Integer>();
        if (!data[1].isEmpty()) {
            String ids[] = data[1].split(",");

            for (int i = 0; i < ids.length; i++) {
                deleteIds.add(Integer.parseInt(ids[i]));
            }
        }
        PoolPriorityList poolprioritylistObj = new ObjectMapper().readValue(data[0], PoolPriorityList.class);
        PoolPriorityList dbPoolPriorityList = routerPoolService.addPoolPriorityList(poolprioritylistObj, deleteIds, statusString);

        return dbPoolPriorityList;
    }

    @RequestMapping(value = "deletepoolprioritylist.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deletePoolPriorityList(@RequestBody String[] id) throws ServiceException {
        logger.info("Start deleting Pool Priority list");

        Map<String, String> map = new HashMap<String, String>();
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < id.length; i++) {
            deleteIds.add(Integer.parseInt(id[i]));
        }
        if (routerPoolService.isDeletePoolPriorityListAllowed(deleteIds)) {
            routerPoolService.deletePoolPriorityMapByPriorityListIds(deleteIds);
            routerPoolService.deletePoolPriorityList(deleteIds);
            map.put("success", "true");
            map.put("message", "Deleted successfully.");
        } else {
            map.put("success", "false");
            map.put("message", "Pool Priority Lists which are used by some Rules cannot be deleted.");
        }
        return map;
    }

    @RequestMapping(value = "addconnection.ajax", method = RequestMethod.POST)
    public @ResponseBody
    PoolToPool addConnection(@RequestBody String[] data) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start saving Pool Connection");

        //PoolToPool connectionObj = new ObjectMapper().readValue(pooltopool, PoolToPool.class);
        PoolToPool pooltopool = new ObjectMapper().readValue(data[0], PoolToPool.class);

        if (data[1] != null && data[1].trim().length() > 0 && !data[1].trim().equals("0"))
            routerPoolService.deleteConnections(pooltopool.getPool1(), Integer.parseInt(data[1]), "MODIFIED");

        PoolToPool dbConnection = routerPoolService.saveConnection(pooltopool);

        return dbConnection;
    }

    @RequestMapping(value = "deleteconnection.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteConnection(@RequestBody String[] data) throws ServiceException {
        logger.info("Start deleting Pool Connection");
        String pool1 = data[0];
        String pool2 = data[1];
        String status = data[2];

        Map<String, String> map = new HashMap<String, String>();
		/*List<Integer> deleteIds = new ArrayList<Integer>();
		for(int i=0; i< id.length; i++){
			deleteIds.add(Integer.parseInt(id[i]));
		}*/


        routerPoolService.deleteConnections(Integer.parseInt(pool1), Integer.parseInt(pool2), status);
        map.put("success", "true");
        map.put("message", "Deleted successfully.");
        return map;
    }

    @RequestMapping(value = "getruleslist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<Rule> getRulesList(@RequestParam String status) throws ServiceException {
        logger.info("Start getting Rule list");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<Rule> ruleList = new ArrayList<Rule>();

        if (null != cloudConfigID) {
            ruleList = routerPoolService.getRulesList(cloudConfigID);
        }

        return ruleList;
    }

    @RequestMapping(value = "addrule.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Rule addRule(@RequestBody String[] data) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start saving Rule");

        List<Integer> deleteIds = new ArrayList<Integer>();
        if (!data[1].isEmpty()) {
            String ids[] = data[1].split(",");

            for (int i = 0; i < ids.length; i++) {
                deleteIds.add(Integer.parseInt(ids[i]));
            }
        }

        Rule ruleObj = new ObjectMapper().readValue(data[0], Rule.class);

        Rule dbRule = routerPoolService.addRule(ruleObj, deleteIds);

        return dbRule;
    }

    @RequestMapping(value = "deleterule.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteRule(@RequestBody String[] id) throws ServiceException {
        logger.info("Start deleting Rule");

        Map<String, String> map = new HashMap<String, String>();
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < id.length; i++) {
            deleteIds.add(Integer.parseInt(id[i]));
        }
        routerPoolService.deleteRules(deleteIds);

        map.put("success", "true");
        map.put("message", "Deleted successfully.");
        return map;
    }

    @RequestMapping(value = "updateruleorder.ajax", method = RequestMethod.POST)
    public @ResponseBody
    List<Rule> updateRuleOrder(@RequestBody int[] data) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start updating Rule order");

        int currentOrder = data[0];
        int newOrder = data[1];

        List<Rule> updatedRulesList = routerPoolService.updateOrderForRuleList(currentOrder, newOrder);

        return updatedRulesList;
    }

    @RequestMapping(value = "getlocations.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<Location> getLocations() throws ServiceException {
        logger.info("Start getting location tags");

        List<Location> list = new ArrayList<Location>();

        list = routerPoolService.getLocations();
        return list;
    }

    @RequestMapping(value = "deletelocation.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> deleteLocations(@RequestBody String[] id) throws ServiceException {
        logger.info("Start deleting Location");
        Map<String, String> map = new HashMap<String, String>();
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < id.length; i++) {
            if (!id[i].equalsIgnoreCase("1")) {
                deleteIds.add(Integer.parseInt(id[i]));
            }
        }
        if (routerPoolService.isDeleteLocationAllowed(deleteIds)) {
            routerPoolService.deleteLocations(deleteIds);
            map.put("success", "true");
            map.put("message", "Deleted successfully.");
        } else {
            map.put("success", "false");
            map.put("message", "Locations which are used by some Rules cannot be deleted.");
        }
        return map;
    }

    @RequestMapping(value = "addlocation.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Location saveLocation(@RequestBody Location location) throws JsonParseException, JsonMappingException, IOException, ServiceException {
        logger.info("Start saving Location");

        // Cannot update or delete Default Location
        if (location.getLocationID() == 1) {
            return location;
        }

        List<Location> locations = routerPoolService.getLocationByTag(location.getLocationTag());
        if (!locations.isEmpty()) {
            // Duplicate checking logic
            Location duplicate = new Location();
            duplicate.setLocationID(-1);
            return duplicate;
        }

        Location dblocation = routerPoolService.saveLocation(location);

        return dblocation;
    }

    @RequestMapping(value = "getavailableprioritylist.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<PoolPriorityList> getAvailablePriorityList(@RequestParam String status) throws ServiceException {
        logger.info("Start getting available Priority List");

        Integer cloudConfigID = routerPoolService.getCloudConfigIdByStatus(status);
        List<PoolPriorityList> poolPriorityList = new ArrayList<PoolPriorityList>();

        if (null != cloudConfigID) {
            poolPriorityList = routerPoolService.getAvailablePoolPriorityList(cloudConfigID);
        }

        return poolPriorityList;
    }

    @RequestMapping(value = "activaterouterpools.ajax", method = RequestMethod.POST)
    public @ResponseBody
    boolean activateRouterPoolsConfig() throws ServiceException, GeneralException {
        logger.info("Start activating routers pools config");

        return routerPoolService.makeRouterPoolsConfigActive();
    }

    @RequestMapping(value = "copyactivateconfigasmodified.ajax", method = RequestMethod.POST)
    public @ResponseBody
    CloudConfig copyActiveConfigAsModified() {
        logger.info("Start copying active config as modified");
        return routerPoolService.makeCopyOfActiveRecords();
    }

    @RequestMapping(value = "ismodifiedpresent.ajax", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> isModifiedPresent() {
        boolean isModifiedPresent = routerPoolService.isRouterPoolPresent();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", isModifiedPresent);
        return map;
    }

    @RequestMapping(value = "discardmodifiedcloud.ajax", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> discardModifiedCoudConfig() {

        Map<String, Object> map = new HashMap<String, Object>();
        int status = 0;
        try {
            status = routerPoolService.discardModifiedCoudConfig("MODIFIED");

        } catch (GeneralException e) {
            status = -2;
            logger.error(e.getMessage());

        }

        if (status == -1) {

            map.put("success", false);
            map.put("message", "No modified cloud configuration found");
        } else if (status == -2) {
            map.put("success", false);
            map.put("message", "Server Error occurred during the operation. Delete failed. ");
        } else {
            map.put("success", true);
            map.put("message", "Deleted successfully.");
        }


        return map;
    }

    @RequestMapping(value = "getassignedpools.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<Pool> getAssignedPools(@RequestParam Integer priorityListId, @RequestParam Integer cloudConfigId) {
        return routerPoolService.getAssignedPools(priorityListId, cloudConfigId);
    }

    @RequestMapping(value = "getavailablepools.ajax", method = RequestMethod.GET)
    public @ResponseBody
    List<Pool> getAvailablePools(@RequestParam(required = false) Integer priorityListId, @RequestParam(required = false) Integer cloudConfigId, @RequestParam(required = false) String cloudConfigStatus) {
        List<Pool> pools = null;
        if (priorityListId != null && cloudConfigId != null) {
            pools = routerPoolService.getUnassignedPools(priorityListId, cloudConfigId);
        } else {
            cloudConfigId = routerPoolService.getCloudConfigIdByStatus(cloudConfigStatus);
            pools = routerPoolService.getPoolList(cloudConfigId);
        }
        return pools;
    }

}