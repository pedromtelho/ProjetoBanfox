package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExplanationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        String name = ((Global) this.getApplication()).getUserName();
        Toast toast = Toast.makeText(this, name, Toast.LENGTH_LONG);
        toast.show();
        Button button = findViewById(R.id.explanationAdvanceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExplanationActivity.this, FunctionalityInformation.class));
            }
        });
    }
}
