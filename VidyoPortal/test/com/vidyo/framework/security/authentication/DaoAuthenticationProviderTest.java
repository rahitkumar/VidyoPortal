package com.vidyo.framework.security.authentication;

import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DaoAuthenticationProviderTest {

    private static final int TENANT_ID = 328;

    @Mock
    private ISystemService systemService;

    private DaoAuthenticationProvider provider;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this.getClass());
        TenantContext.setTenantId(TENANT_ID);
        provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new CustomSHAPasswordEncoder());
        provider.setSystem(systemService);
    }

    @Test
    public void testAdditionalAuthenticationChecks() {

        when(systemService.getToRoles()).thenReturn(Collections.emptyList());
        AuthenticationConfig authCfg = mock(AuthenticationConfig.class);
        when(systemService.getAuthenticationConfig(eq(TENANT_ID))).thenReturn(authCfg);

        String pak2 = "HFr8Yi8mYq2zvJbPJ42eLsiB/1YKDrnhZlG4sl1pXqwEof3lbnAnsA6ggWaPgtA=";
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken("testUser",
                pak2);

        UserDetails  u = new VidyoUserDetails("test",
            pak2/*password*/,
                "bak",
                    "sak", true, true, new GrantedAuthority[]{
            new SimpleGrantedAuthority("Nornal")
        }, true, 3228,
                5, 5, "test", "test@testo.com", pak2, null);
        provider.additionalAuthenticationChecks(u, t);
    }

    @Test
    public void testAdditionalAuthenticationChecks_testbak() {

        when(systemService.getToRoles()).thenReturn(Collections.emptyList());
        AuthenticationConfig authCfg = mock(AuthenticationConfig.class);
        when(systemService.getAuthenticationConfig(eq(TENANT_ID))).thenReturn(authCfg);


        String bak = "0e459763d69a8ea74f3ad727a8f4602819e18705";
        UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken("testUser",
                "F+bXOc0XNfpfW0atDGAhYw==");

        UserDetails  u = new VidyoUserDetails("test",
                ""/*password*/,
                bak,
                "sak", true, true, new GrantedAuthority[]{
                new SimpleGrantedAuthority("Nornal")
        }, true, 3228,
                5, 5, "test", "test@testo.com", "", null);
        provider.additionalAuthenticationChecks(u, t);
    }
}
