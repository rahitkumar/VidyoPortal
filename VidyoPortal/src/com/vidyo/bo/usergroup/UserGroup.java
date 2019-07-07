package com.vidyo.bo.usergroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_group_id")
    @JsonProperty(value = "id")
    private int userGroupId;

    @JsonIgnore
    @Column(name = "tenant_id", updatable = false)
    private int tenantID;

    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @Size(max = 255, message = "description cannot exceed 255 characters")
    private String description;

    @JsonIgnore
    @Column(name = "creation_time", insertable=false, updatable=false)
    private Date creationTime;

    @JsonIgnore
    @Column(name = "update_time", insertable=false, updatable=false)
    private Date updateTime;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_user_group",
            joinColumns = {@JoinColumn(name="user_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private Set<Member> members = new HashSet<>();


    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_user_group",
            joinColumns = {@JoinColumn(name="user_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private Set<Room> rooms = new HashSet<>();

    public Set<Room> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<Member> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    /**
     * Scafoolding code to maintain the integrity of the relationship.
     * This will maintain the relationship from both the sides.
     *
     * @return
     */
    public void addRoom(Room room){
        this.rooms.add(room);
       // room.getUserGroups().add(this);
    }

    /**
     * Scafoolding code to maintain the integrity of the relationship.
     * This will maintain the relationship from both the sides.
     *
     * @return
     */
    public void removeRoom(Room room){
        this.rooms.remove(room);
    }

    /**
     * Scafoolding code to maintain the integrity of the relationship.
     * This will maintain the relationship from both the sides.
     *
     * @return
     */
    public void addMember(Member member){
        this.members.add(member);
    }

    /**
     * Scafoolding code to maintain the integrity of the relationship.
     * This will maintain the relationship from both the sides.
     *
     * @return
     */
    public void removeMember(Member member){
        this.members.remove(member);
    }

    /**
     * Helper method to remove all rooms.
     *
     * @return void
     */
    public void removeAllRooms(){
        this.rooms.clear();
    }

    /**
     * Helper method to remove all members.
     *
     * @return void
     */
    public void removeAllMembers(){
        this.members.clear();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userGroupId).toHashCode();
    }

    /**
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserGroup) {
            final UserGroup userGroup = (UserGroup) obj;
            return new EqualsBuilder().append(userGroupId, userGroup.userGroupId).isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "userGroupId=" + userGroupId +
                ", tenantID=" + tenantID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
