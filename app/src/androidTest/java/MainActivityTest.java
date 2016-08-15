import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.udacity.gradle.builditbigger.JokeAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {
  private String returnedString;
  @Test
  public void testJoke() {
    final CountDownLatch signal = new CountDownLatch(1);
    returnedString = null;
    JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();

    jokeAsyncTask.callback = new JokeAsyncTask.JokeAsyncCallback() {
      @Override
      public void onError(String errorMessage) {
        returnedString = errorMessage;
        assertNotNull(errorMessage);
        signal.countDown();
      }

      @Override
      public void onJoke(String joke) {
        returnedString = joke;
        assertNotNull(joke);
        signal.countDown();
      }
    };
    jokeAsyncTask.execute();

    try {
      signal.await(30, TimeUnit.SECONDS);
    } catch (Exception e) {
      Log.d("Error", "Time thing interrupted");
    }
    assertNotNull(returnedString);

  }
}