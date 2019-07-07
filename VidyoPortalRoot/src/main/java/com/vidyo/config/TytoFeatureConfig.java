package com.vidyo.config;

import com.vidyo.db.endpoints.EndpointDao;
import com.vidyo.service.*;
import com.vidyo.services.TytoRemoteAPIService;
import com.vidyo.services.VidyoTytoBridge;
import com.vidyo.validators.TytoIdValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TytoFeatureConfig {

    @Value("${tyto_read_operation_timeout:20}")
    private int tytoConnectionTimeoutValue;

    @Bean
    public TytoIdValidator createValidator() {
        return new TytoIdValidator();
    }

    @Bean
    public TytoRemoteAPIService createTytoRemoteApiService(ITenantService tenantService,
                                                           ExtIntegrationService extIntegrationService,
                                                           CryptService cryptService,
                                                           VidyoTytoBridge apiBridge,
                                                           EndpointDao endpointDao) {
        return new TytoRemoteAPIService(tenantService, extIntegrationService, cryptService, apiBridge, tytoRestTemplate(), endpointDao);
    }

    @Bean
    public VidyoTytoBridge createVidyoTytoBridgeService(EndpointDao endpointDao,
                                                        IMemberService memberService,
                                                        IUserService userService) {
        return new VidyoTytoBridge(endpointDao, memberService, userService);
    }

    private RestTemplate tytoRestTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(tytoConnectionTimeoutValue * 1000); //time in seconds
        BufferingClientHttpRequestFactory bufferingFactory = new BufferingClientHttpRequestFactory(httpRequestFactory);
        return new RestTemplate(bufferingFactory);
    }
}