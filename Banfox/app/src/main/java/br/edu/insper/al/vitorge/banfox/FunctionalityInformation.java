package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FunctionalityInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionality_information);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView textView = findViewById(R.id.textView2);
        textView.setText(name + ", Tire uma foto do seu rosto!");

        Button button = findViewById(R.id.functionality1_btn);
        button.setOnClickListener(v -> startActivity(new Intent(FunctionalityInformation.this, FunctionalityInformationTwo.class)));
    }
}
