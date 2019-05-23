package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FunctionalityInformation extends AppCompatActivity {

    private String topText;
    private String bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functionality_information);

        String name = ((Global) this.getApplication()).getUserName();
        int pictureNumber = ((Global) this.getApplication()).getPictureNumber();
        ImageView imageView = findViewById(R.id.imageView);

        switch (pictureNumber) {
            case 0:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info1_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info1_bottom_btn_text);
                imageView.setImageResource(R.drawable.ic_selfie);
                break;
            case 1:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info2_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info2_bottom_btn_text);
                imageView.setImageResource(R.drawable.ic_identity_card);
                break;
            case 2:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info3_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info3_bottom_btn_text);
                imageView.setImageResource(R.drawable.ic_selfie);
                break;
            default:
                break;
        }

        TextView textView = findViewById(R.id.textView2);
        textView.setText(topText);

        TextView textView1 = findViewById(R.id.textView3);
        textView1.setText(bottomText);

        Button button = findViewById(R.id.functionality1_btn);
        button.setOnClickListener(v -> startActivity(new Intent(FunctionalityInformation.this, CamOne.class)));
    }
}
