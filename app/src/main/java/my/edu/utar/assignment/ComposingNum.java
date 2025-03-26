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
    private int selectionsCount;

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
                if (selectionsCount < 2) {
                    Toast.makeText(ComposingNum.this, "Please select 2 numbers",
                            Toast.LENGTH_SHORT).show();
                } else {
                    handleConfirm();
                }
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionsCount > 0) {
                    undoLastSelection();
                } else {
                    Toast.makeText(ComposingNum.this, "No more inputs to undo",
                            Toast.LENGTH_SHORT).show();
                }
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
                randomNumber = random.nextInt(10); // Generate a random number between 0-9
            } while (options.contains(randomNumber));
            options.add(randomNumber);
        }

        // Shuffle the options
        Collections.shuffle(options);

        // Randomly select two numbers to be added or subtracted
        int num1Index = random.nextInt(4);
        int num2Index;
        do {
            num2Index = random.nextInt(4);
        } while (num2Index == num1Index);

        // Set the numbers and operation
        int selectedNum1 = options.get(num1Index);
        int selectedNum2 = options.get(num2Index);
        isAddition = random.nextBoolean();

        // Compute the answer based on whether it's an addition or subtraction equation
        answer = isAddition ? selectedNum1 + selectedNum2 : selectedNum1 - selectedNum2;

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

        // Reset selections count
        selectionsCount = 0;
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

        // Increment selections count
        selectionsCount++;
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
                Toast.makeText(this, "Incorrect! Try again.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            if (selectedNum1 - selectedNum2 == answer) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                generateEquation();
            } else {
                Toast.makeText(this, "Incorrect! Try again.",
                        Toast.LENGTH_SHORT).show();
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

        // Decrement selections count
        if (selectionsCount > 0) {
            selectionsCount--;
        }
    }
}