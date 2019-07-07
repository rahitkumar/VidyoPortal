package com.vidyo.service.ldap;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.exceptions.ExtensionExistException;
import com.vidyo.service.exceptions.InvalidUserNameException;
import com.vidyo.service.exceptions.LineLicenseExpiredException;
import com.vidyo.service.exceptions.SeatLicenseExpiredException;
import com.vidyo.service.exceptions.UserExistException;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LdapUserToMemberAttributesMapperImpl implements ILdapUserToMemberAttributesMapper,AttributesMapper {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(LdapUserToMemberAttributesMapperImpl.class.getName());

    private IGroupService group;
    private ISystemService system;
    private IMemberService member;
    private IRoomService room;
    private ITenantLdapAttributesMapping tenantLdapAttributes;
    private LicensingService license;
    private ITenantService tenantService;
    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    public void setGroup(IGroupService group) {
        this.group = group;
    }

    public void setSystem(ISystemService system) {
        this.system = system;
    }

    public void setMember(IMemberService member) {
        this.member = member;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setTenantLdapAttributes(ITenantLdapAttributesMapping tenantLdapAttributes) {
        this.tenantLdapAttributes = tenantLdapAttributes;

    }

    public void setLicense(LicensingService license) {
        this.license = license;
    }

    @Deprecated
    @Override
    public Member getMemberFromAttributes(Attributes attributes) throws NamingException {
        Integer tenantID = TenantContext.getTenantId();
        return mapFromAttributes(tenantID, attributes);
    }

    @Override
    public Member getMemberFromAttributes(int tenantID, Attributes attributes) throws NamingException {
        return mapFromAttributes(tenantID, attributes);
    }

    @Override
    public Member mapFromAttributes(Attributes attributes) throws NamingException {
        Integer tenantID = TenantContext.getTenantId();
        return mapFromAttributes(tenantID, attributes);
    }

    private Member mapFromAttributes(int tenantID, Attributes attributes) throws NamingException {

        List<TenantLdapAttributeMapping> attributeMappings = this.tenantLdapAttributes.getTenantLdapAttributeMapping(tenantID);

        Member member = new Member();
        member.setImportedUsed(1); // this is imported user (from LDAP or etc.)
        member.setEnable("on");
        member.setAllowedToParticipateHtml("on");
        member.setTenantPrefix(this.member.getTenantPrefix());
        member.setLangID(10); // Language 10 = Default System Language

        // by default - will be overwritten if value mapping match portal DB
        member.setGroupID(this.group.getDefaultGroup().getGroupID());
        member.setGroupName(this.group.getDefaultGroup().getGroupName());

        member.setProxyID(0);
        member.setProxyName("No Proxy");

        member.setLocationID(this.system.getTenantDefaultLocationTagID());

        Member existingUser = null;
        // As there attributes which depend on UserName, this attribute processes separately
        for (TenantLdapAttributeMapping mapping : attributeMappings) {
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("UserName")) {
                String userName = this.getLdapAttributeValue(attributes, mapping);
                if(userName == null || userName.isEmpty() || userName.length() > 80 ||
                        !userName.matches(ValidationUtils.USERNAME_REGEX)) {
                    throw new NamingException("Invalid user name");
                }
                member.setUsername(userName);
                if(this.member.isMemberExistForUserName(userName, 0)) {
                    existingUser = this.member.getMemberByName(userName);
                }
                break;
            }
        }

        for (TenantLdapAttributeMapping mapping : attributeMappings) {
            // The usertype being passed in is used to assign the member role.
            // Super and Legacy roles cannot be assigned via this workflow.
            // For LDAP, the usertype value mappings are picked up from the
            // TenantLdapAttributeValueMapping table.
            // The setMemberRoleOfMember method, fetches the user role from the
            // database and assigns it to the user. If the user role doesn't
            // exist, the user is assigned the default user role - Normal
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("UserType")) {
                String userType = this.getLdapAttributeValue(attributes, mapping);
                if (userType != null) {
                    if(!this.member.setMemberRoleOfMember(member, userType)) {
                        this.member.setMemberRoleOfMember(member, mapping.getDefaultAttributeValue());
                    }
                } else {
                    this.member.setMemberRoleOfMember(member, mapping.getDefaultAttributeValue());
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("DisplayName")) {
                String memberName = this.getLdapAttributeValue(attributes, mapping);
                if (memberName != null && !memberName.equalsIgnoreCase("") && memberName.length() <= 80) {
                    member.setMemberName(memberName);
                } else {
                    member.setMemberName(member.getUsername());
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("EmailAddress")) {
                String emailAddress = this.getLdapAttributeValue(attributes, mapping);
                if (emailAddress != null && !emailAddress.equalsIgnoreCase("")) {
                    if (!emailAddress.matches(ValidationUtils.EMAIL_REGEX)) {
                        if (member.getUsername().contains("@")) {
                            // in case username is email address - use it
                            emailAddress = member.getUsername();
                        } else {
                            emailAddress = member.getUsername() + "@" + mapping.getDefaultAttributeValue();
                        }
                    }
                    member.setEmailAddress(emailAddress);
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Extension")) {
                String extNum = this.getLdapAttributeValue(attributes, mapping);
                if(extNum != null) {
                    extNum = extNum.replaceAll("\\D", "");
                }
                if (extNum == null
                    || extNum.equalsIgnoreCase("")
                    || extNum.equalsIgnoreCase(member.getTenantPrefix())
                    || !extNum.matches("^[0-9]+$")
                    || (extNum.length() > 16)) // will add tenant prefix
                {
                    if(existingUser != null) {
                        member.setRoomExtNumber(existingUser.getRoomExtNumber());
                    } else {
                        extNum = this.room.generateRoomExt(member.getTenantPrefix());
                        member.setRoomExtNumber(extNum);
                    }
                } else {
                    member.setRoomExtNumber(member.getTenantPrefix() + extNum);
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Group")) {
                String group = this.getLdapAttributeValue(attributes, mapping);
                if (group != null) {
                    if(!this.member.setGroupOfMember(member, group)) {
                        this.member.setGroupOfMember(member, mapping.getDefaultAttributeValue());
                    }
                } else {
                    this.member.setGroupOfMember(member, mapping.getDefaultAttributeValue());
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Description")) {
                String description = this.getLdapAttributeValue(attributes, mapping);
                if (description != null) {
                    member.setDescription(description);
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Proxy")) {
                String proxy = this.getLdapAttributeValue(attributes, mapping);
                if (proxy != null && !proxy.equalsIgnoreCase("No Proxy")) {
                    if(!this.member.setProxyOfMember(member, proxy)) {
                        this.member.setProxyOfMember(member, mapping.getDefaultAttributeValue());
                    }
                } else if(proxy == null) {
                    this.member.setProxyOfMember(member, mapping.getDefaultAttributeValue());
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("LocationTag")) {
                String locationTag = this.getLdapAttributeValue(attributes, mapping);
                if (locationTag != null) {
                    if(!this.member.setTenantLocationTagOfMember(member, locationTag)) {
                        this.member.setTenantLocationTagOfMember(member, mapping.getDefaultAttributeValue());
                    }
                } else {
                    this.member.setTenantLocationTagOfMember(member, mapping.getDefaultAttributeValue());
                }
            }

            if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber1")) {
                String ph1 = this.getLdapAttributeValue(attributes, mapping);
                if (ph1 != null) {
                    member.setPhone1(ph1);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber2")) {
                String ph2 = this.getLdapAttributeValue(attributes, mapping);
                if (ph2 != null) {
                    member.setPhone2(ph2);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber3")) {
                String ph3 = this.getLdapAttributeValue(attributes, mapping);
                if (ph3 != null) {
                    member.setPhone3(ph3);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Department")) {
                String department = this.getLdapAttributeValue(attributes, mapping);
                if (department != null) {
                    member.setDepartment(department);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Title")) {
                String title = this.getLdapAttributeValue(attributes, mapping);
                if (title != null) {
                    member.setTitle(title);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("IM")) {
                String im = this.getLdapAttributeValue(attributes, mapping);
                if (im != null) {
                    member.setInstantMessagerID(im);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Location")) {
                String location = this.getLdapAttributeValue(attributes, mapping);
                if (location != null) {
                    member.setLocation(location);
                }
            }
            if (mapping.getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo")) {
                Attribute attr = attributes.get(mapping.getLdapAttributeName());
                byte[] imageBytes=null;
                if (attr != null && attr.get()!=null) {
                    try{
                         imageBytes = (byte[]) attr.get();
                         member.setThumbNailImage(imageBytes);
                    }catch(Exception e){
                        logger.error("error during converting values from ldap for the attribute "+mapping.getLdapAttributeName()+" into byte array,value is "+attr.get(),e);
                    }
                }
            }

			if (mapping.getVidyoAttributeName().equalsIgnoreCase("User Groups")
					&& StringUtils.isNotBlank(mapping.getLdapAttributeName())) {
				Attribute attr = attributes.get(mapping.getLdapAttributeName());
				Set<String> userGroups = new HashSet<String>();
				if (attr != null) {
					Configuration configuration = system.getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
					int maxGroupsToBeImported = (configuration != null)
							? Integer.valueOf(configuration.getConfigurationValue()).intValue() : 100;
					NamingEnumeration<?> attributeValueEnumerator = attr.getAll();
					while (attributeValueEnumerator.hasMore()) {
						Matcher userGroupMatcher = USER_GROUP_DETAILS.matcher((String) attributeValueEnumerator.next());
						if (userGroupMatcher.find()) {
							userGroups.add(userGroupMatcher.group(2));
							maxGroupsToBeImported--;
						}

						if (maxGroupsToBeImported == 0) {
							break;
						}
					}
				}
				if (CollectionUtils.isNotEmpty(userGroups)) {
					member.setUserGroupsFromAuthProvider(userGroups);
				}
				member.setUserGroupsUpdated(true);
			}

        }

        return member;
    }

    private static final Pattern USER_GROUP_DETAILS = Pattern.compile(".*(cn|CN)=(.[^,]+),.*");

    @Override
    public String getLdapAttributeValue(Attributes attributes, TenantLdapAttributeMapping attributeMapping) {
        String rc = attributeMapping.getDefaultAttributeValue(); // set default value
        Attribute attr = attributes.get(attributeMapping.getLdapAttributeName());
        if (attr != null) {
            for (int i = 0; i < attr.size(); i++) {
                Object attrValue;
                try {
                    attrValue = attr.get(i);
                } catch (NamingException e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("No attrValue in LDAP attribute - " + attributeMapping.getLdapAttributeName());
                    }
                    continue;
                }
                if (attrValue != null) {
                    boolean isValueMappingDone = false;
                    // check attrValue mapping
                    List<TenantLdapAttributeValueMapping> values = this.tenantLdapAttributes.
                            getTenantLdapAttributeValueMapping(attributeMapping.getTenantID(), attributeMapping.getMappingID());
                    for (TenantLdapAttributeValueMapping v: values) {
                        if (v.getLdapValueName().equalsIgnoreCase("")) continue;
                        if (v.getLdapValueName().equalsIgnoreCase(attrValue.toString())) {
                            rc = v.getVidyoValueName();
                            isValueMappingDone = true;
                            break;
                        }
                    }

                    if (isValueMappingDone) {
                        break; // will do a first found in value mapping
                    } else {
                        if (attrValue.toString() != null && !attrValue.toString().equalsIgnoreCase("")) {
                            rc = attrValue.toString();
                        }
                    }
                }
            }
        }
        return rc;
    }

    @Override
    public int importLdapMember (Member member)
            throws UserExistException, ExtensionExistException, SeatLicenseExpiredException, InvalidUserNameException,
            AccessRestrictedException
    {
        if (this.member.isMemberExistForUserName(member.getUsername(), 0)) {
            // do update member and his personal room
            Member existingMember = this.member.getMemberByName(member.getUsername());

            // TODO - update member if attributes was changed
            if (existingMember.getRoleID() != member.getRoleID()
                || !existingMember.getMemberName().equalsIgnoreCase(member.getMemberName())
                || !existingMember.getEmailAddress().equalsIgnoreCase(member.getEmailAddress())
                || !existingMember.getRoomExtNumber().equalsIgnoreCase(member.getRoomExtNumber())
                || existingMember.getGroupID() != member.getGroupID()
                || !existingMember.getDescription().equalsIgnoreCase(member.getDescription())
                || existingMember.getProxyID() != member.getProxyID()
                || existingMember.getLocationID() != member.getLocationID()
                || existingMember.getPhone1() != member.getPhone1()
                || existingMember.getPhone2() != member.getPhone2()
                || existingMember.getPhone3() != member.getPhone3()
                || existingMember.getDepartment() != member.getDepartment()
                || existingMember.getTitle() != member.getTitle()
                || existingMember.getInstantMessagerID() != member.getInstantMessagerID()
                || existingMember.getLocation() != member.getLocation()
                )
            {
                if (this.room.isRoomExistForRoomExtNumber(member.getRoomExtNumber(), existingMember.getRoomID())) {
                    logger.warn("Extension conflict. Extension will not be updated.");
                    member.setRoomExtNumber(existingMember.getRoomExtNumber());
                }
                // Set member's password null, otherwise the updateMember() will logout the logged in user and regenerate pak
                // which will cause locking out of member.
                member.setPassword(null);
                this.member.updateMember(existingMember.getMemberID(), member);


            }
            return member.getMemberID();
        } else {
            // change extension number
            String extNum = member.getRoomExtNumber();
            if (extNum == null
                || extNum.equalsIgnoreCase("")
                || extNum.equalsIgnoreCase(member.getTenantPrefix())
                || !extNum.matches("^[0-9]+$")
                || (extNum.length() - member.getTenantPrefix().length() > 16)) // tenant prefix already added
            {
                extNum = this.room.generateRoomExt(member.getTenantPrefix());
                member.setRoomExtNumber(extNum);
            }

            // check member name
            String userName = member.getUsername();
            if(userName == null || userName.isEmpty() || userName.length() > 80 ||
                    !userName.matches(ValidationUtils.USERNAME_REGEX)) {
                throw new InvalidUserNameException("Invalid user name");
            }

            if( member.getMemberName() == null || member.getMemberName().trim().length() <= 0 || member.getMemberName().trim().length() > 80 ) {
                throw new InvalidUserNameException("Invalid member name");
            }
            // STEP 1 - check if license allowed to create a new users - lines, Executive and VidyoPanorama types
            if (this.license.lineLicenseExpired()) {
                throw new LineLicenseExpiredException("LineLicenseExpired for the System");
            }

            // checking for available seat for Admin, Operator or Normal types of user
            if((member.getRoleID() == 1 || member.getRoleID() == 2 || member.getRoleID() == 3) && license.getAllowedSeats() < 1) {
                throw new SeatLicenseExpiredException("No seats available for Admin/Operator/Normal User creation");
            }

            // checking for available seat for Executive types of user
            if (member.getRoleID() == 7 && license.getAllowedExecutives() < 1) {
                throw new SeatLicenseExpiredException("No seats available for Executive user creation");
            }

            // checking for available seat for VidyoPanorama types of user
            if (member.getRoleID() == 8 && license.getAllowedPanoramas() < 1) {
                throw new SeatLicenseExpiredException("No seats available for Panorama user creation");
            }

            // checking for duplication
            if (this.member.isMemberExistForUserName(member.getUsername(), 0)) {
                throw new UserExistException();
            }
            if (this.room.isRoomExistForRoomExtNumber(member.getRoomExtNumber(), 0)) {
                throw new ExtensionExistException();
            }

            int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, member.getRoomExtNumber());
            if (extExists > 0) {
                throw new ExtensionExistException("Extension matches Schedule Room prefix. Please choose a different extension and try again");
            }
            // STEP 3 - do insert member and his personal room
            int memberID = this.member.insertMember(member);

            // create a Personal Room for the member
            Room room = new Room();
            room.setMemberID(memberID);
            room.setRoomDescription(member.getUsername()+"-Personal Room");
            room.setGroupID(member.getGroupID());
            room.setRoomEnabled(member.getActive());
            room.setRoomMuted(0);
            room.setRoomSilenced(0);
            room.setRoomExtNumber(member.getRoomExtNumber());
            room.setRoomName(member.getUsername());
            room.setRoomTypeID(1); // Personal Room
            room.setDisplayName(member.getMemberName());
            if (VendorUtils.isRoomsLockedByDefault()) {
                room.setRoomLocked(1);
            }
            this.room.insertRoom(room);

            return memberID;

        }

    }

    @Deprecated
    public String[] getReturnedAttributes() {
        ArrayList<String> rc = new ArrayList<String>();

        List<TenantLdapAttributeMapping> attributeMappings = this.tenantLdapAttributes.getTenantLdapAttributeMapping();
        for (TenantLdapAttributeMapping mapping : attributeMappings) {
            if (!mapping.getLdapAttributeName().equalsIgnoreCase("")) {
                rc.add(mapping.getLdapAttributeName());
            }
        }

        return rc.toArray(new String[rc.size()]);
    }
    public String[] getReturnedAttributes(int tenantID) {
        ArrayList<String> rc = new ArrayList<String>();

        List<TenantLdapAttributeMapping> attributeMappings = this.tenantLdapAttributes.getTenantLdapAttributeMapping(tenantID);
        for (TenantLdapAttributeMapping mapping : attributeMappings) {
            if (!mapping.getLdapAttributeName().equalsIgnoreCase("")) {
                rc.add(mapping.getLdapAttributeName());
            }
        }

        return rc.toArray(new String[rc.size()]);
    }
    public Map<String, String> getPortalToADAttributeMappings(String userName,int tenantID) {
        boolean isTHumbNailNeeded=false;

        Map<String,String> attrMaping=new HashMap<String, String>();

        TenantConfiguration tenantConfiguration=this.tenantService.getTenantConfiguration(tenantID);

        //if tn not enabled
        if (tenantConfiguration.getUserImage() == 0 ){
            isTHumbNailNeeded=false;
        }else if(tenantConfiguration.getUploadUserImage()==1 ){
            Member tempMember=this.member.getMemberByName(userName, tenantID);
            //importing image not needed if member  exist and  image is uploaded by user
            if(tempMember!=null && tempMember.isUserImageUploaded() ){
                isTHumbNailNeeded=false;
            }else{
                isTHumbNailNeeded=true;
            }
        }else{
            isTHumbNailNeeded=true;
        }

        List<TenantLdapAttributeMapping> attributeMappings = this.tenantLdapAttributes.getTenantLdapAttributeMapping(tenantID);
        for (TenantLdapAttributeMapping mapping : attributeMappings) {
            if (!mapping.getLdapAttributeName().equalsIgnoreCase("")) {
                if(mapping.getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo") && !isTHumbNailNeeded){
                    //skipping ldapimage import by not adding the thumbnail attribute
                    continue;
                }

                attrMaping.put(mapping.getVidyoAttributeName(), mapping.getLdapAttributeName());
            }
        }

        return attrMaping;
    }
}