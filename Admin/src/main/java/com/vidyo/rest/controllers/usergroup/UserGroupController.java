package com.vidyo.rest.controllers.usergroup;

import com.vidyo.bo.usergroup.UserGroup;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.ResourceNotFoundException;
import com.vidyo.service.usergroup.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping (value = "/service/v1")
public class UserGroupController{

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UserGroupService userGroupService;

    /**
     * This method is used to create <code>UserGroup</code> instance.
     *
     * @param userGroup - UserGroup instance

     * @return - UserGroup created
     */
    @PostMapping(value = "/usergroups/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserGroup createUserGroup(@Valid @RequestBody UserGroup userGroup) {
        logger.debug("Entering createUserGroup with params : " + userGroup);
        UserGroup savedUserGroup = userGroupService.createUserGroup(userGroup);
        logger.info("Created the usergroup object for the tenantId " + savedUserGroup.getUserGroupId());
        return savedUserGroup;
    }

    /**
     * This method is used to update <code>UserGroup</code> instance.
     *
     * @param userGroup - UserGroup instance

     * @return - UserGroup updated
     */
    @PutMapping(value = "/usergroups/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserGroup updateUserGroup( @Valid @PathVariable Integer id, @Valid @RequestBody UserGroup userGroup){
        logger.debug("Entering updateUserGroup with params : "+userGroup+" for id "+id);
        UserGroup updatedUserGroup = userGroupService.updateUserGroup(id, userGroup);
        logger.info("Update usergroup object for the tenantId "+userGroup.getUserGroupId());
        return updatedUserGroup;
    }

    /**
     * This is an overloaded method to be used for all fetch operations.
     * @param offset - offset page number
     * @param name - usergroup name
     * @param desc - usergroup description
     * @param limit - size
     * @param sort - sort - asc or desc
     * @param properties - sort columns
     * @return
     */
    @GetMapping(value = "/usergroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserGroup> fetchUserGroups( @Valid  @RequestParam (defaultValue = "0", required = false) int offset,
                                       @Valid  @RequestParam (required = false) String name,
                                       @Valid  @RequestParam (required = false) String desc,
                                       @Valid  @RequestParam (defaultValue = "50", required = false) int limit,
                                       @Valid  @Pattern(regexp="^(asc|desc|ASC|DESC)$") @RequestParam (defaultValue = "desc", required = false) String sort,
                                       @Valid  @Pattern(regexp="^(name|description)$") @RequestParam (defaultValue= "name", required = false) String ... properties
                                         ){
        logger.debug("Entering into fetchUserGroups method offset "+offset+" limit "+limit+ " sort "+sort+" properties "+ Arrays.asList(properties)+" name "+name+ " description "+desc);
        List<UserGroup> userGroup = userGroupService.fetchUserGroups(name, desc, offset, limit, sort, properties);
        return userGroup;
    }

    /**
     * This method returns the UserGroup instance based on id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/usergroups/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserGroup fetchUserGroup(@Valid @PathVariable Integer id){
        logger.debug("Entering into fetchUserGroup method "+id);
        UserGroup userGroup = userGroupService.fetchUserGroupById(id);
        return userGroup;
    }

    /**
     * This method will delete tye list of usergroups passed as an array
     *
     * @param userGroupIds - Array of usergroupIds.
     *
     */
    @DeleteMapping(value = "/usergroups/")
    public void deleteUserGroups( @Valid @RequestBody Set<Integer> userGroupIds) {
        logger.debug("Entering into deleteUserGroups method :" +userGroupIds);
        userGroupService.deleteUserGroups(userGroupIds);

    }

    /**
     * This method is used to fetch a specific usergroup instance based on id
     *
     * @param userGroupId - The UserGroupId to be fetched.
     */
    @DeleteMapping(value = "/usergroups/{id}")
    public void deleteUserGroup(@Valid @PathVariable("id") Integer userGroupId) {
        logger.debug("Entering into Delete usergroup method "+userGroupId);
        userGroupService.deleteUserGroup(userGroupId);
    }

