package kevingray.jokeandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayerActivity extends AppCompatActivity {
  public static final String JOKE_EXTRA = "joke";
  // Joke set to a default
  private String joke = "Why did the insect's joke app show the default joke?  Because there was a BUG in it!";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_joke_displayer);

    if (getIntent().hasExtra(JOKE_EXTRA)) {
      joke = getIntent().getStringExtra(JOKE_EXTRA);
    }

    TextView textView = (TextView) findViewById(R.id.joke_text_view);
    textView.setText(joke);
  }
}
