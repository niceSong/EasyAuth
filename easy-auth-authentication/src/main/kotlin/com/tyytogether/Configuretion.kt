package com.tyytogether

import com.tyytogether.driver.RoleRegistrar
import com.tyytogether.tools.EasyAuthJwtTools
import com.tyytogether.tools.EasyJWT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@ComponentScan
class Configuretion {
    @Bean
    fun injectRoleRegistrar(): RoleRegistrar{
        return RoleRegistrar()
    }
}