package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import kevingray.jokeandroidlibrary.JokeDisplayerActivity;


public class MainActivity extends ActionBarActivity {

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
    new EndpointsAsyncTask().execute(this);
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

  private class EndpointsAsyncTask extends AsyncTask<Context, Void, Pair<String, String>> {
    private MyApi myApiService = null;
    private Context context;

    @Override
    protected Pair<String, String> doInBackground(Context... params) {
      if(myApiService == null) {  // Only do this once
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
          new AndroidJsonFactory(), null)
          // options for running against local devappserver
          // - 10.0.2.2 is localhost's IP address in Android emulator
          // - turn off compression when running against local devappserver
          .setRootUrl("http://192.168.0.11:8080/_ah/api/")
          .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
              abstractGoogleClientRequest.setDisableGZipContent(true);
            }
          });
        // end options for devappserver

        myApiService = builder.build();
      }

      context = params[0];
      try {
        joke =  myApiService.tellJoke().execute().getData();
        return new Pair<String, String>(joke, null);
      } catch (IOException e) {
        return new Pair<String, String>(null, e.getMessage());
      }
    }

    @Override
    protected void onPostExecute(Pair<String, String> result) {
      if (result.second != null) {
        presentError(result.second);
      } else {
        presentJoke();
      }
    }
  }


}
