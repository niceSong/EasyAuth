package com.tyytogether.driver

import com.tyytogether.annotation.EasyAuthRole
import com.tyytogether.annotation.EasyAuthRoleScan
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.annotation.MergedAnnotation
import org.springframework.core.type.AnnotationMetadata

class RoleRegistrar : ImportBeanDefinitionRegistrar {

    private val roleProvider = RoleProvider()

    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        var eventPackage = importingClassMetadata.annotations.stream(EasyAuthRoleScan::class.java).map { it.getString(MergedAnnotation.VALUE) }.findFirst().orElse("")
        if (eventPackage.isEmpty()){
            eventPackage = Class.forName(importingClassMetadata.className).`package`.name
        }
        roleProvider.findCandidateComponents(eventPackage).forEach { beanDefinition ->
            val clazz = Class.forName(beanDefinition.beanClassName).kotlin
            val annotation = clazz.annotations.filter { it.annotationClass ==  EasyAuthRole::class }.first() as EasyAuthRole
            RoleMap.map[clazz.simpleName!!] = annotation.permissions.toMutableList()
        }
    }
}