SHELL = /bin/sh

#******* Target name of execuatbles or library (.a) to be built ***********

BUILD_TARGET := drmgr


#******* Build options that can be overriden on the command line ************

# Build type: release debug
BUILD_TYPE := release

# Paths
SDK_DIR := ../../../../SDK/
SECURITY_KERNEL_DIR := ../../../../SecurityKernel
SECURITY_KERNEL_LIB_DIR := $(SECURITY_KERNEL_DIR)/lib/linux/x86_64
OPENSSL_VERSION := openssl-1.0.1q-fips-2.0.10
OPENSSL_DIR := ../../../../Frameworks/Linux/OpenSSL/ubuntu/$(OPENSSL_VERSION)/x86_64-trusty/opt/vidyo/app/openssl
COMMON_DIR := ../../../../../HEAD/Products/VC2/Common
FIPS_LINKER_DIR := $(COMMON_DIR)

# Flags
ADDITIONAL_COMPILE_FLAGS :=
ADDITIONAL_COMPILE_CFLAGS :=
ADDITIONAL_COMPILE_CPPFLAGS :=
ADDITIONAL_LINK_FLAGS :=


#******* Target specific build information *********

# Source file directories (automatically put in include path)
SOURCE_DIRS := .

# C and C++ Source files (not header files)
SOURCE_FILES := drmgr.cpp httpclient.cpp httptransport.cpp log.cpp statemachine.cpp startup.cpp \
                pre_maintenance.cpp maintenance.cpp pre_standby.cpp standby.cpp pre_active.cpp active.cpp \
                dns_watcher.cpp config.cpp param.cpp utils.cpp register.cpp eventhandler.cpp \
                cluster_fqdn_ip.cpp pre_active_script.cpp admin.cpp pre_standby_script.cpp \
					 portal_process_watcher.cpp check_process_script.cpp

# Target additional build flags
TARGET_COMPILE_FLAGS :=
TARGET_COMPILE_CFLAGS :=
TARGET_COMPILE_CPPFLAGS := 
TARGET_LINK_FLAGS :=

# Target external include directories
EXT_INCLUDE_DIRS := $(SDK_DIR) $(OPENSSL_DIR)/include 

# Target external library directories
EXT_LIB_DIRS := $(SDK_DIR) $(SECURITY_KERNEL_LIB_DIR) $(COMMON_DIR) $(OPENSSL_DIR)/lib

# Target external libraries (including OS libraries, in proper order)
EXT_LIBS := 
EXT_LIBS +=  \
             LmiXml \
             LmiWeb \
        LmiCsTls \
        LmiCsUtils \
        LmiTransport \
        LmiXml \
        LmiOs \
        LmiUtils \
        LmiSecurityFips


EXT_LIBS += ssl crypto
EXT_LIBS += m anl dl stdc++ rt


#******* Build and Link flags (do not modify) *********

# Common flags (C and C++)
BUILD_FLAGS := -DLMI_HAVE_LMI_CONFIG_AUTOCONF_H
BUILD_FLAGS += -D_GNU_SOURCE -D_REENTRANT -pthread
BUILD_FLAGS += -msse2 -fstack-protector -ffunction-sections -fdata-sections
BUILD_FLAGS += -Wall -Wextra -Wno-unused-parameter -Wno-comment
BUILD_FLAGS += $(TARGET_COMPILE_FLAGS) $(ADDITIONAL_COMPILE_FLAGS)

# C specific flags
BUILD_CFLAGS :=  -Wc++-compat -Wdeclaration-after-statement $(TARGET_COMPILE_CFLAGS) $(ADDITIONAL_COMPILE_CFLAGS)

# C++ specific flags
BUILD_CPPFLAGS := $(TARGET_COMPILE_CPPFLAGS) $(ADDITIONAL_COMPILE_CPPFLAGS)

# Linker flags
LINK_FLAGS := -pthread -Wl,-z,norelro
LINK_FLAGS += $(TARGET_LINK_FLAGS) $(ADDITIONAL_LINK_FLAGS)


