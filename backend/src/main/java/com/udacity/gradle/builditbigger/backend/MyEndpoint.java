/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Random;

/**
 * An endpoint class we are exposing
 */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.builditbigger.gradle.udacity.com",
    ownerName = "backend.builditbigger.gradle.udacity.com",
    packagePath = ""
  )
)
public class MyEndpoint {

  private String[] jokes = {
    "Why did the chicken cross the road? To get to the other slide!",
    "Did you hear the one about the airplane?  Never mind, it'll go over your head!",
    "Why was six afraid of seven? Because seven ate nine!",
    "What do you call a bear with no teeth? A gummy bear!",
    "Where do cows go to have fun? The mooooooovies!",
    "How do astronomers organize a party? They planet!",
    "Why is it that your nose runs, but your feet smell?"
  };

  /**
   * A simple endpoint method that takes a name and says Hi back
   */
  @ApiMethod(name = "tellJoke")
  public MyJoke tellJoke() {
    MyJoke myJoke = new MyJoke();

    Random randy = new Random();
    int randomIndex = randy.nextInt(this.jokes.length);
    String joke = jokes[randomIndex];
    myJoke.setData(joke);

    return myJoke;
  }

}
