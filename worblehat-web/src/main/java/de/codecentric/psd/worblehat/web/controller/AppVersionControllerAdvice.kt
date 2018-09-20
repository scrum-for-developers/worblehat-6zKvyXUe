package de.codecentric.psd.worblehat.web.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
open class AppVersionControllerAdvice {

    @Value("\${info.version}")
    @get:ModelAttribute("applicationVersion")
    lateinit var applicationVersion: String

}
