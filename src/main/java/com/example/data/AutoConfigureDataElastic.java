package com.example.data;

import java.lang.annotation.*;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureDataElastic {
}
