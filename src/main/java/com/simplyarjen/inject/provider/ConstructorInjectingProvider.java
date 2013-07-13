package com.simplyarjen.inject.provider;

import java.lang.reflect.Constructor;

import com.simplyarjen.inject.Context;
import com.simplyarjen.inject.reflection.Classes;

class ConstructorInjectingProvider<T> implements Provider<T> {
  private final Constructor<T> constructor;
  private final Class<?>[] parameterClasses;
  
  ConstructorInjectingProvider(Constructor<T> constructor) {
    this.constructor = constructor;
    this.parameterClasses = constructor.getParameterTypes();
  }
  
  @Override
  public T provide(Context context) {
    return Classes.newInstance(constructor, getAll(context, parameterClasses));
  }
  
  private Object[] getAll(Context context, Class<?>... classes) {
    Object[] result = new Object[classes.length];
    for (int index = 0; index < classes.length; index++) {
      result[index] = context.get(classes[index]);
    }
    return result;
  }
}