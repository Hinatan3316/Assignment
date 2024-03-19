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

public class OrderingNum extends AppCompatActivity {

    private Button num1Button, num2Button, num3Button, num4Button, backButton, undoButton;;
    private List<Button> numberButtons;
    private List<Integer> numbers;
    private List<Integer> userInput;
    private boolean ascendingOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_num);

        num1Button = findViewById(R.id.num1Button);
        num2Button = findViewById(R.id.num2Button);
        num3Button = findViewById(R.id.num3Button);
        num4Button = findViewById(R.id.num4Button);
        backButton = findViewById(R.id.backButton);
        undoButton = findViewById(R.id.undoButton);

        numberButtons = new ArrayList<>();
        numberButtons.add(num1Button);
        numberButtons.add(num2Button);
        numberButtons.add(num3Button);
        numberButtons.add(num4Button);

        numbers = new ArrayList<>();
        userInput = new ArrayList<>();

        generateRandomNumbers();

        for (final Button button : numberButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(button);
                }
            });
        }

        Button confirmButton = findViewById(R.id.confirmButton);
        // Set click listener for each number button
        for (final Button button : numberButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleButtonClick(button);
                }
            });
        }

        // Set click listener for the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInput.size() == 4) {
                    // Handle confirm button click when all numbers are input
                    handleConfirm();
                } else {
                    Toast.makeText(OrderingNum.this, "Please select all 4 numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderingNum.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity when navigating back
            }
        });


        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastInput();
            }
        });

        String order = ascendingOrder ? "ascending" : "descending";
        String initialInstruction = "Order the numbers in \n" + order + " order";
        ((TextView) findViewById(R.id.instrucTextView)).setText(initialInstruction);
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        numbers.clear();

        // Generate unique random numbers
        while (numbers.size() < 4) {
            int randomNumber = random.nextInt(100); // Generate a random number between 0 and 99
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }

        // Shuffle the numbers to scramble them
        Collections.shuffle(numbers);

        // Decide whether to order in ascending or descending order
        ascendingOrder = random.nextBoolean();

        // Set numbers to buttons
        for (int i = 0; i < numberButtons.size(); i++) {
            Button button = numberButtons.get(i);
            button.setText(String.valueOf(numbers.get(i)));
            button.setVisibility(View.VISIBLE); // Make sure all buttons are visible
        }
    }

    private void handleButtonClick(Button button) {
        if (userInput.size() < 4) {
            int number = Integer.parseInt(button.getText().toString());
            userInput.add(number);
            button.setVisibility(View.GONE); // Hide the button after selecting the number
            updateInputs();
        }
    }

    private void handleConfirm() {
        // Compare userInput with the correct order of numbers
        List<Integer> correctOrder = new ArrayList<>(numbers);

        // Sort the correct order according to the chosen order (ascending or descending)
        if (!ascendingOrder) {
            Collections.sort(correctOrder, Collections.reverseOrder());
        } else {
            Collections.sort(correctOrder);
        }

        // Check if userInput matches the correct order
        if (userInput.equals(correctOrder)) {
            // User input is correct
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            // User input is incorrect
            Toast.makeText(this, "Incorrect! Please try again.", Toast.LENGTH_SHORT).show();
        }

        // Reset userInput and display buttons again
        userInput.clear();
        generateRandomNumbers();
        updateInputs();
    }

    private void undoLastInput() {
        if (!userInput.isEmpty()) {
            int lastNumber = userInput.remove(userInput.size() - 1);
            for (Button button : numberButtons) {
                if (button.getText().toString().equals(String.valueOf(lastNumber))) {
                    button.setVisibility(View.VISIBLE); // Show the button again
                    break; // Assuming each number appears only once
                }
            }
            updateInputs();
        } else {
            Toast.makeText(this, "No recent input to undo", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateInputs() {
        StringBuilder builder = new StringBuilder();
        for (int num : userInput) {
            builder.append(num).append(" ,  ");
        }
        String userNumbers = builder.toString();
        if (userNumbers.endsWith(" ,  ")) {
            userNumbers = userNumbers.substring(0, userNumbers.length() - 4); // Remove the trailing comma
        }
        String order = ascendingOrder ? "ascending" : "descending";
        String instruction = "Order the numbers in \n" + order + " order\n" + userNumbers;
        ((TextView) findViewById(R.id.instrucTextView)).setText(instruction);
    }
}