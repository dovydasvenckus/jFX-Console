package com.dovydasvenckus.jfxconsole.ui.component

import spock.lang.Specification
import spock.lang.Unroll

import javax.management.MBeanOperationInfo

class ReturnTypeNameResolverSpec extends Specification {

    @Unroll
    def '#type should be resolved as #expectedType'() {
        expect:
            ReturnTypeNameResolverKt.getReturnTypeName('java.lang.String') == 'String'

        where:
            type                            || expectedType
            'java.util.String'              || 'String'
            'java.util.Double'              || 'Double'
            'com.dovydasvenckus.CustomType' || 'com.dovydasvenckus.CustomType'
    }

    @Unroll
    def 'mbean operation with return type #type should resolve #expectedType'() {
        given:
            MBeanOperationInfo operationInfo = Stub(MBeanOperationInfo)
            operationInfo.returnType >> type

        expect:
            ReturnTypeNameResolverKt.getReturnTypeName('java.lang.String') == 'String'

        where:
            type                            || expectedType
            'java.util.String'              || 'String'
            'java.util.Double'              || 'Double'
            'com.dovydasvenckus.CustomType' || 'com.dovydasvenckus.CustomType'
    }
}
