#!/bin/bash
echo 'Stopping VidyoConferencing '
[ -x /opt/vidyo/bin/vrgcore.sh ] && /opt/vidyo/bin/vrgcore.sh

pkill -9 VidyoManager
pkill -9 vr2
echo 'VidyoConferencing Stopped'
