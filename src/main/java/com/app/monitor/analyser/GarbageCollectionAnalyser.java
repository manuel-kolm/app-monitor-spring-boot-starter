package com.app.monitor.analyser;

import com.app.monitor.rest.memory.GarbageCollection;
import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GarbageCollectionAnalyser {

    private final List<GarbageCollectionNotificationInfo> notifications = Collections.synchronizedList(new ArrayList<>());

    public GarbageCollectionAnalyser() {
        for (var gcMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            NotificationEmitter emitter = (NotificationEmitter) gcMXBean;
            emitter.addNotificationListener((notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    CompositeData cd = (CompositeData) notification.getUserData();
                    notifications.add(GarbageCollectionNotificationInfo.from(cd));
                }
            }, null, null);
        }
    }

    public List<GarbageCollection> getCollections() {
        List<GarbageCollectionNotificationInfo> tempNotifications = new ArrayList<>(notifications);
        notifications.clear();

        return tempNotifications.stream()
                .map(gc -> {
                    return new GarbageCollection()
                            .setName(gc.getGcName())
                            .setCause(gc.getGcCause())
                            .setDuration(gc.getGcInfo().getDuration());
                    //.setMemoryUsageBeforeGc(gc.getGcInfo().getMemoryUsageBeforeGc())
                    //.setMemoryUsageAfterGc(gc.getGcInfo().getMemoryUsageAfterGc());
                }).toList();
    }
}
