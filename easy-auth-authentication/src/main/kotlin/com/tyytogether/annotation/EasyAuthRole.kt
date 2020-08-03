package com.tyytogether.annotation


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EasyAuthRole (
    vararg val permissions: String
)