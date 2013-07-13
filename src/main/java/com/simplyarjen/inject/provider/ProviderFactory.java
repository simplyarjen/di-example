package com.simplyarjen.inject.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;

import com.simplyarjen.inject.reflection.Injectables;

public class ProviderFactory {
  private ProviderFactory() {}
  
  public static <T> Provider<T> create(Class<T> clazz) {
    Provider<T> result = createConstructing(clazz);
    result = createFields(clazz, result);
    result = new SingletonProvider(result);
    return result;
  }
  
  public static <T> Provider<T> createConstructing(Class<T> clazz) {
    Constructor<T> injectableConstructor = Injectables.findConstructor(clazz);
    if (injectableConstructor != null) {
      return new ConstructorInjectingProvider<T>(injectableConstructor);
    }
    return new SimpleReflectiveProvider<T>(clazz);
  }
  
  public static <T> Provider<T> createFields(Class<T> clazz, Provider<T> delegate) {
    Collection<Field> injectableFields = Injectables.findFields(clazz);
    if (injectableFields.isEmpty()) {
      return delegate;
    }
    return new FieldInjectingProvider<T>(injectableFields, delegate);
  }
}