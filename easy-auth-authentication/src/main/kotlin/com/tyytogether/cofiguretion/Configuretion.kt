package com.tyytogether.cofiguretion

import com.tyytogether.driver.RoleRegistrar
import org.springframework.context.annotation.Bean

class Configuretion {
    @Bean
    fun getBean(): RoleRegistrar{
        return RoleRegistrar()
    }
}