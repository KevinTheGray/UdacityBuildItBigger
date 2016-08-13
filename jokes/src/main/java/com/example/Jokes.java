package com.example;
import java.util.Random;
public class Jokes {
  private String[] jokes = {
    "Why did the chicken cross the road? To get to the other slide!",
    "Did you hear the one about the airplane?  Never mind, it'll go over your head!",
    "Why was six afraid of seven? Because seven ate nine!",
    "What do you call a bear with no teeth? A gummy bear!",
    "Where do cows go to have fun? The mooooooovies!",
    "How do astronomers organize a party? They planet!",
    "Why is it that your nose runs, but your feet smell?"
  };
  public String getJoke(){
    Random randy = new Random();
    int randomIndex = randy.nextInt(this.jokes.length);
    return jokes[randomIndex];
  }
}
