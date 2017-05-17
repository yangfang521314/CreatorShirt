package com.example.yf.creatorshirt.inject.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by yang on 17/05/2017.
 *
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestUrl {
}
