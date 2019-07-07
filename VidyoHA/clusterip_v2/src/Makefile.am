bin_PROGRAMS = clusterip cip_admin

clusterip_SOURCES = active_nolink_nostandby.cpp \
                    active_nolink_standby_nolink.cpp \
                    active_nostandby.cpp \
                    active_with_standby.cpp \
                    active_with_standby_nolink.cpp \
                    clusterip.cpp \
                    dbversion.cpp \
                    negotiate.cpp \
                    negotiate_nolink.cpp \
                    reboot.cpp \
                    standby.cpp \
                    standby_nolink.cpp \
                    miscutils.cpp \
                    reqmsg.cpp \
                    params.cpp \
                    link_watcher.cpp \
                    clusterinfo.cpp \
                    cluster_signals.cpp \
                    admin.cpp \
                    maintenance.cpp \
                    statemachine.cpp

cip_admin_SOURCES = cluster_admin.cpp #miscutils.cpp 
cip_admin_CPPFLAGS = -g -Wall -I ../include
cip_admin_LDADD = -lpthread


clusterip_CPPFLAGS = -g -Wall -I ../include
clusterip_LDADD = -lpthread
