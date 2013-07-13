package com.simplyarjen.inject.provider;

import com.simplyarjen.inject.Context;

public interface Provider<T> {
  T provide(Context context);
}
