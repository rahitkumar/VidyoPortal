#!/bin/bash

# FipsLinker.sh: wrapper (for POSIX systems) to link an application
# using the Vidyo Security Kernel (libLmiSecurityFips.a) and OpenSSL FIPS libraries.

# Usage: pass the entire normal link command line as arguments to this script.
# Optionally: set CALCHASH env variable to script to execute on executable
# to calculate system hash.

# The FipsLinker.sh script provides a "SDK" fingerprint that is provided as
# a constant passed in at compile time to SDK "canister" LmiSecurityFipsFingerprint.c
# In addition, the OpenSSL canister must be also initialized.  This is done
# with the openssl/bin/fipsld
# 
# Note that both builds (create file for SDK fingerprint) and build with the 
# calculated SDK fingerprint must BOTH be built with the OpenSSL canister so
# both Lmi FIPS fingerprint and OpenSSL FIPS fingerprint are valid.
# 
set -e

SCRIPT_NAME=$(basename $0)
SCRIPT_DIR=$( cd $(dirname $0); pwd)

# Both link scripts must be used to initialize two separate fingerprints.
#
# ASSUME : directory structure, initialized openssl directory.
#
# Environment must set FIPS_OPENSSL_DIR and FIPS_SECURITY_KERNEL_DIR
OPENSSL_FIPSLINK="$FIPS_OPENSSL_DIR"/fips-2.0/bin/fipsld
FIPS_UTILS_DIR="$FIPS_SECURITY_KERNEL_DIR"/FipsUtils

export FIPSLD_CC=gcc
export CC=$OPENSSL_FIPSLINK

BUILD_COMMAND="$@"

# Find the product which needs the fingerprint, specified in the build
# command with -o
#
OUTPUT="`(while test -n "$1" -a "$1" != "-o"; do shift; done; echo $2)`"

# In the case there is no output directive, assume a.out as fingerprinted 
# file
#
if test -z "$OUTPUT"
then
	OUTPUT=./a.out
fi

# If we don't have CALCHASH defined, assume we're running natively
if test -z "$CALCHASH"
then
	if test -n "`type -p objdump`"
	then
		CALCHASH="$FIPS_UTILS_DIR"/LmiSecurityKernel-CalcHash-Binutils.pl
	elif test -n "`type -p otool`"
	then
		CALCHASH="$FIPS_UTILS_DIR"/LmiSecurityKernel-CalcHash-OTool.pl
	else
		echo "Cannot determine how to calculate hash, please set \$CALCHASH" 1>$2
		exit 2
	fi
fi

# This build create the "output" file that requires the SDK fingerprint
#
echo $OPENSSL_FIPSLINK "$@" $FIPS_UTILS_DIR/LmiSecurityFipsFingerprint.c
$OPENSSL_FIPSLINK "$@" $FIPS_UTILS_DIR/LmiSecurityFipsFingerprint.c

# OpenSSL 1.0.0 prints "(stdin)=" before the fingerprint, so extract
# just the last field.
FINGERPRINT=`"$CALCHASH" "$OUTPUT" | awk '{print $NF}'`

# The quoted fingerprint is the actual value passed in for the second link (with the fingerprint).
#
QUOTEDFINGERPRINT="`echo "{$FINGERPRINT}" | sed 's/[0-9a-fA-F][0-9a-fA-F]/0x&,/g' | sed 's/,}/}/'`"

# This link provides the last build.  This should allow bothe (OpenSSL) 
# FIPS_mode_set and LmiSecurityInitialize, LmiSecurityDoSelfTest
# (from LmiSecurityFips.a statically linked library)
#
echo $OPENSSL_FIPSLINK "$@" -DLMI_SECURITY_FIPS_FINGERPRINT=$QUOTEDFINGERPRINT $FIPS_UTILS_DIR/LmiSecurityFipsFingerprint.c
$OPENSSL_FIPSLINK "$@" -DLMI_SECURITY_FIPS_FINGERPRINT=$QUOTEDFINGERPRINT $FIPS_UTILS_DIR/LmiSecurityFipsFingerprint.c
