package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExplanationActivity extends AppCompatActivity {

    // Widgets
    private Button explanationAdvanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        // Assigning the view's button to the class's attribute.
        explanationAdvanceButton = findViewById(R.id.explanationAdvanceButton);

        // Crating a click listener for the button.
        explanationAdvanceButton.setOnClickListener((view) -> {
            // Creating an intent to change activity.
            Intent intent = new Intent(ExplanationActivity.this, Reader.class);

            // Starting the activity.
            startActivity(intent);
        });
    }
}
