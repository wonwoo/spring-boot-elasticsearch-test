package com.example.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.BootstrapWith;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(DataElasticTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataElastic
@ImportAutoConfiguration
public @interface DataElasticTest {

  /**
   * Determines if default filtering should be used with
   * {@link SpringBootApplication @SpringBootApplication}. By default no beans are
   * included.
   * @see #includeFilters()
   * @see #excludeFilters()
   * @return if default filters should be used
   */
  boolean useDefaultFilters() default true;

  /**
   * A set of include filters which can be used to add otherwise filtered beans to the
   * application context.
   * @return include filters to apply
   */
  Filter[] includeFilters() default {};

  /**
   * A set of exclude filters which can be used to filter beans that would otherwise be
   * added to the application context.
   * @return exclude filters to apply
   */
  Filter[] excludeFilters() default {};

  /**
   * Auto-configuration exclusions that should be applied for this test.
   * @return auto-configuration exclusions to apply
   */
  @AliasFor(annotation = ImportAutoConfiguration.class, attribute = "exclude")
  Class<?>[] excludeAutoConfiguration() default {};

}
