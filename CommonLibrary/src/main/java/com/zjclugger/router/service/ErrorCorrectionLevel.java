package com.zjclugger.router.service;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(CLASS)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface ErrorCorrectionLevel {
    String L = "L";
    String M = "M";
    String Q = "Q";
    String H = "H";
}
