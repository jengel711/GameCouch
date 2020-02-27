package edu.cscc.hibernate_test;

import org.testng.annotations.Test;

public class LocationTest {
  @Test
  public void f() {
  	var lookup = new Lookup();
  	assert (lookup.getLocationByID(Long.valueOf("1")).getId() == 1L);
  }
}
