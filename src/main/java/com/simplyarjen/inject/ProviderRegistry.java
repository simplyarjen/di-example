package com.simplyarjen.inject;

import java.util.Map;
import java.util.HashMap;

import com.simplyarjen.inject.provider.Provider;
import com.simplyarjen.inject.reflection.Classes;

class ProviderRegistry {
  private static final Provider<?> AMBIGUOUS = new Provider<Void>() {
    @Override
    public Void provide(Context context) {
      throw new UnsupportedOperationException("The AMBIGUOUS provider should not be invoked");
    }
  };  
  
  private final Map<Class<?>, Provider<?>> delegate = new HashMap<>();
  
  public <T> void put(Class<T> clazz, Provider<T> provider) {
    for (Class<?> assignable : Classes.getAssignableFrom(clazz)) {
      Provider<?> actual = contains(assignable) ? AMBIGUOUS : provider;
      delegate.put(assignable, actual);
    }
  }
  
  @SuppressWarnings("unchecked")
  public <T> Provider<? extends T> get(Class<T> clazz) {
    checkArgument(contains(clazz), "No provider for class " + clazz);
    checkArgument(!isAmbiguous(clazz), "More than one provider for class " + clazz);
    return (Provider<? extends T>) delegate.get(clazz);
  }
  
  private boolean contains(Class<?> clazz) {
    return delegate.containsKey(clazz);
  }
  
  private boolean isAmbiguous(Class<?> clazz) {
    return AMBIGUOUS.equals(delegate.get(clazz));
  }
  
  private static void checkArgument(boolean condition, String message) {
    if (!condition) {
      throw new IllegalStateException(message);
    }
  }
}