package com.app.monitor.analyser;

import com.app.monitor.rest.memory.GarbageCollection;
import com.app.monitor.rest.memory.MemoryUsage;
import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .map(gc -> new GarbageCollection()
                        .setName(gc.getGcName())
                        .setCause(gc.getGcCause())
                        .setDuration(gc.getGcInfo().getDuration())
                        .setMemoryUsageBeforeGc(translateMemoryUsageMap(gc.getGcInfo().getMemoryUsageBeforeGc()))
                        .setMemoryUsageAfterGc(translateMemoryUsageMap(gc.getGcInfo().getMemoryUsageAfterGc()))
                ).toList();
    }

    private Map<String, MemoryUsage> translateMemoryUsageMap(Map<String, java.lang.management.MemoryUsage> internalMemoryUsages) {
        HashMap<String, MemoryUsage> memoryUsageMap = new HashMap<>();

        for (String name : internalMemoryUsages.keySet()) {
            memoryUsageMap.put(name, translateMemoryUsage(internalMemoryUsages.get(name)));
        }

        return memoryUsageMap;
    }

    private MemoryUsage translateMemoryUsage(java.lang.management.MemoryUsage internalMemoryUsage) {
        return new MemoryUsage()
                .setInit(internalMemoryUsage.getInit())
                .setCommitted(internalMemoryUsage.getCommitted())
                .setMax(internalMemoryUsage.getMax())
                .setUsed(internalMemoryUsage.getUsed());
    }
}
