package com.app.monitor.logic;

import com.app.monitor.analyser.JvmAnalyser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JvmAnalyzerTest {

    JvmAnalyser service = new JvmAnalyser();

    @Test
    void shouldGetJvmVersion() {
        assertEquals(service.getVersion(), Runtime.version().toString());
    }

}
