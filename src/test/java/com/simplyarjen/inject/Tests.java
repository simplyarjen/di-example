package com.simplyarjen.inject;

import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {
  public static class A {
    private Object o;
  }
  
  public static class B {
    private A a;
    
    @Inject
    public B(A a) {this.a = a;}
  }
  
  public static class C {
    @Inject
    private A a;
    
  }
  
  public static class D {
    
  }
  
  public static class E {
    @Inject
    private A a;
    
    private D d;
    
    @Inject
    public E(D d) {this.d = d;}
  }
  
  public static class F {
    @Inject 
    private E e;
  }
  
  public static class G extends A {
  
  }
  
  public static class H extends C {
  
  }
  
  @Test
  public void testConstruction() {
    Context context = new ContextBuilder()
      .add(A.class)
      .build();
      
    A a = context.get(A.class);
    
    assertNotNull(a);
    assertNull(a.o);
  }
  
  @Test
  public void testConstructorInjection() {
    Context context = new ContextBuilder()
      .add(A.class)
      .add(B.class)
      .build();
   
    B b = context.get(B.class);
    assertNotNull(b);
    assertNotNull(b.a);
  }
  
  @Test
  public void testFieldInjection() {
    Context context = new ContextBuilder()
      .add(A.class)
      .add(C.class)
      .build();

    C c = context.get(C.class);
    assertNotNull(c);
    assertNotNull(c.a);
  }
  
  @Test
  public void testMixedInjection() {
    Context context = new ContextBuilder()
      .add(A.class)
      .add(D.class)
      .add(E.class)
      .build();

    E e = context.get(E.class);
    assertNotNull(e);
    assertNotNull(e.a);
    assertNotNull(e.d);
  }
  
  @Test
  public void testRecursiveInjection() {
    Context context = new ContextBuilder()
      .add(A.class)
      .add(D.class)
      .add(E.class)
      .add(F.class)
      .build();

    F f = context.get(F.class);
    assertNotNull(f);
    assertNotNull(f.e);
    assertNotNull(f.e.a);
    assertNotNull(f.e.d);
  }
  
  @Test
  public void testSuperclassRetrieval() {
    Context context = new ContextBuilder()
      .add(G.class)
      .build();
      
    A a = context.get(A.class);
    
    assertNotNull(a);
    assertTrue(a instanceof G);
  }
  
  @Test
  public void testSuperclassInjection() {
    Context context = new ContextBuilder()
      .add(G.class)
      .add(H.class)
      .build();
      
    C c = context.get(C.class);
    
    assertNotNull(c);
    assertTrue(c instanceof H);    
    assertNotNull(c.a);
    assertTrue(c.a instanceof G);
  }
}