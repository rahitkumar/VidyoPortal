Ext.define('SuperApp.store.settings.MenuTree', {
    extend : 'Ext.data.TreeStore',
    root : {
        expanded : true,
        children : [{
            text : "Platform Network Settings",
            id : "pns",
            leaf : true
        }, {
            text : "System License",
            id : "sl",
            leaf : true
        }, {
            text : "Upload Endpoint Software",
            id : "ues",
            leaf : true
        }, {
            text : "Maintenance",
            id : "maintenance",
            leaf : false,
            children : [{
                text : "Database",
                id : "db",
                leaf : true
            }, {
                text : "System Upgrade",
                id : "sysup",
                leaf : true
            }, {
                text : "System Restart",
                id : "sysrst",
                leaf : true
            }, {
                text : "CDR Access",
                id : "cdr",
                leaf : true
            }, {
                text : "Audit Logs",
                id : "auditlog",
                leaf : true
            }, {
                text : "Diagnostics",
                id : "diagnostics",
                leaf : true
            }, {
                text : "Syslog",
                id : "syslog",
                leaf : true
            }, {
                text : "External Database",
                id : "extdb",
                leaf : true
            }, {
                text : "Status Notify",
                id : "statusnotify",
                leaf : true
            }, {
                text : "Events Notify",
                id : "eventsnotify",
                leaf : true
            }]
        }, {
            text : "Super accounts",
            id : "superacc",
            leaf : true
        }, {
            text : "Customization",
            id : "cust",
            leaf : false,
            children : [{
                text : "About Info",
                id : "aboutinfo",
                leaf : true
            }, {
                text : "Support Info",
                id : "supportinfo",
                leaf : true
            }, {
                text : "Notifications",
                id : "notifications",
                leaf : true
            }, {
                text : "Invite Text",
                id : "invitetext",
                leaf : true
            }, {
                text : "Customized Logos",
                id : "custlogos",
                leaf : true
            }, {
                text : "Guides Properties",
                id : "guidesprop",
                leaf : true
            }, {
                text : "Banners",
                id : "banners",
                leaf : true
            }, {
                text : "Passwords",
                id : "passwords",
                leaf : true
            }]
        }, {
            text : "Security",
            id : "security",
            leaf : false,
            children : [{
                text : "SSL Private Key",
                id : "sslpk",
                leaf : true
            }, {
                text : "SSL CSR",
                id : "sslcsr",
                leaf : true
            }, {
                text : "Server Certificate",
                id : "servercert",
                leaf : true
            }, {
                text : "Server CA Certificates",
                id : "servcacert",
                leaf : true
            }, {
                text : "Applications",
                id : "apps",
                leaf : true
            }, {
                text : "Advanced",
                id : "advanced",
                leaf : true
            }]
        }, {
            text : "Scheduled Room",
            id : "schedroom",
            leaf : true
        }, {
            text : "Global Feature Settings",
            id : "gfs",
            leaf : false,
            children : [{
                text : "VidyoWeb",
                id : "vidyoweb",
                leaf : true
            }, {
                text : "VidyoMobile",
                id : "vidyomobile",
                leaf : true
            }, {
                text : "VidyoDesktop",
                id : "vidyodesktop",
                leaf : true
            }, {
                text : "Search Options",
                id : "searchoptions",
                leaf : true
            }, {
                text : "Inter-Portal Communication",
                id : "ipc",
                leaf : true
            }, {
                text : "TLS",
                id : "tls",
                leaf : true
            }, {
                text : "Chat",
                id : "chat",
                leaf : true
            },{
                text : "User Attributes",
                id : "userAttributes",
                leaf : true
            },{
                text : "Custom Roles",
                id : "customRoles",
                leaf : true
            },{
                text : "Epic Integration",
                id :   "enableEpicIntegration",
                leaf : true
            },{      
                text : "TytoCare Integration",
                id :   "enableTytoCareIntegration",
                leaf : true
            },{            
                text : "Installed Gmail Plugin",
                id : "gmail",
                leaf : true
            }]
        }]
    },
    autoLoad : false
});
