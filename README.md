# sync-android-p2p-example

An example Android application that uses https://github.com/snowch/sync-android-p2p to expose a device sync android 
database allowing the device to become a target destination for a couchdb replication.

*Setup instructions*

- Ensure you have couchdb running

- Deploy this code to device that is on the same network as the couchdb server.

- Run netcfg to get the wlan0 IP address on the device:

```
adb shell netcfg
lo       UP                                   127.0.0.1/8   0x00000049 00:00:00:00:00:00
dummy0   DOWN                                   0.0.0.0/0   0x00000082 92:e5:00:cc:2d:20
sit0     DOWN                                   0.0.0.0/0   0x00000080 00:00:00:00:00:00
ip6tnl0  DOWN                                   0.0.0.0/0   0x00000080 00:00:00:00:00:00
p2p0     UP                                     0.0.0.0/0   0x00001003 62:a4:4c:90:20:93
wlan0    UP                                192.168.1.65/24  0x00001043 60:a4:4c:90:20:93
```

- Ensure the device http listener is accepting connections:
```
curl http://192.168.1.65:8182/mydb
{"update_seq":"3","instance_start_time":"1381218659871282"}
```

- Send a request to couchdb to replicate to the android device on the wlan0 ip address:
```
 curl -X POST -H 'Content-Type:application/json' -d '
{
  "source": "http://localhost:5984/newdb",
  "target": "http://192.168.1.65:8182/mydb"
}' http://localhost:5984/_replicate
{"ok":true,"no_changes":true,"session_id":"dab949feb5487b225641c6c47b994d9c","source_last_seq":2,"replication_id_version":3,"history":[{"session_id":"dab949feb5487b225641c6c47b994d9c","start_time":"Sun, 01 Feb 2015 00:09:48 GMT","end_time":"Sun, 01 Feb 2015 00:09:48 GMT","start_last_seq":0,"end_last_seq":2,"recorded_seq":2,"missing_checked":2,"missing_found":2,"docs_read":2,"docs_written":2,"doc_write_failures":0}]}
```
