package paczka.aplikacji.pytaniaegzaminacyjneppla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout Bars
    Button learnButton, testButton, examButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        //initialize layout elements
        learnButton = (Button) findViewById(R.id.learnButton);
        testButton = (Button) findViewById(R.id.testButton);
        examButton = findViewById(R.id.examButton);

        //set actions for buttons
        learnButton.setOnClickListener(this);
        testButton.setOnClickListener(this);
        examButton.setOnClickListener(this);
    }

    //actions on click buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.learnButton:
                startActivity(new Intent(MainActivity.this, ChooseSubjectActivity.class));
                break;
            case R.id.testButton:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
            case R.id.examButton:
                startActivity(new Intent(MainActivity.this, ExamActivity.class));
                break;
        }
    }
}