package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceivedInfoActivity extends AppCompatActivity {

    Float infoScore;
    Float faceMatch;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_info);

        // Aqui pegamos a variável que indica se os faceMatch's foram um sucesso ou não.
        faceMatch = ((Global) this.getApplication()).getFaceMatch();
        faceMatch = faceMatch / 4;
        System.out.println("Facial: " + faceMatch);
        infoScore = ((Global) this.getApplication()).getInfoMatch();
        System.out.println("Nome: " + infoScore);
        boolean success = (faceMatch > (float) 0.5) && (infoScore > (float) 0.5);
        System.out.println(((Global) this.getApplication()).getTextDetected());

        // Inicializando os widgets.
        // Declarando todos os widgets usados nessa tela.
        View icon_view = findViewById(R.id.icon_view);
        TextView done_textView = findViewById(R.id.done_textView);
        TextView info_textView = findViewById(R.id.info_textView);
        TextView thank_textView = findViewById(R.id.thank_textView);
        Button home_btn = findViewById(R.id.home_btn);

        new PostRequest().execute();

        // Checamos se as informações foram recebidas com sucesso e apartir disso
        // decidimos exibir uma tela de sucesso ou de erro.
        if (success) {
            // Mudando o ícone exibido para o de sucesso.
            icon_view.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp));

            // Mudando o texto a ser exibido.
            done_textView.setText(R.string.scr_received_info_success1);

            // Mudando o texto a ser exibido.
            info_textView.setText(R.string.scr_received_info_success2);

            // Mudando o texto a ser exibido.
            thank_textView.setText(R.string.scr_received_info_success3);

        } else {
            // Mudando o ícone exibido para o de erro.
            icon_view.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_error_black_24dp));

            // Mudando o texto a ser exibido.
            done_textView.setText(R.string.scr_received_info_error1);

            // Mudando o texto a ser exibido.
            info_textView.setText(R.string.scr_received_info_error2);

            // Mudando o texto a ser exibido.
            thank_textView.setText(R.string.scr_received_info_error3);
        }


        // Criando um listener para o botão de voltar ao início.
        home_btn.setOnClickListener(view -> {
            // Criamos a intent para aa mudança de telas.
            Intent homeScrIntent = new Intent(ReceivedInfoActivity.this, MainActivity.class);

            // Iniciamos a activity.
            startActivity(homeScrIntent);
        });
    }

}
