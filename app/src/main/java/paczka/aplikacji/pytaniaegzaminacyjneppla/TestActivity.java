package paczka.aplikacji.pytaniaegzaminacyjneppla;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestActivity extends Activity implements View.OnClickListener {

    private String index_key;

    //q&a array
    String[] question, correct_answer, ansB, ansC, ansD;

    private int index, gotonr;

    //Layout Bars
    TextView questionTextView, questionnumberTextView;
    Button nextButton, prevButton, gotoButton, ansAButton, ansBButton, ansCButton, ansDButton;
    EditText gotoEditText;

    private boolean iscorrectA=false, iscorrectB=false, iscorrectC=false, iscorrectD=false;

    private boolean back_pressed_twice;

    private AdView madView, madView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_choose_subject_layout);
        back_pressed_twice=true;

        //initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        madView = findViewById(R.id.adView_test_choose_subject);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView.loadAd(adRequest);
    }

    public void onChooseSubjectButtonClicked(View v) {
        switch (v.getId()){
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

        setContentView(R.layout.test_layout);

        //load ad
        madView2 = findViewById(R.id.adView_test);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView2.loadAd(adRequest);

        SetArrays(subject_name);



        //initialize layout elements
        questionTextView = (TextView) findViewById(R.id.questiontestTextView);
        questionnumberTextView = (TextView) findViewById(R.id.questionnumbertestTextView);
        gotoEditText = (EditText) findViewById(R.id.gototestEditText);
        nextButton = (Button) findViewById(R.id.rightarrowtestButton);
        prevButton = (Button) findViewById(R.id.leftarrowtestButton);
        gotoButton = (Button) findViewById(R.id.gototestButton);
        ansAButton = (Button) findViewById(R.id.ans1testButton);
        ansBButton = (Button) findViewById(R.id.ans2testButton);
        ansCButton = (Button) findViewById(R.id.ans3testButton);
        ansDButton = (Button) findViewById(R.id.ans4testButton);

        questionnumberTextView.setText((index+1)+"/"+(question.length));
        questionTextView.setText(question[index]);
        randomAnswerOrder();

        //set actions for buttons
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        gotoButton.setOnClickListener(this);
        ansAButton.setOnClickListener(this);
        ansBButton.setOnClickListener(this);
        ansCButton.setOnClickListener(this);
        ansDButton.setOnClickListener(this);

    }

    //setting appropriate arrays to subjects
    public void SetArrays(String name_of_subject){
        if(name_of_subject.equals("Prawo")){
            question = getResources().getStringArray(R.array.prawo_questions);
            correct_answer = getResources().getStringArray(R.array.prawo_answers);
            ansB = getResources().getStringArray(R.array.prawo_odp_b);
            ansC = getResources().getStringArray(R.array.prawo_odp_c);
            ansD = getResources().getStringArray(R.array.prawo_odp_d);
        }
        else if(name_of_subject.equals("Bezpieczenstwo")){
            question = getResources().getStringArray(R.array.bezpieczenstwo_questions);
            correct_answer = getResources().getStringArray(R.array.bez_answers);
            ansB = getResources().getStringArray(R.array.bez_odp_b);
            ansC = getResources().getStringArray(R.array.bez_odp_c);
            ansD = getResources().getStringArray(R.array.bez_odp_d);
        }
        else if(name_of_subject.equals("Wiedza o samolocie")){
            question = getResources().getStringArray(R.array.wiedza_questions);
            correct_answer = getResources().getStringArray(R.array.wiedza_answers);
            ansB = getResources().getStringArray(R.array.wiedza_odp_b);
            ansC = getResources().getStringArray(R.array.wiedza_odp_c);
            ansD = getResources().getStringArray(R.array.wiedza_odp_d);
        }
        else if(name_of_subject.equals("Osiagi")){
            question = getResources().getStringArray(R.array.osiagi_questions);
            correct_answer = getResources().getStringArray(R.array.osiagi_answers);
            ansB = getResources().getStringArray(R.array.osiagi_odp_b);
            ansC = getResources().getStringArray(R.array.osiagi_odp_c);
            ansD = getResources().getStringArray(R.array.osiagi_odp_d);
        }
        else if(name_of_subject.equals("Czlowiek")){
            question = getResources().getStringArray(R.array.czlowiek_questions);
            correct_answer = getResources().getStringArray(R.array.czlowiek_answers);
            ansB = getResources().getStringArray(R.array.czlowiek_odp_b);
            ansC = getResources().getStringArray(R.array.czlowiek_odp_c);
            ansD = getResources().getStringArray(R.array.czlowiek_odp_d);
        }
        else if(name_of_subject.equals("Navi")){
            question = getResources().getStringArray(R.array.navi_questions);
            correct_answer = getResources().getStringArray(R.array.navi_answers);
            ansB = getResources().getStringArray(R.array.navi_odp_b);
            ansC = getResources().getStringArray(R.array.navi_odp_c);
            ansD = getResources().getStringArray(R.array.navi_odp_d);
        }
        else if(name_of_subject.equals("Procedury")){
            question = getResources().getStringArray(R.array.procedury_questions);
            correct_answer = getResources().getStringArray(R.array.procedury_answers);
            ansB = getResources().getStringArray(R.array.procedury_odp_b);
            ansC = getResources().getStringArray(R.array.procedury_odp_c);
            ansD = getResources().getStringArray(R.array.procedury_odp_d);
        }
        else if(name_of_subject.equals("Zasady")){
            question = getResources().getStringArray(R.array.zasady_questions);
            correct_answer = getResources().getStringArray(R.array.zasady_answers);
            ansB = getResources().getStringArray(R.array.zasady_odp_b);
            ansC = getResources().getStringArray(R.array.zasady_odp_c);
            ansD = getResources().getStringArray(R.array.zasady_odp_d);
        }
        else if(name_of_subject.equals("Lacznosc")){
            question = getResources().getStringArray(R.array.lacznosc_questions);
            correct_answer = getResources().getStringArray(R.array.lacznosci_answers);
            ansB = getResources().getStringArray(R.array.lacznosc_odp_b);
            ansC = getResources().getStringArray(R.array.lacznosc_odp_c);
            ansD = getResources().getStringArray(R.array.lacznosc_odp_d);
        }
        else if(name_of_subject.equals("Meteo")){
            question = getResources().getStringArray(R.array.meteo_questions);
            correct_answer = getResources().getStringArray(R.array.meteo_answers);
            ansB = getResources().getStringArray(R.array.meteo_odp_b);
            ansC = getResources().getStringArray(R.array.meteo_odp_c);
            ansD = getResources().getStringArray(R.array.meteo_odp_d);
        }
    }

    //actions on click buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rightarrowtestButton:
                setAllBoolsToFalseandDefaultColors();
                if(index<question.length-1){
                    index++;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                }
                else{
                    index=0;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                }
                break;
            case R.id.leftarrowtestButton:
                setAllBoolsToFalseandDefaultColors();
                if(index>0){
                    index--;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                }
                else{
                    index=question.length-1;
                    questionnumberTextView.setText((index+1)+"/"+(question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                }
                break;
            case R.id.gototestButton:
                try {
                    gotonr = Integer.parseInt(gotoEditText.getText().toString());
                    closeKeyboard();
                    setAllBoolsToFalseandDefaultColors();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter number!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (gotonr > 1 && gotonr < question.length - 1) {
                    index = gotonr;
                    questionnumberTextView.setText((index) + "/" + (question.length));
                    index--;
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();

                } else if (gotonr == 1) {
                    index = 0;
                    questionnumberTextView.setText((index + 1) + "/" + (question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                } else if (gotonr == question.length) {
                    index = question.length - 1;
                    questionnumberTextView.setText((index + 1) + "/" + (question.length));
                    questionTextView.setText(question[index].trim());
                    randomAnswerOrder();
                }
                gotoEditText.setText(null);
                gotoEditText.clearFocus();
                break;
            case R.id.ans1testButton:
                if(iscorrectA) {
                    ansAButton.setBackgroundColor(Color.parseColor("#018329"));
                } else ansAButton.setBackgroundColor(Color.parseColor("#b20909"));
                break;
            case R.id.ans2testButton:
                if(iscorrectB) {
                    ansBButton.setBackgroundColor(Color.parseColor("#018329"));
                } else ansBButton.setBackgroundColor(Color.parseColor("#b20909"));
                break;
            case R.id.ans3testButton:
                if(iscorrectC) {
                    ansCButton.setBackgroundColor(Color.parseColor("#018329"));
                } else ansCButton.setBackgroundColor(Color.parseColor("#b20909"));
                break;
            case R.id.ans4testButton:
                if(iscorrectD) {
                    ansDButton.setBackgroundColor(Color.parseColor("#018329"));
                } else ansDButton.setBackgroundColor(Color.parseColor("#b20909"));
                break;

        }
    }

    //setting random order for answers to make game a bit harder
    public void randomAnswerOrder(){
        Integer[] nr={0,1,2,3};
        List<Integer> intList = Arrays.asList(nr);
        Collections.shuffle(intList);
        intList.toArray(nr);

        if(nr[0]==0) {
            ansAButton.setText(correct_answer[index].trim());
            iscorrectA=true;
        }
        else if(nr[0]==1) ansAButton.setText(ansB[index].trim());
        else if(nr[0]==2) ansAButton.setText(ansC[index].trim());
        else if(nr[0]==3) ansAButton.setText(ansD[index].trim());

        if(nr[1]==0) {
            ansBButton.setText(correct_answer[index].trim());
            iscorrectB=true;
        }
        else if(nr[1]==1) ansBButton.setText(ansB[index].trim());
        else if(nr[1]==2) ansBButton.setText(ansC[index].trim());
        else if(nr[1]==3) ansBButton.setText(ansD[index].trim());

        if(nr[2]==0) {
            ansCButton.setText(correct_answer[index].trim());
            iscorrectC=true;
        }
        else if(nr[2]==1) ansCButton.setText(ansB[index].trim());
        else if(nr[2]==2) ansCButton.setText(ansC[index].trim());
        else if(nr[2]==3) ansCButton.setText(ansD[index].trim());

        if(nr[3]==0) {
            ansDButton.setText(correct_answer[index].trim());
            iscorrectD=true;
        }
        else if(nr[3]==1) ansDButton.setText(ansB[index].trim());
        else if(nr[3]==2) ansDButton.setText(ansC[index].trim());
        else if(nr[3]==3) ansDButton.setText(ansD[index].trim());
    }

    //set all booleans to false, necessary to show if you choose correct answer
    public void setAllBoolsToFalseandDefaultColors(){
        iscorrectA=false;
        iscorrectB=false;
        iscorrectC=false;
        iscorrectD=false;
        ansAButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansBButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansCButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansDButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
    }

    public int loadIndex(String name_of_subject){
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        if(name_of_subject.equals("Prawo")) index_key="Prawo_test_key";
        else if(name_of_subject.equals("Bezpieczenstwo")) index_key="Bezpieczenstwo_test_key";
        else if(name_of_subject.equals("Wiedza o samolocie")) index_key="Wiedza o samolocie_test_key";
        else if(name_of_subject.equals("Osiagi")) index_key="Osiagi_test_key";
        else if(name_of_subject.equals("Czlowiek")) index_key="Czlowiek_test_key";
        else if(name_of_subject.equals("Navi")) index_key="Navi_test_key";
        else if(name_of_subject.equals("Procedury")) index_key="Procedury_test_key";
        else if(name_of_subject.equals("Zasady")) index_key="Zasady_test_key";
        else if(name_of_subject.equals("Lacznosc")) index_key="Lacznosc_test_key";
        else if(name_of_subject.equals("Meteo")) index_key="Meteo_test_key";
        return prefs.getInt(index_key, 0);
    }

    public void saveIndex(int i){
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        prefs.edit().putInt(index_key, i).commit();
    }

    //save index while pressing back
    @Override
    public void onBackPressed() {
        if(!back_pressed_twice){
            saveIndex(index);
            back_pressed_twice=true;
            setContentView(R.layout.test_choose_subject_layout);
            madView = findViewById(R.id.adView_test_choose_subject);
            AdRequest adRequest = new AdRequest.Builder().build();
            madView.loadAd(adRequest);
            setAllBoolsToFalseandDefaultColors();
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
