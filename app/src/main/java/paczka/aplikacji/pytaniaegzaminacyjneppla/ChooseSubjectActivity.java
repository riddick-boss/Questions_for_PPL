package paczka.aplikacji.pytaniaegzaminacyjneppla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ChooseSubjectActivity extends Activity implements View.OnClickListener {

    public static String index_key;

    //q&a array
    String[] question, answer;

    int index, gotonr;

    //Layout Bars
    TextView questionTextView, answerTextView, questionnumberTextView, subjectTextView;
    Button nextButton, prevButton, gotoButton;
    EditText gotoEditText;

    public static boolean back_pressed_twice;

    private AdView madView, madView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.learn_choose_subject_layout);

        //initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        madView = findViewById(R.id.adView_learn_choose_subject);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);

        back_pressed_twice=true;



    }

    public void onChooseSubjectButtonClicked(View v) {
        switch (v.getId()){
            case R.id.wszystkiepytaniaButton:
                startActivity(new Intent(ChooseSubjectActivity.this, LearnActivity.class));
                break;
            case R.id.bezpieczenstwoButton:
                StartSubjectLearn("Bezpieczenstwo");
                break;
            case R.id.wiedzaosamolocieButton:
                StartSubjectLearn("Wiedza o samolocie");
                break;
            case R.id.prawolotniczeButton:
                StartSubjectLearn("Prawo");
                break;
            case R.id.osiagiButton:
                StartSubjectLearn("Osiagi");
                break;
            case R.id.czlowiekButton:
                StartSubjectLearn("Czlowiek");
                break;
            case R.id.nawigacjaButton:
                StartSubjectLearn("Navi");
                break;
            case R.id.proceduryButton:
                StartSubjectLearn("Procedury");
                break;
            case R.id.zasadylotuButton:
                StartSubjectLearn("Zasady");
                break;
            case R.id.lacznoscButton:
                StartSubjectLearn("Lacznosc");
                break;
            case R.id.meteoButton:
                StartSubjectLearn("Meteo");
                break;
        }
    }


    /*SETTING ACTIONS FOR EACH SUBJECT*/
    /*SETTING ACTIONS FOR EACH SUBJECT*/

    public void StartSubjectLearn(String subject_name){

            //load index
        try{
            index=loadIndex(subject_name);
        }
        catch (Exception e){
            index=0;
        }

            back_pressed_twice=false;

            setContentView(R.layout.learn_layout);

            //load ad
            madView2 = findViewById(R.id.adView_learn);
            AdRequest adRequest = new AdRequest.Builder().build();
            madView2.loadAd(adRequest);

            SetArrays(subject_name);



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
            subjectSetText(subject_name);

            //set actions for buttons
            nextButton.setOnClickListener(this);
            prevButton.setOnClickListener(this);
            gotoButton.setOnClickListener(this);

        }

    //setting appropriate arrays to subjects
    public void SetArrays(String name_of_subject){
        if(name_of_subject.equals("Prawo")){
            question = getResources().getStringArray(R.array.prawo_questions);
            answer = getResources().getStringArray(R.array.prawo_answers);
        }
        else if(name_of_subject.equals("Bezpieczenstwo")){
            question = getResources().getStringArray(R.array.bezpieczenstwo_questions);
            answer = getResources().getStringArray(R.array.bez_answers);
        }
        else if(name_of_subject.equals("Wiedza o samolocie")){
            question = getResources().getStringArray(R.array.wiedza_questions);
            answer = getResources().getStringArray(R.array.wiedza_answers);
        }
        else if(name_of_subject.equals("Osiagi")){
            question = getResources().getStringArray(R.array.osiagi_questions);
            answer = getResources().getStringArray(R.array.osiagi_answers);
        }
        else if(name_of_subject.equals("Czlowiek")){
            question = getResources().getStringArray(R.array.czlowiek_questions);
            answer = getResources().getStringArray(R.array.czlowiek_answers);
        }
        else if(name_of_subject.equals("Navi")){
            question = getResources().getStringArray(R.array.navi_questions);
            answer = getResources().getStringArray(R.array.navi_answers);
        }
        else if(name_of_subject.equals("Procedury")){
            question = getResources().getStringArray(R.array.procedury_questions);
            answer = getResources().getStringArray(R.array.procedury_answers);
        }
        else if(name_of_subject.equals("Zasady")){
            question = getResources().getStringArray(R.array.zasady_questions);
            answer = getResources().getStringArray(R.array.zasady_answers);
        }
        else if(name_of_subject.equals("Lacznosc")){
            question = getResources().getStringArray(R.array.lacznosc_questions);
            answer = getResources().getStringArray(R.array.lacznosci_answers);
        }
        else if(name_of_subject.equals("Meteo")){
            question = getResources().getStringArray(R.array.meteo_questions);
            answer = getResources().getStringArray(R.array.meteo_answers);
        }
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
                }
                else{
                    index=0;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                }
                break;
            case R.id.leftarrowlearnButton:
                if(index>0){
                    index--;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                }
                else{
                    index=question.length-1;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
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
                    index--;
                } else if (gotonr == 1) {
                    index = 0;
                    questionnumberTextView.setText((index + 1) + "/" + (question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                } else if (gotonr == question.length) {
                    index = question.length - 1;
                    questionnumberTextView.setText((index + 1) + "/" + (question.length));
                    questionTextView.setText(question[index].trim());
                    answerTextView.setText(answer[index].trim());
                }
                gotoEditText.setText(null);
                gotoEditText.clearFocus();
                break;

        }
    }

    public void subjectSetText(String name_of_subject){
        if(name_of_subject.equals("Prawo")) subjectTextView.setText(R.string.prawo_lotnicze);
        else if(name_of_subject.equals("Bezpieczenstwo")) subjectTextView.setText(R.string.bezpieczenstwo);
        else if(name_of_subject.equals("Wiedza o samolocie")) subjectTextView.setText(R.string.wiedza_o_samolocie);
        else if(name_of_subject.equals("Osiagi")) subjectTextView.setText(R.string.osiagi);
        else if(name_of_subject.equals("Czlowiek")) subjectTextView.setText(R.string.czlowiek);
        else if(name_of_subject.equals("Navi")) subjectTextView.setText(R.string.nawigacja);
        else if(name_of_subject.equals("Procedury")) subjectTextView.setText(R.string.procedury_operacyjne);
        else if(name_of_subject.equals("Zasady")) subjectTextView.setText(R.string.zasady_lotu);
        else if(name_of_subject.equals("Lacznosc")) subjectTextView.setText(R.string.lacznosc);
        else if(name_of_subject.equals("Meteo")) subjectTextView.setText(R.string.meteo);
    }

    public int loadIndex(String name_of_subject){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(name_of_subject.equals("Prawo")) index_key="Prawo_key";
        else if(name_of_subject.equals("Bezpieczenstwo")) index_key="Bezpieczenstwo_key";
        else if(name_of_subject.equals("Wiedza o samolocie")) index_key="Wiedza o samolocie_key";
        else if(name_of_subject.equals("Osiagi")) index_key="Osiagi_key";
        else if(name_of_subject.equals("Czlowiek")) index_key="Czlowiek_key";
        else if(name_of_subject.equals("Navi")) index_key="Navi_key";
        else if(name_of_subject.equals("Procedury")) index_key="Procedury_key";
        else if(name_of_subject.equals("Zasady")) index_key="Zasady_key";
        else if(name_of_subject.equals("Lacznosc")) index_key="Lacznosc_key";
        else if(name_of_subject.equals("Meteo")) index_key="Meteo_key";
        return prefs.getInt(index_key, 0);
    }

    public void saveIndex(int i){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(index_key, i);
        editor.apply();
        index_key=null;
    }

    //save index while pressing back
    @Override
    public void onBackPressed() {
        if(!back_pressed_twice){
            saveIndex(index);
            back_pressed_twice=true;
            setContentView(R.layout.learn_choose_subject_layout);
            madView = findViewById(R.id.adView_learn_choose_subject);
            AdRequest adRequest = new AdRequest.Builder().build();
            madView.loadAd(adRequest);
        }
        else{
            finish();
        }
    }

    //save index on exiting app
    @Override
    protected void onPause() {
        super.onPause();
        saveIndex(index);
    }

    public void closeKeyboard(){
        View v = this.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }
}
