package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class FunctionalityInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_guide1);

        // Getting which picture the user is to take next.
        int pictureNumber = ((Global) this.getApplication()).getPictureNumber();

        // Determine the layout to show based o which picture the user has to take next.
        switch (pictureNumber) {
            case 0:
                setContentView(R.layout.activity_picture_guide1);
                break;
            case 1:
                setContentView(R.layout.activity_picture_guide2);
                break;
            case 2:
                setContentView(R.layout.activity_picture_guide3);
                break;
            default:
                break;
        }

        // Assigning the view's button to the class's attribute.
        // Widgets
        Button nextScr_btn = findViewById(R.id.nextScr_btn);

        // Creating a click listener for the button.
        nextScr_btn.setOnClickListener((view) -> {
            // Creating an intent to change activity.
            Intent intent = new Intent(FunctionalityInformation.this, CamOne.class);

            // Starting the activity.
            startActivity(intent);
        });
    }
}
