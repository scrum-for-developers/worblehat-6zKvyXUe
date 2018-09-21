package de.codecentric.psd.worblehat.web.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.FIELD,
        AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NumericConstraintValidator::class])
@MustBeDocumented
annotation class Numeric(val message: String = "{de.codecentric.psd.worblehat.web.validation.Numeric}",
                         val groups: Array<KClass<*>> = [],
                         val payload: Array<KClass<out Payload>> = [])
