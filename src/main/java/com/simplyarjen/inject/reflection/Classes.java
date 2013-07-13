package com.simplyarjen.inject.reflection;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class Classes {
  
  private Classes() { }
  
  public static <T> T newInstance(Class<T> clazz) {
    try {
      return clazz.newInstance();
    }
    catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Cannot create instance of class " + clazz, e);
    }
  }
  
  public static <T> T newInstance(Constructor<T> constructor, Object... arguments) {
    try {
      return constructor.newInstance(arguments);
    }
    catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Cannot invoke constructor " + constructor, e);
    }
  }
  
  public static Collection<Class<?>> getSuperclasses(Class<?> clazz) {
    Set<Class<?>> result = new HashSet<>();
    addSuperclasses(clazz, result);
    return result;
  }
  
  public static Collection<Class<?>> getAssignableFrom(Class<?> clazz) {
    Set<Class<?>> result = new HashSet<>();
    result.addAll(getSuperclasses(clazz));
    result.addAll(Arrays.asList(clazz.getInterfaces()));
    return result;
  }
  
  private static void addSuperclasses(Class<?> clazz, Set<Class<?>> result) {
    if (clazz != null) {
      result.add(clazz);
      addSuperclasses(clazz.getSuperclass(), result);
    }
  }
}
