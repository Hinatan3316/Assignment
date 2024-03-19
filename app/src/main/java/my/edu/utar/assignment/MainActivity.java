package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPage1 = findViewById(R.id.button);
        Button buttonPage2 = findViewById(R.id.button2);
        Button buttonPage3 = findViewById(R.id.button3);

        buttonPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompareNum.class);
                startActivity(intent);
            }
        });

        buttonPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderingNum.class);
                startActivity(intent);
            }
        });

        buttonPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ComposingNum.class);
                startActivity(intent);
            }
        });
    }

    // Method to quit the app
    public void quitApp(View view) {
        finish(); // Finish the activity, which will close the app
    }
}