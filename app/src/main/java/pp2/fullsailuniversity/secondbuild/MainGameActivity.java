package pp2.fullsailuniversity.secondbuild;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class MainGameActivity extends AppCompatActivity implements GetTriviaJSONData.OnDataAvailable
{
    private static final String TAG = "MainActivity";
    
    
    public static List<QuizQuestion> quiz;
    private AtomicInteger i, score;
    private TextView question;
    private  TextView scorecounter;
    private GetTriviaJSONData getTriviaJSONData;
    private Button next;
    private Button exit;
    private AtomicBoolean answered;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private ImageButton startbtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_game);
        i = new AtomicInteger();
        score = new AtomicInteger(0);
        answered = new AtomicBoolean(false);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        scorecounter = findViewById(R.id.score);
        scorecounter.setText(score.toString());
        question = findViewById(R.id.textView);
        next = findViewById(R.id.nextB);
        exit = findViewById(R.id.exitB);
        startbtn = findViewById(R.id.start_button);
        question.setText("");
        next.setAlpha(0.0f);
        next.setClickable(false);
        b1.setClickable(false);
        b1.setAlpha(0.0f);
        b2.setClickable(false);
        b2.setAlpha(0.0f);
        b3.setClickable(false);
        b3.setAlpha(0.0f);
        b4.setClickable(false);
        b4.setAlpha(0.0f);
        
        //new GetTriviaJSONData(this, "https://opentdb.com/api.php?amount=10&type=multiple").execute("testurl");
    
        GetTriviaJSONData getTriviaJSONData = new GetTriviaJSONData(this, "https://api.flickr.com/services/feeds/photos_public.gne");
    
        getTriviaJSONData.execute("testURL");
        
        startbtn.setOnClickListener(v -> {
            GameLoop(0);
            startbtn.setBackgroundColor(Color.RED);
        });
    
        exit.setOnClickListener((view) ->
        {
            finish();
            System.exit(0);
        });
        
        Log.d(TAG, "onCreate: ends");
//Setting of Q/A and Tags
    }
    
    
    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume starts");
        super.onResume();
        Log.d(TAG, "onResume ends");
    }
    
    @Override
    public void onDataAvailable(List<QuizQuestion> data, DownloadStatus status)
    {
        if (status == DownloadStatus.OK)
        {
            Log.d(TAG, "onDataAvailable: data is " + data);
            quiz = data;
        } else
        {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }
    }
    
    public void GameLoop(int index)
    {
        
        if (quiz != null)
        {
            startbtn.setClickable(false);
            startbtn.setAlpha(0.0f);
            next.setAlpha(0.0f);
            
            b1.setClickable(true);
            b2.setClickable(true);
            b3.setClickable(true);
            b4.setClickable(true);
    
            b1.setAlpha(1.0f);
            b2.setAlpha(1.0f);
            b3.setAlpha(1.0f);
            b4.setAlpha(1.0f);
    
    
            b1.setBackgroundColor(Color.LTGRAY);
            b2.setBackgroundColor(Color.LTGRAY);
            b3.setBackgroundColor(Color.LTGRAY);
            b4.setBackgroundColor(Color.LTGRAY);
            
            question.setText(quiz.get(index).questionString);
            quiz.get(index).RandomizeQuestionOrder();
            b1.setText(quiz.get(index).answers[0].m_answer);
            b2.setText(quiz.get(index).answers[1].m_answer);
            b3.setText(quiz.get(index).answers[2].m_answer);
            b4.setText(quiz.get(index).answers[3].m_answer);
            
            
            if (quiz.get(index).answers[0].isCorrect)
            {
                b1.setTag("true");
            } else
                b1.setTag("false");
            
            if (quiz.get(index).answers[1].isCorrect)
            {
                b2.setTag("true");
            } else
                b2.setTag("false");
            
            if (quiz.get(index).answers[2].isCorrect)
            {
                b3.setTag("true");
            } else
                b3.setTag("false");
            
            if (quiz.get(index).answers[3].isCorrect)
            {
                b4.setTag("true");
            } else
                b4.setTag("false");


// set string values for the questions
            next.setOnClickListener((view) ->
                    {
    
                        next.setClickable(false);
                        if (i.get() < quiz.size() - 1)
                        {
                            i.set(i.get() + 1);
                            GameLoop(i.get());
                        }
                        else{
    
                            finish();
                            startActivity(getIntent());
                        }
                    }
            );
            
            
            //exit App
            
            //next button functionality
            b1.setOnClickListener((view) ->
            {
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                next.setClickable(true);
                next.setAlpha(1.0f);
                
                
                if (b1.getTag() == "true")
                {
                    score.set(score.get() + 1);
                    b1.setBackgroundColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "Correct!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else
                {
                    b1.setBackgroundColor(Color.RED);
                    Context context = getApplicationContext();
                    CharSequence text = "Wrong!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
    
                int temp = i.get() + 1;
                scorecounter.setText(score.toString() + "/" + Integer.toString(temp));
            });
            
            
            b2.setOnClickListener((view) ->
            {
    
                next.setClickable(true);
                next.setAlpha(1.0f);
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);

                if (b2.getTag() == "true")
                {
                    score.set(score.get() + 1);
                    b2.setBackgroundColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "Correct!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else
                {
    
                    b2.setBackgroundColor(Color.RED);
                    Context context = getApplicationContext();
                    CharSequence text = "Wrong!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                int temp = i.get() + 1;
                scorecounter.setText(score.toString() + "/" + Integer.toString(temp));
            });
            
            
            b3.setOnClickListener((view) ->
            {
    
                next.setClickable(true);
                next.setAlpha(1.0f);
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                
                if (b3.getTag() == "true")
                {
                    score.set(score.get() + 1);
                    b3.setBackgroundColor(Color.GREEN);
                    
                    Context context = getApplicationContext();
                    CharSequence text = "Correct!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else
                {
                    b3.setBackgroundColor(Color.RED);
                    Context context = getApplicationContext();
                    CharSequence text = "Wrong!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                int temp = i.get() + 1;
                scorecounter.setText(score.toString() + "/" + Integer.toString(temp));
            });
            
            b4.setOnClickListener((view) ->
            {
    
                next.setClickable(true);
                next.setAlpha(0.9f);
                b1.setClickable(false);
                b2.setClickable(false);
                b3.setClickable(false);
                b4.setClickable(false);
                
                if (b4.getTag() == "true")
                {
                    score.set(score.get() + 1);
                    b4.setBackgroundColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "Correct!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else
                {
                    b4.setBackgroundColor(Color.RED);
                    Context context = getApplicationContext();
                    CharSequence text = "Wrong!";
                    int duration = Toast.LENGTH_SHORT;
                    
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                int temp = i.get() + 1;
                scorecounter.setText(score.toString() + "/" + Integer.toString(temp));
                
            });
        }
    }
    
    
}
