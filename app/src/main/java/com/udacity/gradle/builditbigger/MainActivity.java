package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import kevingray.jokeandroidlibrary.JokeDisplayerActivity;


public class MainActivity extends ActionBarActivity implements JokeAsyncTask.JokeAsyncCallback {

  public String joke = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void tellJoke(View view) {
    JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();
    jokeAsyncTask.callback = this;
    jokeAsyncTask.execute();
  }

  public void presentJoke() {
    if (this.joke != null) {
      Intent intent = new Intent(this, JokeDisplayerActivity.class);
      intent.putExtra(JokeDisplayerActivity.JOKE_EXTRA, joke);
      startActivity(intent);
    } else {
      presentError("The joke was null.");
    }
  }

  public void presentError(String errorMessage) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
  }

  @Override
  public void onJoke(String joke) {
    this.joke = joke;
    presentJoke();
  }

  @Override
  public void onError(String errorMessage) {
    presentError(errorMessage);
  }
}
