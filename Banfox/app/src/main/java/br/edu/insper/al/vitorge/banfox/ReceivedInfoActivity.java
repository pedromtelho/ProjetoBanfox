package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ReceivedInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_info);

        // Aqui pegamos a variável que indica se os faceMatch's foram um sucesso ou não.
        Float faceMatch = ((Global) this.getApplication()).getFaceMatch();
        faceMatch = faceMatch / 4;
        System.out.println("Facial: " + faceMatch);
        Float infoScore = ((Global) this.getApplication()).getInfoMatch();
        System.out.println("Nome: " + infoScore);
        System.out.println(((Global) this.getApplication()).getTextDetected());

        // Inicializando os widgets.
        // Declarando todos os widgets usados nessa tela.
        Button home_btn = findViewById(R.id.home_btn);

        new PostRequest().execute();

        // Criando um listener para o botão de voltar ao início.
        home_btn.setOnClickListener(view -> {
            // Criamos a intent para aa mudança de telas.
            Intent homeScrIntent = new Intent(ReceivedInfoActivity.this, MainActivity.class);

            // Iniciamos a activity.
            startActivity(homeScrIntent);
        });
    }

}
