
/**
 * VidyoPortalSuperServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.portal.superapi;
    /**
     *  VidyoPortalSuperServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalSuperServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Save all tenant and user information about the
				entire system, and provides the saved backup file name.
                                    * @param saveDBRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.SaveDBResponse saveDb
                (
                  com.vidyo.portal.superapi.SaveDBRequest saveDBRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Save all tenant and user information about the
				entire system, and provides the saved backup URL for download.
                                    * @param backupDbRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.BackupDbResponse backupDb
                (
                  com.vidyo.portal.superapi.BackupDbRequest backupDbRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Allows for adding an additional client version at
				the super level. This
				new client version will be seen by all tenants.
				The new client
				version will not be activated.
                                    * @param addClientVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ExternalModeFaultException : 
         */

        
                public com.vidyo.portal.superapi.AddClientVersionResponse addClientVersion
                (
                  com.vidyo.portal.superapi.AddClientVersionRequest addClientVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException,ExternalModeFaultException;
        
         
        /**
         * Auto generated method signature
         * Provides the list of available service
				components. If the type is not specified, all lists will be
				provided. If the type is specified, only the list associated with
				this type will be provided
                                    * @param getServiceComponentsDataRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetServiceComponentsDataResponse getServiceComponentsData
                (
                  com.vidyo.portal.superapi.GetServiceComponentsDataRequest getServiceComponentsDataRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Get the tenant details based on the tenant ID.
                                    * @param getTenantDetailsRequest
             * @throws GeneralFaultException : 
             * @throws InvalidTenantFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetTenantDetailsResponse getTenantDetails
                (
                  com.vidyo.portal.superapi.GetTenantDetailsRequest getTenantDetailsRequest
                 )
            throws GeneralFaultException,InvalidTenantFaultException;
        
         
        /**
         * Auto generated method signature
         * Provides license data. If a tenant is not
				specified, the license parameters should be provided for all tenants
				as a Total number
                                    * @param getLicenseDataRequest
             * @throws NotLicensedFaultException : 
             * @throws GeneralFaultException : 
             * @throws NotAuthorizedFaultException : 
             * @throws InvalidTenantFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetLicenseDataResponse getLicenseData
                (
                  com.vidyo.portal.superapi.GetLicenseDataRequest getLicenseDataRequest
                 )
            throws NotLicensedFaultException,GeneralFaultException,NotAuthorizedFaultException,InvalidTenantFaultException;
        
         
        /**
         * Auto generated method signature
         * Deleting an existing tenant from the system. The
				default tenant can not be deleted.
                                    * @param deleteTenantRequest
             * @throws GeneralFaultException : 
             * @throws InvalidTenantFaultException : 
         */

        
                public com.vidyo.portal.superapi.DeleteTenantResponse deleteTenant
                (
                  com.vidyo.portal.superapi.DeleteTenantRequest deleteTenantRequest
                 )
            throws GeneralFaultException,InvalidTenantFaultException;
        
         
        /**
         * Auto generated method signature
         * The objective of this API is to  enable/disable custom role.
                                    * @param setCustomRoleRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.SetCustomRoleResponse setCustomRole
                (
                  com.vidyo.portal.superapi.SetCustomRoleRequest setCustomRoleRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * The objective of this API is to set whether chat
				is available on the
				VidyoPortal and the default values for private /
				public chat on the
				newly created tenants.
				Once made unavailable,
				tenant admins will not be able to enable chat on
				the VidyoPortal and
				Vidyo clients joining the conferences on this
				VidyoPortal won't be
				able to engage in chat conversations.
                                    * @param setChatStateSuperRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.SetChatStateSuperResponse setChatStateSuper
                (
                  com.vidyo.portal.superapi.SetChatStateSuperRequest setChatStateSuperRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Retrieve the pool of routers.
                                    * @param getRouterPoolListRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetRouterPoolListResponse getRouterPoolList
                (
                  com.vidyo.portal.superapi.GetRouterPoolListRequest getRouterPoolListRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Add and / or remove a list of allowed IPC
				domains. The user can add and remove IPC domains at the same time.
                                    * @param setIpcDomainsRequest
             * @throws GeneralFaultException : 
             * @throws IncorrectIpcAccessLevelFaultException : 
         */

        
                public com.vidyo.portal.superapi.SetIpcDomainsResponse setIpcDomains
                (
                  com.vidyo.portal.superapi.SetIpcDomainsRequest setIpcDomainsRequest
                 )
            throws GeneralFaultException,IncorrectIpcAccessLevelFaultException;
        
         
        /**
         * Auto generated method signature
         * Set the login and welcome banner parameters on
				the VidyoPortal tenants and enable / disable the feature altogether.
                                    * @param setLoginAndWelcomeBannerRequest
             * @throws BannerTextFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.SetLoginAndWelcomeBannerResponse setLoginAndWelcomeBanner
                (
                  com.vidyo.portal.superapi.SetLoginAndWelcomeBannerRequest setLoginAndWelcomeBannerRequest
                 )
            throws BannerTextFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * List all network components
                                    * @param listNetworkComponentsRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.ListNetworkComponentsResponse listNetworkComponents
                (
                  com.vidyo.portal.superapi.ListNetworkComponentsRequest listNetworkComponentsRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Provide the level of access that user has to view
				and modify allowed IPC domains.
                                    * @param getIpcAccessControlRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetIpcAccessControlResponse getIpcAccessControl
                (
                  com.vidyo.portal.superapi.GetIpcAccessControlRequest getIpcAccessControlRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Create a new tenant based on the information
				provided below.
                                    * @param createTenantRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ExistingTenantFaultException : 
         */

        
                public com.vidyo.portal.superapi.CreateTenantResponse createTenant
                (
                  com.vidyo.portal.superapi.CreateTenantRequest createTenantRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ExistingTenantFaultException;
        
         
        /**
         * Auto generated method signature
         * Get a list of database backups
                                    * @param listDbRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.ListDbResponse listDb
                (
                  com.vidyo.portal.superapi.ListDbRequest listDbRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Provide the location tags available on the
				portal.
                                    * @param getLocationTagsRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetLocationTagsResponse getLocationTags
                (
                  com.vidyo.portal.superapi.GetLocationTagsRequest getLocationTagsRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Delete saved database backup from the disk.
                                    * @param deleteDBRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.DeleteDBResponse deleteDb
                (
                  com.vidyo.portal.superapi.DeleteDBRequest deleteDBRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Provides the list of all tenants in the system
				with the following filters. All filters are optional and if a filter
				is not provided the query will use the defaults, which are sorting
				in a descending order all the tenants by the tenant names.
                                    * @param listTenantsRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.ListTenantsResponse getListOfTenants
                (
                  com.vidyo.portal.superapi.ListTenantsRequest listTenantsRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Update an existing tenant by the tenant ID. Each
				optional parameter that is provided aside from the tenant ID will
				overwrite the existing parameters.
                                    * @param updateTenantRequest
             * @throws NotLicensedFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ExistingTenantFaultException : 
             * @throws InvalidTenantFaultException : 
         */

        
                public com.vidyo.portal.superapi.UpdateTenantResponse updateTenant
                (
                  com.vidyo.portal.superapi.UpdateTenantRequest updateTenantRequest
                 )
            throws NotLicensedFaultException,InvalidArgumentFaultException,GeneralFaultException,ExistingTenantFaultException,InvalidTenantFaultException;
        
         
        /**
         * Auto generated method signature
         * Get the list of all allowed or blocked IPC
				domains that are stored the database.
                                    * @param getIpcDomainListRequest
             * @throws GeneralFaultException : 
             * @throws IncorrectIpcAccessLevelFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetIpcDomainListResponse getIpcDomainList
                (
                  com.vidyo.portal.superapi.GetIpcDomainListRequest getIpcDomainListRequest
                 )
            throws GeneralFaultException,IncorrectIpcAccessLevelFaultException;
        
         
        /**
         * Auto generated method signature
         * The objective of this API is to get the
				configured value for chat
				availability on the VidyoPortal as well as
				the default values for
				private and public chat for newly created
				tenants.
                                    * @param getChatStateSuperRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.GetChatStateSuperResponse getChatStateSuper
                (
                  com.vidyo.portal.superapi.GetChatStateSuperRequest getChatStateSuperRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Define IPC control access (Allow / Block) for
				Tenant and System levels.
                                    * @param setIpcAccessControlRequest
             * @throws InvalidArgumentFaultException : 
             * @throws MissingArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.portal.superapi.SetIpcAccessControlResponse setIpcAccessControl
                (
                  com.vidyo.portal.superapi.SetIpcAccessControlRequest setIpcAccessControlRequest
                 )
            throws InvalidArgumentFaultException,MissingArgumentFaultException,GeneralFaultException;
                
                /**
                 * Auto generated method signature
                 * The objective of this API is to set log aggregation server FQDN on the VidyoPortal.
                                             * @param setLogAggregationServerRequest 
                     * @return setLogAggregationServerResponse 
                     * @throws InvalidArgumentFaultException 
                     * @throws GeneralFaultException 
                     * @throws NotLicensedFaultException 
                 */
                
                         public com.vidyo.portal.superapi.SetLogAggregationServerResponse setLogAggregationServer
                          (
                          com.vidyo.portal.superapi.SetLogAggregationServerRequest setLogAggregationServerRequest
                          )
                    throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;
                         
             /**
              * Auto generated method signature
              * Set mobile login mode at system level , enforce all tenants to use it.
                                          * @param setMobileLoginModeRequest 
                  * @return setMobileLoginModeResponse 
                  * @throws InvalidArgumentFaultException 
                  * @throws GeneralFaultException 
                  * @throws NotLicensedFaultException 
              */
             
                      public com.vidyo.portal.superapi.SetMobileLoginModeResponse setMobileLoginMode
                       (
                       com.vidyo.portal.superapi.SetMobileLoginModeRequest setMobileLoginModeRequest
                       )
                 throws InvalidArgumentFaultException,GeneralFaultException,NotLicensedFaultException;                         
        
         }
    