package com.udacity.gradle.builditbigger;

public class MainActivityTest {
  MainActivityTest() {

  }

  public void testJoke() {
    MainActivity mainActivity = new MainActivity();
    mainActivity.tellJoke(null);
  }
}