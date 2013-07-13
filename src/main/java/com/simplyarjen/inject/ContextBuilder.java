package com.simplyarjen.inject;

import com.simplyarjen.inject.provider.ProviderFactory;

public class ContextBuilder {
  private final ProviderRegistry registry = new ProviderRegistry();
  
  public <T> ContextBuilder add(Class<T> clazz) {
    registry.put(clazz, ProviderFactory.create(clazz));
    return this;
  }
  
  public Context build() {
    return new SimpleContext(registry);
  }
}