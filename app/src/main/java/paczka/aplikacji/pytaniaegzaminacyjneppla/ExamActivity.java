package paczka.aplikacji.pytaniaegzaminacyjneppla;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExamActivity extends Activity implements View.OnClickListener {

    //q&a array
    String[] question, correct_answer, ansB, ansC, ansD;

    int[][] whichanswhere;

    ArrayList<Integer> localindex, whichbuttonclicked;

    int globalindex, nrofquestions, pointsscored;

    //Layout Bars
    TextView questionTextView, questionnumberTextView, resultTextView, percentTextView, bravoTextView;
    Button nextButton, prevButton, ansAButton, ansBButton, ansCButton, ansDButton;

    public static boolean back_pressed_twice;

    private AdView madView, madView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_choose_subject_layout);
        back_pressed_twice=true;
        localindex = new ArrayList<>();
        whichbuttonclicked = new ArrayList<>();

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
                nrofquestions=12;
                StartSubjectLearn("Bezpieczenstwo");
                break;
            case R.id.wiedzaosamolocieButton:
                nrofquestions=16;
                StartSubjectLearn("Wiedza o samolocie");
                break;
            case R.id.prawolotniczeButton:
                nrofquestions=28;
                StartSubjectLearn("Prawo");
                break;
            case R.id.osiagiButton:
                nrofquestions=20;
                StartSubjectLearn("Osiagi");
                break;
            case R.id.czlowiekButton:
                nrofquestions=12;
                StartSubjectLearn("Czlowiek");
                break;
            case R.id.nawigacjaButton:
                nrofquestions=24;
                StartSubjectLearn("Navi");
                break;
            case R.id.proceduryButton:
                nrofquestions=12;
                StartSubjectLearn("Procedury");
                break;
            case R.id.zasadylotuButton:
                nrofquestions=16;
                StartSubjectLearn("Zasady");
                break;
            case R.id.lacznoscButton:
                nrofquestions=12;
                StartSubjectLearn("Lacznosc");
                break;
            case R.id.meteoButton:
                nrofquestions=12;
                StartSubjectLearn("Meteo");
                break;
        }
    }


    /*SETTING ACTIONS FOR EACH SUBJECT*/
    /*SETTING ACTIONS FOR EACH SUBJECT*/

    public void StartSubjectLearn(String subject_name){

        back_pressed_twice=false;
        globalindex=0;
        pointsscored=0;
        whichanswhere= new int[nrofquestions][4];
        for(int i=0; i<nrofquestions; i++) whichbuttonclicked.add(i,5);


        setContentView(R.layout.exam_layout);

        //load ad
        madView2 = findViewById(R.id.adView_exam);
        AdRequest adRequest = new AdRequest.Builder().build();
        madView2.loadAd(adRequest);

        //initialize layout elements
        questionTextView = findViewById(R.id.questionexamTextView);
        questionnumberTextView = findViewById(R.id.questionnumberexamTextView);
        nextButton = findViewById(R.id.rightarrowexamButton);
        prevButton = findViewById(R.id.leftarrowexamButton);
        ansAButton = findViewById(R.id.ans1examButton);
        ansBButton = findViewById(R.id.ans2examButton);
        ansCButton = findViewById(R.id.ans3examButton);
        ansDButton = findViewById(R.id.ans4examButton);

        SetArrays(subject_name);
        generaterandomindexes(question.length-1);
        randomAnswerOrder();
        questionnumberTextView.setText((globalindex+1)+"/"+(nrofquestions));
        questionTextView.setText(question[localindex.get(globalindex)].trim());
        setTextToButtons();

        //set actions for buttons
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
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
            case R.id.rightarrowexamButton:
                if(globalindex<nrofquestions-1){
                    globalindex++;
                    questionnumberTextView.setText((globalindex+1)+"/"+(nrofquestions));
                    questionTextView.setText(question[localindex.get(globalindex)].trim());
                    setDefaultColors();
                    try {
                        if (whichbuttonclicked.get(globalindex) == 0)
                            ansAButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 1)
                            ansBButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 2)
                            ansCButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 3)
                            ansDButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    }catch (Exception e){
                    }
                    setTextToButtons();
                }
                break;
            case R.id.leftarrowexamButton:
                if(globalindex>0){
                    globalindex--;
                    questionnumberTextView.setText((globalindex+1)+"/"+(nrofquestions));
                    questionTextView.setText(question[localindex.get(globalindex)].trim());
                    setDefaultColors();
                    try {
                        if (whichbuttonclicked.get(globalindex) == 0)
                            ansAButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 1)
                            ansBButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 2)
                            ansCButton.setBackgroundColor(Color.parseColor("#0850b9"));
                        else if (whichbuttonclicked.get(globalindex) == 3)
                            ansDButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    }catch (Exception e){
                    }
                    setTextToButtons();
                }
                break;
            case R.id.ans1examButton:
                    ansAButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    ansBButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansCButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansDButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    whichbuttonclicked.set(globalindex, 0);
                break;
            case R.id.ans2examButton:
                    ansBButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    ansAButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansCButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansDButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    whichbuttonclicked.set(globalindex, 1);
                break;
            case R.id.ans3examButton:
                    ansCButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    ansAButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansBButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansDButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    whichbuttonclicked.set(globalindex, 2);
                break;
            case R.id.ans4examButton:
                    ansDButton.setBackgroundColor(Color.parseColor("#0850b9"));
                    ansAButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansBButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    ansCButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
                    whichbuttonclicked.set(globalindex, 3);
                break;
        }
    }

    //setting random order for answers to make game a bit harder
    public void randomAnswerOrder(){
        int j, i;
        Integer[] nr = {1, 2, 3, 4};
        List<Integer> intList = Arrays.asList(nr);
        for(i=0; i<nrofquestions; i++) {
            Collections.shuffle(intList);

            for(j=0; j<4; j++){
                whichanswhere[i][j]=intList.get(j);
            }
        }
    }

    //save index while pressing back
    @Override
    public void onBackPressed() {
        if(!back_pressed_twice){
            back_pressed_twice=true;
            setContentView(R.layout.test_choose_subject_layout);
            madView = findViewById(R.id.adView_test_choose_subject);
            AdRequest adRequest = new AdRequest.Builder().build();
            madView.loadAd(adRequest);
            clearArrays();
        }
        else{
            finish();
        }
    }

    //generating random numbers as indexes to shuffle questions
    public void generaterandomindexes(int questionsnrinsubject){
        int i;
        ArrayList<Integer> list = new ArrayList<>();
        for (i=0; i<questionsnrinsubject; i++) list.add(i);
        Collections.shuffle(list);
        for(i=0; i<nrofquestions; i++) localindex.add(i, list.get(i));
    }

    //which answer to show on which button
    public String whichTextToButtons(int buttonnr){
        if(whichanswhere[globalindex][buttonnr]==1) return correct_answer[localindex.get(globalindex)];
        else if(whichanswhere[globalindex][buttonnr]==2) return ansB[localindex.get(globalindex)];
        else if(whichanswhere[globalindex][buttonnr]==3) return ansC[localindex.get(globalindex)];
        else if(whichanswhere[globalindex][buttonnr]==4) return ansD[localindex.get(globalindex)];
        else return null;
    }

    //set text to buttons
    public void setTextToButtons(){
        ansAButton.setText(whichTextToButtons(0));
        ansBButton.setText(whichTextToButtons(1));
        ansCButton.setText(whichTextToButtons(2));
        ansDButton.setText(whichTextToButtons(3));
    }

    //clearing all arrays
    public void clearArrays(){
        int j, i;
        for (i=0; i<whichanswhere.length; i++){
            for(j=0; j<4; j++){
                whichanswhere[i][j]= 0;
            }
        }
        localindex.clear();
        whichbuttonclicked.clear();
    }

    public void setDefaultColors(){
        ansAButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansBButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansCButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
        ansDButton.setBackgroundColor(Color.parseColor("#19FFFFFF"));
    }

    //checking answers to get how many are correct
    public int getScoredPoints(int a){
        int i;
        for(i=0; i<nrofquestions; i++){
            try {
                if (whichanswhere[i][whichbuttonclicked.get(i)] == 1) a++;
            }catch (Exception e){
            }
        }
        return a;
    }

    public void showresults(){
        double percent;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        percent= ((double) getScoredPoints(pointsscored)/nrofquestions* 100);
        if(percent<75) {
            bravoTextView.setTextColor(Color.parseColor("#b20909"));
            bravoTextView.setText(R.string.bravo_text_view_negative);
        }
        resultTextView.setText((getScoredPoints(pointsscored))+"/"+(nrofquestions));
        percentTextView.setText((df.format(percent))+"%");
    }

    public void onResultClicked(View v){
        switch (v.getId()){
            case R.id.checkanswersButton:
                    setContentView(R.layout.result_show_layout);
                    resultTextView= findViewById(R.id.resultTextView);
                    percentTextView= findViewById(R.id.percentageTextView);
                    bravoTextView= findViewById(R.id.bravoTextView);
                    showresults();
                break;

        }
    }
}