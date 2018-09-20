package de.codecentric.psd.worblehat.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppVersionControllerAdvice {

    @Value("${info.version}")
    private String applicationVersion;

    @ModelAttribute("applicationVersion")
    public String getApplicationVersion() {
        return applicationVersion;
    }

}
