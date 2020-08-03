package com.tyytogether.driver

import com.tyytogether.annotation.EasyAuthRole
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter

class RoleProvider : ClassPathScanningCandidateComponentProvider(true) {

    override fun registerDefaultFilters() {
        addIncludeFilter(AnnotationTypeFilter(EasyAuthRole::class.java))
    }

    override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean {
        return beanDefinition.metadata.isIndependent && beanDefinition.metadata.isConcrete
    }
}