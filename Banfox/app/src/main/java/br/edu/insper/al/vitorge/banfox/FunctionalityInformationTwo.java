package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class FunctionalityInformationTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionality_information2);

        Button button = findViewById(R.id.functionality2_btn);
        button.setOnClickListener(v -> startActivity(new Intent(FunctionalityInformationTwo.this, FunctionalityInformationThree.class)));
    }
}