    /**
     * This method is used to update the userGroupIds for a room
     *
     * @param roomId
     * @param userGoupIds
     */
    @PutMapping(value = "/rooms/{id}/usergroups", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void attachGroupsToRoom( @Valid @PathVariable("id") int roomId, @Valid @RequestBody Set<Integer> userGoupIds ){
        logger.debug("Entering into attachGroupsToRoom : params: "+roomId+" usergroupIds: "+userGoupIds);
        userGroupService.updateGroupsForRoom(roomId, userGoupIds, UserGroupService.Operation.UPDATE);
    }

    /**
     * This method is used to remove the usergroup ids for a room
     *
     * @param roomId
     * @param userGoupIds
     */

    @DeleteMapping(value = "/rooms/{id}/usergroups")
    public void removeGroupsFromRoom(@Valid @PathVariable("id") int roomId, @Valid  @RequestBody Set<Integer> userGoupIds ){
        logger.debug("Entering into removeGroupsFromRoom : params: "+roomId+" usergroupIds: "+userGoupIds);
        userGroupService.updateGroupsForRoom(roomId, userGoupIds, UserGroupService.Operation.DELETE);
    }

	/**
	 * This method returns the UserGroups for a Room
	 *
	 * @param roomId
	 * @return userGroups
	 */
	@GetMapping(value = "/rooms/{id}/usergroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<UserGroup> fetchUserGroupsAttachedToRoom(@Valid @PathVariable("id") Integer roomId) {
		logger.debug("Entering into fetchUserGroupsAttachedToRoom method : RoomId : " + roomId);
		return userGroupService.fetchUserGroupsAttachedToRoom(roomId);
	}

    /**
     * This method is used to updated the member with usergoupids
     *
     * @param memberId
     * @param userGoupIds
     */
    @PutMapping(value = "/members/{id}/usergroups", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void attachGroupsToUser(@Valid @PathVariable("id") int memberId, @Valid  @RequestBody Set<Integer> userGoupIds ){
        logger.debug("Entering into attachGroupsToUser : params: "+memberId+" usergroupIds: "+userGoupIds);
        userGroupService.updateGroupsForMember(memberId, userGoupIds, UserGroupService.Operation.UPDATE);
    }

    /**
     * This method is used to delete the usergroups for a member
     *
     * @param memberId
     * @param userGoupIds
     */

    @DeleteMapping(value = "/members/{id}/usergroups")
    public void removeGroupsFromUser(@Valid @PathVariable("id") int memberId, @Valid @RequestBody Set<Integer> userGoupIds ){
        logger.debug("Entering into removeGroupsFromUser : params: "+memberId+" usergroupIds: "+userGoupIds);
        userGroupService.updateGroupsForMember(memberId, userGoupIds, UserGroupService.Operation.DELETE);
    }

	/**
	 * This method returns the UserGroups for a Member
	 *
	 * @param memberId
	 * @return userGroups
	 */
	@GetMapping(value = "/members/{id}/usergroups", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<UserGroup> fetchUserGroupsAttachedToMember(@Valid @PathVariable("id") Integer memberId) {
		logger.debug("Entering into fetchUserGroupsAttachedToMember method : MemberId : " + memberId);
		return userGroupService.fetchUserGroupsAttachedToMember(memberId);
	}

    @ExceptionHandler(value = { MethodArgumentNotValidException.class, DataValidationException.class, IndexOutOfBoundsException.class} )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected List<String> handleMethodArgumentNotValid(final Exception ex) {
        final List<String> errors = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException) {
            for (final FieldError error : ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            for (final ObjectError error : ((MethodArgumentNotValidException)ex).getBindingResult().getGlobalErrors()) {
                errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            }
        } else if (ex instanceof  DataValidationException || ex instanceof  IndexOutOfBoundsException){
            errors.add(ex.getMessage());
        }
        return errors;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected List<String> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return errors;
    }
}