#******* Build rules (do not modify) *******

CC = gcc
CXX = g++
AR = ar
RANLIB = ranlib
FIPS_LINKER = $(FIPS_LINKER_DIR)/FipsLinker.sh

LIB_TARGETS = $(filter %.a,$(BUILD_TARGET))
PROGRAM_TARGETS = $(filter-out %.a,$(BUILD_TARGET))

SOURCE_CFILES = $(filter %.c,$(SOURCE_FILES))
SOURCE_CPPFILES = $(filter %.cpp,$(SOURCE_FILES))

OBJECT_CFILES = $(SOURCE_CFILES:%.c=%.o)
OBJECT_CPPFILES = $(SOURCE_CPPFILES:%.cpp=%.o)

CFLAGS = $(BUILD_FLAGS) $(BUILD_CFLAGS) -I. -I ../include  $(SOURCE_DIRS:%=-I%) $(EXT_INCLUDE_DIRS:%=-I%)
CXXFLAGS += $(BUILD_FLAGS) $(BUILD_CPPFLAGS) -I.  -I ../include $(SOURCE_DIRS:%=-I%) $(EXT_INCLUDE_DIRS:%=-I%)
ARFLAGS = cru
LDFLAGS += $(LINK_FLAGS) -L. $(EXT_LIB_DIRS:%=-L%) 
LDFLAGS += 


# Add debug and/or optimization flags
ifeq ($(BUILD_TYPE), debug)
CFLAGS += -g
CXXFLAGS += -g
LDLAGS += -g
else
CFLAGS += -g -O2
CXXFLAGS += -g -O2
LDFLAGS += -g -O2
endif

# Generate dependency files (.d) when compiling
CFLAGS += -MD -MP
CXXFLAGS += -MD -MP

# Export info needed by FIPS Linker script
export FIPS_OPENSSL_DIR=$(OPENSSL_DIR)
export FIPS_SECURITY_KERNEL_DIR=$(SECURITY_KERNEL_DIR)

# no target means "all"
.PHONY: default
default: all

# Speed up make
Makefile: ;
%.mak: ;
%.c: ;
%.h: ;
%.d: ;

# Standard all target
.PHONY: all
all: $(BUILD_TARGET)
	g++ -o dr_admin dr_admin.cpp
	@echo Build complete.
	scp drmgr  eric@172.16.44.163:/tmp/
	scp drmgr  eric@172.16.44.157:/tmp/
	scp rundr.sh  eric@172.16.44.163:/tmp/
	scp dr_admin eric@172.16.44.163:/tmp/

# Clean up
.PHONY: clean
clean:
	@echo Cleaning all source directories and the top level directory.
	@rm -f $(LIB_TARGETS) $(PROGRAM_TARGETS) *.[aiodt] $(foreach dir,$(SOURCE_DIRS), $(dir)/*.[odit])

# Compile C files
$(OBJECT_CFILES): %.o : %.c Makefile
	$(CC) $(CFLAGS) -c $< -o $@

# Compile C++ files
$(OBJECT_CPPFILES): %.o : %.cpp Makefile
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Create Libraries
$(LIB_TARGETS): $(OBJECT_CFILES) $(OBJECT_CPPFILES)
	-rm -f $@
	$(AR) $(ARFLAGS) $@ $(OBJECT_CFILES) $(OBJECT_CPPFILES)
	$(RANLIB) $@

# Link applications
$(PROGRAM_TARGETS): $(OBJECT_CFILES) $(OBJECT_CPPFILES)
	-rm -f $@
	$(FIPS_LINKER) $(LDFLAGS) $(OBJECT_CFILES) $(OBJECT_CPPFILES) $(EXT_LIBS:%=-l%) -o $@

# Pull in dependency files (if they exist)
-include $(SOURCE_CFILES:%.c=%.d) $(SOURCE_CPPFILES:%.cpp=%.d)
