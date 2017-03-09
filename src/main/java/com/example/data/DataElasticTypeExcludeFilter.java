package com.example.data;

import java.util.Collections;
import java.util.Set;
import org.springframework.boot.test.autoconfigure.filter.AnnotationCustomizableTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
class DataElasticTypeExcludeFilter extends AnnotationCustomizableTypeExcludeFilter {
  private final DataElasticTest annotation;

  DataElasticTypeExcludeFilter(final Class<?> testClass) {
    this.annotation = AnnotatedElementUtils.getMergedAnnotation(testClass,
      DataElasticTest.class);
  }

  @Override
  protected boolean hasAnnotation() {
    return this.annotation != null;
  }

  @Override
  protected Filter[] getFilters(final FilterType type) {
    switch (type) {
      case INCLUDE:
        return this.annotation.includeFilters();
      case EXCLUDE:
        return this.annotation.excludeFilters();
      default:
        throw new IllegalStateException("Unsupported type " + type);
    }
  }

  @Override
  protected boolean isUseDefaultFilters() {
    return this.annotation.useDefaultFilters();
  }

  @Override
  protected Set<Class<?>> getDefaultIncludes() {
    return Collections.emptySet();
  }

  @Override
  protected Set<Class<?>> getComponentIncludes() {
    return Collections.emptySet();
  }
}
