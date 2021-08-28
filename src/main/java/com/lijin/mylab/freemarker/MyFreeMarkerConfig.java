package com.lijin.mylab.freemarker;

import com.lijin.mylab.freemarker.directive.HelloDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MyFreeMarkerConfig {

    @Autowired
    public HelloDirective helloDirective;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(FreeMarkerProperties properties) {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths(properties.getTemplateLoaderPath());
        freeMarkerConfigurer.setPreferFileSystemAccess(properties.isPreferFileSystemAccess());
        freeMarkerConfigurer.setDefaultEncoding(properties.getCharsetName());
        Properties settings = new Properties();
        settings.put("recognize_standard_file_extensions", "true");
        settings.putAll(properties.getSettings());
        freeMarkerConfigurer.setFreemarkerSettings(settings);
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("hello", helloDirective);
        freeMarkerConfigurer.setFreemarkerVariables(variables);
        return freeMarkerConfigurer;
    }

}
