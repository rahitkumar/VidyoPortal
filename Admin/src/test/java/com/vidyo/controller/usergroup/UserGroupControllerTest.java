package com.vidyo.controller.usergroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.usergroup.UserGroup;

import com.vidyo.rest.controllers.usergroup.UserGroupController;
import com.vidyo.service.usergroup.UserGroupService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserGroupControllerTest {

    @Mock
    private UserGroupService userGroupService;

    private RestDocumentationResultHandler document;

    @Autowired
    private ObjectMapper objectMapper;


    @InjectMocks
    private UserGroupController userGroupController;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;


    @Before
    public void setup() {

        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
       // this.document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.standaloneSetup(userGroupController)
                .apply(documentationConfiguration(this.restDocumentation).uris()
                        .withHost("example.com"))
               // .alwaysDo(document("{class-name}/{method-name}/"))
                .build();

    }

    @Test
    public void testCreateUserGroup() throws Exception {
        UserGroup userGroup = new UserGroup();;
        userGroup.setName("Dev-Group");
        userGroup.setDescription("Description");
        when(userGroupService.createUserGroup(any(UserGroup.class)))
                .thenReturn(userGroup);
        this.mockMvc.perform(post("/admin/service/v1/usergroups/")
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"dev-group\", \"description\":\"test-development group\"}"))
                .andExpect(status().isCreated())
                .andDo(document("usergroup-create",
                        requestFields(
                                fieldWithPath("name").description("The name of the user-group"),
                                fieldWithPath("description").description("The description of the user-group"))));
    }

    @Test
    public void testUpdateUserGroup() throws Exception {
        UserGroup userGroup = new UserGroup();;
        userGroup.setName("Dev-Group");
        userGroup.setDescription("Description");
        when(userGroupService.updateUserGroup(anyInt(),any(UserGroup.class)))
                .thenReturn(userGroup);
        this.mockMvc.perform(put("/admin/service/v1/usergroups/{Number}",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"qa-user-group\", \"description\":\"qa-user group\"}"))
                .andExpect(status().isOk())
                .andDo(document("usergroup-update",
                        requestFields(
                                fieldWithPath("name").description("The name of the user-group"),
                                fieldWithPath("description").description("The description of the user-group"))));
    }

    @Test
    public void testUserGroupGet() throws Exception {

        List<UserGroup> userGroups = new ArrayList<>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");

        UserGroup userGroup2 = new UserGroup();
        userGroup2.setName("qa-group");
        userGroup2.setDescription("qa-group");

        userGroups.add(userGroup1);
        userGroups.add(userGroup2);

        when(userGroupService.fetchUserGroups(anyString(), anyString(), anyInt(),anyInt(),anyString(), any(String[].class))).thenReturn(userGroups);

        this.mockMvc.perform(get("/admin/service/v1/usergroups/")
                .contextPath("/admin")
                .param("name", "dev-group")
                .param("desc", "description" )
                .param("offset", "0")
                .param("limit","10")
                .param("sort","desc")
                .param("properties", new String[]{"name"})
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"development-group\",\"description\":\"development-group\",\"id\":0},{\"name\":\"qa-group\",\"description\":\"qa-group\",\"id\":0}]"))
                .andDo(document("usergroup-fetch",
                        requestParameters(
                                parameterWithName("name").description("user group name"),
                                parameterWithName("desc").description("description of user group"),
                                parameterWithName("offset").description("offset page"),
                                parameterWithName("limit").description("number of records per page"),
                                parameterWithName("sort").description("sort by asc or desc"),
                                parameterWithName("properties").description("sort properties name")
                        ),

                        responseFields(
                                fieldWithPath("[]").description("An array of user groups"),
                                fieldWithPath("[].name").description("UserGroup name"),
                                fieldWithPath("[].description").description("UserGroup description"),
                                fieldWithPath("[].id").description("UserGroup Id")
                        )
                        )
                );

    }

    @Test
    public void testDeleteUserGroup() throws Exception {
        Mockito.doNothing().when(userGroupService).deleteUserGroup(anyInt());

        this.mockMvc.perform(delete("/admin/service/v1/usergroups/{Number}",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("usergroup-delete"));

    }

    @Test
    public void testDeleteUserGroups() throws Exception {
        Mockito.doNothing().when(userGroupService).deleteUserGroups(any(Set.class));

        this.mockMvc.perform(delete("/admin/service/v1/usergroups/")
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2,3]")
                )
                .andExpect(status().isOk())
                .andDo(document("multiple-delete"));

    }

    @Test
    public void testAttachRoomsToGroups() throws Exception {

        Set<Integer> userGroupIds=new HashSet<>();
        userGroupIds.add(1);
        userGroupIds.add(2);
        userGroupIds.add(3);
        userGroupIds.add(4);
        Mockito.doNothing().when(userGroupService).updateGroupsForMember(3, userGroupIds, UserGroupService.Operation.UPDATE);

        this.mockMvc.perform(put("/admin/service/v1/rooms/{Number}/usergroups/",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2,3,4]"))
                .andExpect(status().isOk())
                .andDo(document("attach-usergroups-to-room"));
    }

    @Test
    public void testDetachRoomsToGroups() throws Exception {

        Set<Integer> userGroupIds=new HashSet<>();
        userGroupIds.add(1);
        userGroupIds.add(2);
        userGroupIds.add(3);
        userGroupIds.add(4);
        Mockito.doNothing().when(userGroupService).updateGroupsForMember(3, userGroupIds, UserGroupService.Operation.DELETE);

        this.mockMvc.perform(put("/admin/service/v1/rooms/{Number}/usergroups/",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2,3,4]"))
                .andExpect(status().isOk())
                .andDo(document("detach-usergroups-from-room"));
    }

	@Test
	public void testFetchUserGroupsAttachedToRoom() throws Exception {

		Set<UserGroup> userGroups = new HashSet<UserGroup>();
		UserGroup userGroup1 = new UserGroup();
		userGroup1.setName("development-group");
		userGroup1.setDescription("development-group");
		userGroups.add(userGroup1);

		when(userGroupService.fetchUserGroupsAttachedToRoom(3)).thenReturn(userGroups);

		this.mockMvc.perform(get("/admin/service/v1/rooms/{id}/usergroups/", 3)
                .contextPath("/admin")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"name\":\"development-group\",\"description\":\"development-group\",\"id\":0}]"))
				.andDo(document("fetch-usergroups-attached-to-room"));
	}

    @Test
    public void testAttachMembersToGroups() throws Exception {
        Set<Integer> userGroupIds=new HashSet<>();
        userGroupIds.add(1);
        userGroupIds.add(2);
        userGroupIds.add(3);
        userGroupIds.add(4);
        Mockito.doNothing().when(userGroupService).updateGroupsForMember(3, userGroupIds, UserGroupService.Operation.UPDATE);

        this.mockMvc.perform(put("/admin/service/v1/members/{Number}/usergroups/",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2,3,4]"))
                .andExpect(status().isOk())
                .andDo(document("attach-usergroups-to-member"));
    }

    @Test
    public void testDetachMembersFromGroups() throws Exception {

        Set<Integer> userGroupIds=new HashSet<>();
        userGroupIds.add(1);
        userGroupIds.add(2);
        userGroupIds.add(3);
        userGroupIds.add(4);
        Mockito.doNothing().when(userGroupService).updateGroupsForMember(3, userGroupIds, UserGroupService.Operation.DELETE);

        this.mockMvc.perform(put("/admin/service/v1/members/{Number}/usergroups/",3)
                .contextPath("/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1,2,3,4]"))
                .andExpect(status().isOk())
                .andDo(document("detach-usergroups-from-member"));
    }

	@Test
	public void testFetchUserGroupsAttachedToMember() throws Exception {

		Set<UserGroup> userGroups = new HashSet<UserGroup>();
		UserGroup userGroup1 = new UserGroup();
		userGroup1.setName("development-group");
		userGroup1.setDescription("development-group");
		userGroups.add(userGroup1);

		when(userGroupService.fetchUserGroupsAttachedToMember(3)).thenReturn(userGroups);

		this.mockMvc.perform(get("/admin/service/v1/members/{id}/usergroups/", 3)
                .contextPath("/admin")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("[{\"name\":\"development-group\",\"description\":\"development-group\",\"id\":0}]"))
				.andDo(document("fetch-usergroups-attached-to-member"));
	}
}
