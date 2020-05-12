package paczka.aplikacji.pytaniaegzaminacyjneppla;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class LearnActivity extends AppCompatActivity implements View.OnClickListener {

    
    public static final String INDEX_KEY = "index_key";
    //q&a array
    String[] question, answer, subject;

    int index, gotonr;

    private AdView madView;

    //Layout Bars
    TextView questionTextView, answerTextView, questionnumberTextView, subjectTextView;
    Button nextButton, prevButton, gotoButton;
    EditText gotoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_layout);

        //initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        madView = findViewById(R.id.adView_learn);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        //initialize array
        question = getResources().getStringArray(R.array.questions);
        answer = getResources().getStringArray(R.array.answers);
        subject= getResources().getStringArray(R.array.question_codes);

        //load index
        index=loadIndex();

        //initialize layout elements
        questionTextView = (TextView) findViewById(R.id.questionlearnTextView);
        answerTextView = (TextView) findViewById(R.id.answerlearnTextView);
        questionnumberTextView = (TextView) findViewById(R.id.questionnumberlearnTextView);
        subjectTextView = (TextView) findViewById(R.id.subjectTextView);
        gotoEditText = (EditText) findViewById(R.id.gotolearnEditText);
        nextButton = (Button) findViewById(R.id.rightarrowlearnButton);
        prevButton = (Button) findViewById(R.id.leftarrowlearnButton);
        gotoButton = (Button) findViewById(R.id.gotolearnButton);

        questionnumberTextView.setText((index+1)+"/"+(question.length));
        questionTextView.setText(question[index]);
        answerTextView.setText(answer[index]);
        subjectSetText(index);

        //set actions for buttons
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        gotoButton.setOnClickListener(this);

    }

    //save index on exiting app
    @Override
    protected void onPause() {
        super.onPause();
        saveIndex(index);
    }

    //actions on click buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightarrowlearnButton:
                if(index<question.length-1){
                    index++;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                    subjectSetText(index);
                }
                else{
                    index=0;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                    subjectSetText(index);
                }
                break;
            case R.id.leftarrowlearnButton:
                if(index>0){
                    index--;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                    subjectSetText(index);
                }
                else{
                    index=question.length-1;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                    subjectSetText(index);
                }
                break;
            case R.id.gotolearnButton:
                try {
                    gotonr = Integer.parseInt(gotoEditText.getText().toString());
                    closeKeyboard();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter number!",Toast.LENGTH_SHORT).show();
                    break;
                }
                    if (gotonr > 1 && gotonr < question.length - 1) {
                        index = gotonr;
                        questionnumberTextView.setText((index) + "/" + (question.length));
                        questionTextView.setText(question[index - 1].trim());
                        answerTextView.setText(answer[index - 1].trim());
                        subjectSetText(index-1);
                        index--;
                    } else if (gotonr == 1) {
                        index = 0;
                        questionnumberTextView.setText((index + 1) + "/" + (question.length));
                        questionTextView.setText(question[index].trim());
                        answerTextView.setText(answer[index].trim());
                        subjectSetText(index);
                    } else if (gotonr == question.length) {
                        index = question.length - 1;
                        questionnumberTextView.setText((index + 1) + "/" + (question.length));
                        questionTextView.setText(question[index].trim());
                        answerTextView.setText(answer[index].trim());
                        subjectSetText(index);
                    }
                    gotoEditText.setText(null);
                    gotoEditText.clearFocus();
                break;

        }
    }


    public void subjectSetText(int i){
        if(subject[i].trim().equals("10")) subjectTextView.setText(R.string.prawo_lotnicze);
        else if(subject[i].trim().equals("20")) subjectTextView.setText(R.string.wiedza_o_samolocie);
        else if(subject[i].trim().equals("30")) subjectTextView.setText(R.string.osiagi);
        else if(subject[i].trim().equals("40")) subjectTextView.setText(R.string.czlowiek);
        else if(subject[i].trim().equals("50")) subjectTextView.setText(R.string.meteo);
        else if(subject[i].trim().equals("60")) subjectTextView.setText(R.string.nawigacja);
        else if(subject[i].trim().equals("70")) subjectTextView.setText(R.string.procedury_operacyjne);
        else if(subject[i].trim().equals("80")) subjectTextView.setText(R.string.zasady_lotu);
        else if(subject[i].trim().equals("90")) subjectTextView.setText(R.string.lacznosc);
        else if(subject[i].trim().equals("99")) subjectTextView.setText(R.string.bezpieczenstwo);
    }

    public int loadIndex(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getInt(INDEX_KEY, 0);
    }

    public void saveIndex(int i){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(INDEX_KEY, i);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void closeKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }


}


