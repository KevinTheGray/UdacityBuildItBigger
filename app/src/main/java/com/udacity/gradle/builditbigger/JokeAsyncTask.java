package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<Void, Void, Pair<String, String>> {
  public MyApi myApiService = null;
  public JokeAsyncCallback callback;
  @Override
  protected Pair<String, String> doInBackground(Void... voids) {
    if (myApiService == null) {  // Only do this once
      MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
        new AndroidJsonFactory(), null)
        // options for running against local devappserver
        // - 10.0.2.2 is localhost's IP address in Android emulator
        // - turn off compression when running against local devappserver
        .setRootUrl("http://192.168.0.10:8080/_ah/api/")
        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
          @Override
          public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
            abstractGoogleClientRequest.setDisableGZipContent(true);
          }
        });
      // end options for devappserver

      myApiService = builder.build();
    }

    try {
      String joke = myApiService.tellJoke().execute().getData();
      return new Pair<String, String>(joke, null);
    } catch (IOException e) {
      return new Pair<String, String>(null, e.getMessage());
    }
  }

  @Override
  protected void onPostExecute(Pair<String, String> result) {
    if (this.callback != null) {
      if (result.second != null) {
        this.callback.onError(result.second);
      } else {
        this.callback.onJoke(result.first);
      }
    }
  }

  public interface JokeAsyncCallback {
    void onError(String errorMessage);
    void onJoke(String joke);
  }

}
