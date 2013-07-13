package com.simplyarjen.inject.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.simplyarjen.inject.Context;
import com.simplyarjen.inject.Inject;

class FieldInjectingProvider<T> implements Provider<T> {
  private final Collection<Field> injectableFields;
  private final Provider<T> delegate;
  
  FieldInjectingProvider(Collection<Field> injectableFields, Provider<T> delegate) {
    this.delegate = delegate;
    this.injectableFields = injectableFields;
  }

  @Override
  public T provide(Context context) {
      T result = delegate.provide(context);
      for (Field field: injectableFields) {
        inject(result, field, context);
      }
      return result;
  }
  
  private void inject(Object result, Field field, Context context) {
    try {
      field.set(result, context.get(field.getType()));
    }
    catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Cannot inject field " + field, e);
    }
  }
}