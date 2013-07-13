package com.simplyarjen.inject;

class SimpleContext implements Context {
  private final ProviderRegistry providers;

  SimpleContext(ProviderRegistry providers) {
    this.providers = providers;
  }

  @Override
  public <T> T get(Class<T> clazz) {
    return providers.get(clazz).provide(this);
  }
}