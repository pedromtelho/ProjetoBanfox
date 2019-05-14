package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceivedInfoActivity extends AppCompatActivity {

    // Declarando todos os widgets usados nessa tela.
    private View icon_view;
    private TextView done_textView;
    private TextView info_textView;
    private TextView thank_textView;
    private Button home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_info);


        // Pegando o Intent que iniciou essa tela.
        Intent intent = getIntent();

        // Aqui estamos esperando que uma informação seja passada para essa tela,
        // que mostra se os dados enviados foram recebidos com sucesso ou não.
        // O valor padrão, case não seja recebido nada, é de ocorreu um erro.
        Boolean success = intent.getBooleanExtra("success", false);


        // Inicializando os widgets.
        this.icon_view = findViewById(R.id.icon_view);
        this.done_textView = findViewById(R.id.done_textView);
        this.info_textView = findViewById(R.id.info_textView);
        this.thank_textView = findViewById(R.id.thank_textView);
        this.home_btn = findViewById(R.id.home_btn);


        // Checamos se as informações foram recebidas com sucesso e apartir disso
        // decidimos exibir uma tela de sucesso ou de erro.
        if (success) {
            // Mudando o ícone exibido para o de sucesso.
            this.icon_view.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp));

            // Mudando o texto a ser exibido.
            this.done_textView.setText(R.string.scr_received_info_success1);

            // Mudando o texto a ser exibido.
            this.info_textView.setText(R.string.scr_received_info_success2);

            // Mudando o texto a ser exibido.
            this.thank_textView.setText(R.string.scr_received_info_success3);

        } else {
            // Mudando o ícone exibido para o de erro.
            this.icon_view.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_error_black_24dp));

            // Mudando o texto a ser exibido.
            this.done_textView.setText(R.string.scr_received_info_error1);

            // Mudando o texto a ser exibido.
            this.info_textView.setText(R.string.scr_received_info_error2);

            // Mudando o texto a ser exibido.
            this.thank_textView.setText(R.string.scr_received_info_error3);
        }


        // Criando um listener para o botão de voltar ao início.
        this.home_btn.setOnClickListener(view -> {
            // Criamos a intent para aa mudança de telas.
            Intent homeScrIntent = new Intent(ReceivedInfoActivity.this, MainActivity.class);

            // Iniciamos a activity.
            startActivity(homeScrIntent);
        });
    }
}
