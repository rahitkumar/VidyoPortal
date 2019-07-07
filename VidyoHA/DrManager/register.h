#ifndef _REGISTER_H_
#define _REGISTER_H_

#define ACTIVE_CONF_DIR "/opt/vidyo/data/dr/active"
#define ACTIVE_CONF      ACTIVE_CONF_DIR"/node.conf"
#define ACTIVE_CONF_LOCK ACTIVE_CONF_DIR"/node.conf.lock"

void *Register(void *args);

#endif
