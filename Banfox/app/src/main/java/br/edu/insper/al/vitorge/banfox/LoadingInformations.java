package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.LoadingSendingTextView);
        mLoadingTextTwo = findViewById(R.id.LoadingMiddleTextView);
        mLoadingTextThree = findViewById(R.id.LoadingFinalTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mProgressStatus < 100){
                    mProgressStatus++;
                    SystemClock.sleep(70);

                    mHandler.post(new Runnable(){
                        @Override
                        public void run(){
                            mProgressBar.setProgress(mProgressStatus);
                            if (mProgressStatus > 0 && mProgressStatus<30){
                                mLoadingText.setVisibility(View.VISIBLE);
                            }

                            if (mProgressStatus > 35 && mProgressStatus < 60){
                                mLoadingText.setVisibility(View.INVISIBLE);
                                mLoadingTextTwo.setVisibility(View.VISIBLE);
                            }
                            else if (mProgressStatus > 60 && mProgressStatus < 100){
                                mLoadingTextTwo.setVisibility(View.INVISIBLE);
                                mLoadingTextThree.setVisibility(View.VISIBLE);
                            } else if (mProgressStatus == 100) {
                                Intent intent = new Intent(LoadingInformations.this, ReceivedInfoActivity.class);
                                intent.putExtra("success", true);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
