package com.simplyarjen.inject;

public interface Context {
  <T> T get(Class<T> clazz);
}