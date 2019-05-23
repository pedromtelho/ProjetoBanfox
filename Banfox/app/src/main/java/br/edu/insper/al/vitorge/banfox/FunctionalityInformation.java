package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView imageView1 = findViewById(R.id.imageView2);

        TextView textView = findViewById(R.id.textView2);
        TextView textView1 = findViewById(R.id.textView3);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) imageView1.getLayoutParams();

        switch (pictureNumber) {
            case 0:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info1_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info1_bottom_btn_text);
                imageView1.setVisibility(View.INVISIBLE);
                params1.weight = 0;
                imageView1.setLayoutParams(params1);
                params.weight = 100;
                params.setMarginStart(0);
                params.setMarginEnd(0);
                imageView.setLayoutParams(params);
                imageView.setImageResource(R.drawable.ic_selfie);
                break;
            case 1:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info2_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info2_bottom_btn_text);
                imageView1.setVisibility(View.INVISIBLE);
                params1.weight = 0;
                imageView1.setLayoutParams(params1);
                params.weight = 100;
                params.setMarginStart(0);
                params.setMarginEnd(0);
                imageView.setLayoutParams(params);
                imageView.setImageResource(R.drawable.ic_identity_card);
                break;
            case 2:
                topText = name + ", " + getResources().getString(R.string.scr_functionality_info3_top_btn_text);
                bottomText = getResources().getString(R.string.scr_functionality_info3_bottom_btn_text);
                imageView1.setVisibility(View.VISIBLE);
                params1.weight = 50;
                params1.setMarginEnd(30);
                imageView1.setLayoutParams(params1);
                params.weight = 50;
                params.setMarginStart(30);
                params.setMarginEnd(30);
                imageView.setLayoutParams(params);
                imageView1.setImageResource(R.drawable.ic_selfie_2);
                imageView.setImageResource(R.drawable.ic_identity_card);
                break;
            default:
                break;
        }

        textView.setText(topText);
        textView1.setText(bottomText);

        Button button = findViewById(R.id.functionality1_btn);
        button.setOnClickListener(v -> startActivity(new Intent(FunctionalityInformation.this, CamOne.class)));
    }
}
