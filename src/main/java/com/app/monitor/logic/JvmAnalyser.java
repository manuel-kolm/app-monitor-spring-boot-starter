package com.app.monitor.logic;

import com.app.monitor.rest.memory.MemoryPool;
import com.app.monitor.rest.memory.MemoryUsage;
import com.app.monitor.rest.metric.Metric;
import com.app.monitor.rest.system.Cpu;
import com.app.monitor.rest.system.OperatingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

@Service
public class JvmAnalyser {

    private static final Logger LOG = LoggerFactory.getLogger(JvmAnalyser.class);

    private final MemoryMXBean memoryMXBean;
    private final List<MemoryPoolMXBean> memoryPoolMXBeans;
    private final ThreadMXBean threadMXBean;
    private final OperatingSystemMXBean operatingSystemMXBean;


    public JvmAnalyser() {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
        memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        threadMXBean = ManagementFactory.getThreadMXBean();
        operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
    }

    public String getVersion() {
        return Runtime.version().toString();
    }

    public Metric getMetric() {
        return new Metric()
                .setCpu(getCpu())
                .setMemoryPools(getCurrentMemoryPools())
                .setHeap(getHeapMemoryUsage())
                .setNonHeap(getNonHeapMemoryUsage());
    }

    private MemoryUsage getHeapMemoryUsage() {
        java.lang.management.MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        return new MemoryUsage()
                .setInit(memoryUsage.getInit())
                .setCommitted(memoryUsage.getCommitted())
                .setMax(memoryUsage.getMax())
                .setUsed(memoryUsage.getUsed());
    }

    private MemoryUsage getNonHeapMemoryUsage() {
        java.lang.management.MemoryUsage memoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return new MemoryUsage()
                .setInit(memoryUsage.getInit())
                .setCommitted(memoryUsage.getCommitted())
                .setMax(memoryUsage.getMax())
                .setUsed(memoryUsage.getUsed());
    }

    private List<MemoryPool> getCurrentMemoryPools() {
        List<MemoryPool> memoryPools = new ArrayList<>();
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            java.lang.management.MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
            MemoryPool memoryPool = new MemoryPool()
                    .setName(memoryPoolMXBean.getName())
                    .setInit(memoryUsage.getInit())
                    .setCommitted(memoryUsage.getCommitted())
                    .setMax(memoryUsage.getMax())
                    .setUsed(memoryUsage.getUsed());

            memoryPools.add(memoryPool);
        }
        return memoryPools;
    }

    public Cpu getCpu() {
        return new Cpu()
                .setThreadCount(threadMXBean.getThreadCount())
                .setPeakThreadCount(threadMXBean.getPeakThreadCount())
                .setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount())
                .setDaemonThreadCount(threadMXBean.getDaemonThreadCount())
                .setProcessCpuLoad(getProcessCpuLoad());
    }

    public Double getProcessCpuLoad() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList attributes = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

            for (Object item : attributes) {
                Attribute attribute = (Attribute) item;
                return (Double) attribute.getValue();
            }

        } catch (Exception ex) {
            LOG.error("Failed to calculate process cpu load. Root cause: " + ex.getMessage());
        }
        return -1.0;
    }

    public OperatingSystem getOperatingSystem() {
        return new OperatingSystem()
                .setName(operatingSystemMXBean.getName())
                .setArch(operatingSystemMXBean.getArch())
                .setVersion(operatingSystemMXBean.getVersion())
                .setAvailableProcessors(operatingSystemMXBean.getAvailableProcessors());
    }
}
