package com.simplyarjen.inject.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.ArrayList;

import com.simplyarjen.inject.Inject;

public final class Injectables {
  private Injectables() {}
  
  @SuppressWarnings("unchecked")
  public static <T> Constructor<T> findConstructor(Class<T> clazz) {
    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
      if (constructor.isAnnotationPresent(Inject.class)) {
        constructor.setAccessible(true);
        return (Constructor<T>) constructor;
      }
    }
    return null;
  }
  
  public static Collection<Field> findFields(Class<?> clazz) {
    Collection<Field> result = new ArrayList<>();
    for (Class<?> candidate: Classes.getSuperclasses(clazz)) {
      findFields(candidate, result);
    }
    return result;
  }
  
  private static void findFields(Class<?> clazz, Collection<Field> result) {
    for (Field field : clazz.getDeclaredFields()) {
      if (isInjectable(field)) {
        field.setAccessible(true);
        result.add(field);
      }
    }
  }
  
  private static boolean isInjectable(Field field) {
    int modifier = field.getModifiers();
    if (Modifier.isStatic(modifier) || Modifier.isFinal(modifier)) {
      return false;
    }
    return field.isAnnotationPresent(Inject.class);
  }
}