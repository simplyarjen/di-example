package com.simplyarjen.inject.provider;

import com.simplyarjen.inject.Context;
import com.simplyarjen.inject.reflection.Classes;

class SimpleReflectiveProvider<T> implements Provider<T> {
  private final Class<T> clazz;
  
  SimpleReflectiveProvider(Class<T> clazz) {
    this.clazz = clazz;
  }
  
  @Override  
  public T provide(Context context) {
    return Classes.newInstance(clazz);
  }
}