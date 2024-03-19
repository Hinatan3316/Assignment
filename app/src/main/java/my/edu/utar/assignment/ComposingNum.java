package my.edu.utar.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComposingNum extends AppCompatActivity {

    private TextView equationTextView;
    private List<Button> numberButtons;
    private Button confirmButton, undoButton, backButton;
    private int num1, num2, answer;
    private boolean isAddition;
    private String originalEquation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composing_num);

        equationTextView = findViewById(R.id.equationTextView);
        numberButtons = new ArrayList<>();
        numberButtons.add(findViewById(R.id.num1Button));
        numberButtons.add(findViewById(R.id.num2Button));
        numberButtons.add(findViewById(R.id.num3Button));
        numberButtons.add(findViewById(R.id.num4Button));
        confirmButton = findViewById(R.id.confirmButton);
        undoButton = findViewById(R.id.undoButton);
        backButton = findViewById(R.id.backButton);

        generateEquation();

        for (final Button button : numberButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleNumberSelection(button);
                }
            });
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleConfirm();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastSelection();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComposingNum.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity when navigating back
            }
        });
    }

    private void generateEquation() {
        Random random = new Random();

        // Generate unique random numbers for the options
        List<Integer> options = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int randomNumber;
            do {
                randomNumber = random.nextInt(10); // Generate a random number between 0 and 9
            } while (options.contains(randomNumber));
            options.add(randomNumber);
        }

        // Shuffle the options
        Collections.shuffle(options);

        // Select two numbers to be num1 and num2
        num1 = options.get(0);
        num2 = options.get(1);

        // Randomly select addition or subtraction
        isAddition = random.nextBoolean();

        // Compute the answer based on whether it's an addition or subtraction equation
        answer = isAddition ? num1 + num2 : num1 - num2;

        // Set the equation text view with placeholders
        originalEquation = "? " + (isAddition ? "+ " : "- ") + "? = " + answer;
        equationTextView.setText(originalEquation);

        // Set text for the number buttons
        for (int i = 0; i < numberButtons.size(); i++) {
            Button button = numberButtons.get(i);
            button.setText(String.valueOf(options.get(i)));
            button.setVisibility(View.VISIBLE);
        }

        // Make undo button visible
        undoButton.setVisibility(View.VISIBLE);
    }


    private void handleNumberSelection(Button button) {
        String buttonText = button.getText().toString();
        String equation = equationTextView.getText().toString();

        // Replace the first occurrence of "?" in the equation with the selected number
        equation = equation.replaceFirst("\\?", buttonText);

        // Update the equation text view
        equationTextView.setText(equation);

        // Hide the clicked button
        button.setVisibility(View.INVISIBLE);
    }

    private void handleConfirm() {
        // Parse the equation to get the selected numbers
        String equation = equationTextView.getText().toString();
        String[] parts = equation.split("\\s*[+=-]\\s*");
        int selectedNum1 = Integer.parseInt(parts[0]);
        int selectedNum2 = Integer.parseInt(parts[1]);

        // Check if the selected numbers match the correct numbers
        if (isAddition) {
            if (selectedNum1 + selectedNum2 == answer) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                generateEquation();
            } else {
                Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (selectedNum1 - selectedNum2 == answer) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                generateEquation();
            } else {
                Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void undoLastSelection() {
        // Revert the equation back to its original form
        equationTextView.setText(originalEquation);

        // Make all number buttons visible again
        for (Button button : numberButtons) {
            button.setVisibility(View.VISIBLE);
        }
    }
}