package com.simplyarjen.inject.provider;

import com.simplyarjen.inject.Context;

class SingletonProvider<T> implements Provider<T> {
  private final Provider<T> delegate;
  private T instance = null;

  SingletonProvider(Provider<T> delegate) {
    this.delegate = delegate;
  }

  @Override  
  public T provide(Context context) {
    if (instance == null) {
      instance = delegate.provide(context);
    }
    return instance;
  }
}
