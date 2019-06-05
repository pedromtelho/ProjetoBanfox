package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceivedInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_info);

        // Aqui pegamos a variável que indica se os faceMatch's foram um sucesso ou não.
        Float faceMatch = ((Global) this.getApplication()).getFaceMatch();
        faceMatch = faceMatch / 4;
        System.out.println("[LOGG] Match facial: " + faceMatch);
        Float infoScore = ((Global) this.getApplication()).getInfoMatch();
        System.out.println("[LOGG] Score de informações: " + infoScore);
        System.out.println("[LOGG] Texto detectado:\n" + ((Global) this.getApplication()).getTextDetected());

        // Inicializando os widgets.
        // Declarando todos os widgets usados nessa tela.
        Button home_btn = findViewById(R.id.home_btn);
        View icon_view = findViewById(R.id.icon_view);
        TextView done_textView = findViewById(R.id.done_textView);
        TextView info_textView = findViewById(R.id.info_textView);
        TextView thank_textView = findViewById(R.id.thank_textView);

        boolean success = ((Global) this.getApplication()).isInfoSentSuccessfully();

        if (success) {
            // Mudando o ícone exibido para o de sucesso.
            icon_view.setBackground(ContextCompat.getDrawable(LoadingInformations.getmContext(), R.drawable.ic_check_black_24dp));
            // Mudando o texto a ser exibido.
            done_textView.setText(R.string.scr_received_info_success1);
            // Mudando o texto a ser exibido.
            info_textView.setText(R.string.scr_received_info_success2);
            // Mudando o texto a ser exibido.
            thank_textView.setText(R.string.scr_received_info_success3);
        }
        else {
            // Mudando o ícone exibido para o de erro.
            icon_view.setBackground(ContextCompat.getDrawable(LoadingInformations.getmContext(), R.drawable.ic_error_black_24dp));
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
