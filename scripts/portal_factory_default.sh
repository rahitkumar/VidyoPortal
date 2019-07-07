#!/bin/bash

logme()
{
   logger -t "portal_factory_default.sh" "$*"
}

logme "Creating Portal2 DB..."
/opt/vidyo/bin/CreatePortal20Db.sh

logme "Purging thumnails/customization folder"

mv -f /opt/vidyo/portal2/thumbnail/default_thumbnail.base64  /opt/vidyo/temp/root/
rm -rf /opt/vidyo/portal2/customization
rm -rf /opt/vidyo/portal2/thumbnail

mkdir -p /opt/vidyo/portal2/customization
mkdir -p /opt/vidyo/portal2/thumbnail

mv -f /opt/vidyo/temp/root/default_thumbnail.base64 /opt/vidyo/portal2/thumbnail/

/opt/vidyo/bin/update_file_permission.sh
