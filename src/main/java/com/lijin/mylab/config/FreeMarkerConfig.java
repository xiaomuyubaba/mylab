package com.lijin.mylab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Value("${system.ctx}")
    private String ctx;

    @Value("${system.spath}")
    private String spath;

    @PostConstruct
    public void setConfigure() throws Exception {
        configuration.setSharedVariable("CPATH", ctx);
        configuration.setSharedVariable("SPATH", spath);
    }
}
