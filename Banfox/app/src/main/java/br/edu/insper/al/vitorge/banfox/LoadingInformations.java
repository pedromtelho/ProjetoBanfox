package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.nio.ByteBuffer;

public class LoadingInformations extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mLoadingTextTwo;
    private TextView mLoadingTextThree;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_informations);

        String sourceImage = ((Global) this.getApplication()).getFacePicture();
        String targetImage = ((Global) this.getApplication()).getGroupPicture();

        FaceCompare faceCompare = new FaceCompare();
        faceCompare.compareFaces(sourceImage, targetImage);

        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.LoadingSendingTextView);
        mLoadingTextTwo = findViewById(R.id.LoadingMiddleTextView);
        mLoadingTextThree = findViewById(R.id.LoadingFinalTextView);


    }
}
