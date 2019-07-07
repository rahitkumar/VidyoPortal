#!/bin/bash

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV

[ -f $DB_REPL ] && . $DB_REPL

behind_master()
{
   [ -f $DB_REPL_STATUS_DIR/BEHIND_MASTER ] && return 0
   return 1
}

slave_ok()
{
   [ -f $DB_REPL_STATUS_DIR/SLAVE_OK ] && return 0
   return 1
}

net_err()
{
   [ -f $DB_REPL_STATUS_DIR/NET_ERR ] && return 0
   return 1
}

slave_err()
{
   [ -f $DB_REPL_STATUS_DIR/SLAVE_ERR ] && return 0
   return 1
}

slave_not_running()
{
   [ -f $DB_REPL_STATUS_DIR/SLAVE_NOT_RUNNING ] && return 0
   return 1
}

tunnel_ok()
{
   [ -f $DB_REPL_STATUS_DIR/TUNNEL_OK ] && return 0
   return 1
}

