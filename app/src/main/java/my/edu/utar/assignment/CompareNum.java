package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CompareNum extends AppCompatActivity {

    private TextView instructionTextView;
    private Button buttonNumber1;
    private Button buttonNumber2;
    private Button backButton;
    private int number1;
    private int number2;
    private boolean isGreaterThan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_num);

        instructionTextView = findViewById(R.id.instrucTextView);
        buttonNumber1 = findViewById(R.id.button);
        buttonNumber2 = findViewById(R.id.button2);
        backButton = findViewById(R.id.backButton);

        generateNewNumbers();

        buttonNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGreaterThan) {
                    compareNumbers(number1, number2);
                } else {
                    compareNumbers(number2, number1);
                }
            }
        });

        buttonNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGreaterThan) {
                    compareNumbers(number2, number1);
                } else {
                    compareNumbers(number1, number2);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompareNum.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method to compare numbers
    private void compareNumbers(int firstNumber, int secondNumber) {
        if (firstNumber > secondNumber) {
            if (isGreaterThan) {
                showToast("Correct! " + firstNumber + " is bigger.");
            } else {
                showToast("Correct! " + secondNumber + " is smaller.");
            }
        } else if (firstNumber < secondNumber) {
            if (isGreaterThan) {
                showToast("Incorrect! " + secondNumber + " is bigger.");
            } else {
                showToast("Incorrect! " + firstNumber + " is smaller.");
            }
        } else {
            showToast("Both numbers are equal.");
        }
        generateNewNumbers();
    }

    private void generateNewNumbers() {
        Random random = new Random();
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);
        isGreaterThan = random.nextBoolean(); // Randomly select comparison type
        instructionTextView.setText(isGreaterThan ? "Choose the bigger number." : "Choose the smaller number.");
        buttonNumber1.setText(String.valueOf(number1));
        buttonNumber2.setText(String.valueOf(number2));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}