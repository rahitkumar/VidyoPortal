#!/bin/bash

OPENSSL_VERSION=openssl-1.0.1u-fips-2.0.12
OPENSSL_DIR=../Frameworks/Linux/OpenSSL/ubuntu/${OPENSSL_VERSION}/x86_64-trusty/opt/vidyo/app/openssl



aclocal -I m4
autoheader
autoconf
automake -a -c -f
./configure --with-libsrtp=../Frameworks/Linux/LibSRTP/libsrtp-1.5.3/x86_64 --with-openssl=${OPENSSL_DIR} --enable-build-mode=release --enable-conference-server --disable-vidyo-client

make
