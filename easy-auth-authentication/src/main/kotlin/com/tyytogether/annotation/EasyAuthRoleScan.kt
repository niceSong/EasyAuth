package com.tyytogether.annotation

import com.tyytogether.driver.RoleRegistrar
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(RoleRegistrar::class)
annotation class EasyAuthRoleScan (
    val value: String = ""
)