package com.vidyo.services;

import com.vidyo.bo.Member;
import com.vidyo.db.endpoints.EndpointDao;
import com.vidyo.dto.GetVisitResponse;
import com.vidyo.dto.VisitRequest;
import com.vidyo.dto.tyto.TytoCreateClinicianRq;
import com.vidyo.dto.tyto.TytoCreateVisitRequest;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class VidyoTytoBridgeTest {

    private static final int REF_TENANT_ID = 5;
    private static final String REF_ENDPOINT_GUID = "zzz-XXXX-yyyy-999";
    private static final String REF_STATION_GUID = "zzz-yyyy-1111";
    private static final String REFERENCE_TYTO_CLINICIAN_ID = "63@5_D";
    
    private static final String CLINICIAN_REMOTE_ADDR = "127.0.0.1";

    @Mock
    private EndpointDao endpointDao;

    @Mock
    private IMemberService memberService;

    @Mock
    private IUserService userService;

    private VidyoTytoBridge apiBridge;

    @Before
    public void setup() {
        initMocks(this.getClass());
        apiBridge = new VidyoTytoBridge(endpointDao, memberService, userService);
    }

    @Test
    public void createVisit_success() {
        when(endpointDao.findPublicIpAndMemberId(eq(REF_TENANT_ID),
                eq(Arrays.asList(REF_ENDPOINT_GUID, REF_STATION_GUID)))
        ).then(invocation -> {
            List<Map<String, Object>> results = new ArrayList<>();
            Map<String, Object> clinicianData = new HashMap<>();
            clinicianData.put("endpointPublicIPAddress", "172.24.249.169");
            clinicianData.put("memberID", 63L);
            clinicianData.put("endpointGUID", REF_ENDPOINT_GUID);
            clinicianData.put("memberType", "D");

            Map<String, Object> stationData = new HashMap<>();
            stationData.put("endpointPublicIPAddress", "172.24.248.42");
            stationData.put("memberID", 144L);
            stationData.put("endpointGUID", REF_STATION_GUID);
            stationData.put("memberType", "G");

            results.add(clinicianData);
            results.add(stationData);
            return results;
        });

        TytoCreateVisitRequest tytoRequest = apiBridge.createVisit(REF_TENANT_ID,
                new VisitRequest("v123", REF_ENDPOINT_GUID, REF_STATION_GUID), CLINICIAN_REMOTE_ADDR);

        assertEquals(CLINICIAN_REMOTE_ADDR, tytoRequest.getClinicianRemoteAddress());
        assertEquals(REFERENCE_TYTO_CLINICIAN_ID, tytoRequest.getClinicianIdentifier());
        assertEquals("172.24.248.42", tytoRequest.getStationRemoteAddress());
        assertEquals(REF_STATION_GUID, tytoRequest.getStationIdentifier());
    }

    @Test(expected = TytoProcessingException.class)
    public void createVisit_member_not_found() {
        when(endpointDao.findPublicIpAndMemberId(eq(REF_TENANT_ID), any())).thenReturn(new ArrayList<>());

        apiBridge.createVisit(REF_TENANT_ID,
                new VisitRequest("v123", REF_ENDPOINT_GUID, "GUID1"), CLINICIAN_REMOTE_ADDR);
    }

   @Test
    public void testBuilClinicianIDForTyto() {
        String result = VidyoTytoBridge.translateVidyoToTytoId(REF_TENANT_ID, 63L, "D");
        assertEquals(REFERENCE_TYTO_CLINICIAN_ID, result);
    }

    @Test
    public void testCleanUpGetVisitResponse_success() {
        String randomUUID = UUID.randomUUID().toString();
        GetVisitResponse vr = new GetVisitResponse();
        vr.setClinicianIdentifier("123@123");
        when(userService.getLinkedEndpointGUID(anyInt())).thenReturn(randomUUID);
        GetVisitResponse sanitized = apiBridge.cleanUpGetVisitResponse(vr);
        assertEquals(randomUUID, sanitized.getClinicianIdentifier());
    }

    @Test
    public void testCleanUpGetVisitResponse_invalidID() {
        String invalidClinicianID = "123123";
        GetVisitResponse vr = new GetVisitResponse();
        vr.setClinicianIdentifier(invalidClinicianID);
        GetVisitResponse sanitized = apiBridge.cleanUpGetVisitResponse(vr);
        assertEquals(invalidClinicianID, sanitized.getClinicianIdentifier());
    }

    @Test(expected = TytoProcessingException.class)
    public void findPublicIpAndMemberId_no_guid() {

        when(apiBridge.findPublicIpAndMemberId(eq(REF_TENANT_ID), eq(asList(REF_ENDPOINT_GUID))))
                .thenReturn(new ArrayList<>());
       apiBridge.findPublicIpAndMemberId(REF_TENANT_ID, REF_ENDPOINT_GUID);
    }

    @Test
    public void testTruncateFirstAndLastName() {
        Member m = new Member();
        m.setMemberName("Highmountain Tauren");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("Highmountain", rq.getFirstName());
        assertEquals("Tauren", rq.getLastName());
    }

    @Test
    public void testTruncateFirstAndLastName_multiple_whitespaces() {
        Member m = new Member();
        m.setMemberName("Highmountain Tauren XXL");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("Highmountain", rq.getFirstName());
        assertEquals("Tauren", rq.getLastName());
    }


    @Test
    public void testTruncateFirstAndLastName_no_whitespace() {
        Member m = new Member();
        m.setMemberName("HighmountainTauren");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("HighmountainTauren", rq.getFirstName());
        assertEquals("HighmountainTauren", rq.getLastName());
    }

    @Test
    public void testTruncate_exceeds() {
        Member m = new Member();
        m.setMemberName("Highmountain12345678901 TTTTTTTTTTTTTTTTTTTTTauren1234567");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("Highmountain12345678", rq.getFirstName());
        assertEquals("TTTTTTTTTTTTTTTTTTTTTauren1234", rq.getLastName());
    }

    @Test
    public void testTruncate_specialChars() {
        Member m = new Member();
        m.setMemberName("Highmountain Ta@3uren");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("Highmountain", rq.getFirstName());
        assertEquals("Ta3uren", rq.getLastName());
    }

    @Test
    public void testTruncate_handleQuote() {
        Member m = new Member();
        m.setMemberName("O'Nill Sp");
        m.setEmailAddress("test@test.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("O'Nill", rq.getFirstName());
        assertEquals("Sp", rq.getLastName());
    }

    @Test
    public void testTruncate_email() {

        Member m = new Member();
        m.setMemberName("Highmountain Ta3uren");
        m.setEmailAddress("veerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrry_longgggggggggggggggggggggggggg@email.com");
        when(memberService.getMember(eq(REF_TENANT_ID), eq(5))).thenReturn(m);
        TytoCreateClinicianRq rq = apiBridge.createClinicianRq(REF_TENANT_ID, 5L, "5@5_G");
        assertEquals("email_unknown@dummy-domain.net", rq.getEmail());
    }
}
