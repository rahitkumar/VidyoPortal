bin_PROGRAMS = clusterip cip_admin
cip_admin_SOURCES = cluster_admin.cpp miscutils.cpp csocket.cpp
cip_admin_CPPFLAGS = -g -Wall -I ../include
cip_admin_LDADD = -lpthread

clusterip_SOURCES = clusterip.cpp clusterinfo.cpp params.cpp reqmsg.cpp pinger.cpp statemachine.cpp miscutils.cpp admin.cpp csocket.cpp cluster_signals.cpp checksum.cpp


clusterip_CPPFLAGS = -g -Wall -I ../include
clusterip_LDADD = -lpthread
