package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.AsyncTask;
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
    private final Handler mHandler = new Handler();
    private static LoadingInformations mContext;

    private boolean face_id_compare_done = false;
    private boolean face_group_compare_done = false;
    private boolean id_group_compare_done = false;

    public static LoadingInformations getmContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_informations);
        mContext = this;

        String faceImage = ((Global) this.getApplication()).getFacePicture();
        String idImage = ((Global) this.getApplication()).getIdPicture();
        String groupImage = ((Global) this.getApplication()).getGroupPicture();

        //new FaceCompare().execute(faceImage, idImage, "1");
        FaceCompare face_id_compare = new FaceCompare();
        face_id_compare.execute(faceImage, idImage, "1");

        //new FaceCompare().execute(faceImage, groupImage, "2");
        FaceCompare face_group_compare = new FaceCompare();
        face_group_compare.execute(faceImage, groupImage, "2");

        //new FaceCompare().execute(idImage, groupImage, "2");
        FaceCompare id_group_compare = new FaceCompare();
        id_group_compare.execute(idImage, groupImage, "2");

        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.LoadingSendingTextView);
        mLoadingTextTwo = findViewById(R.id.LoadingMiddleTextView);
        mLoadingTextThree = findViewById(R.id.LoadingFinalTextView);

        new Thread(() -> {
            while (mProgressStatus < 100) {
                if (face_id_compare.isDone() && !face_id_compare_done) {
                    mProgressStatus += 33;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            mLoadingText.setVisibility(View.VISIBLE);
                        }
                    });

                    face_id_compare_done = true;
                }

                if (face_group_compare.isDone() && !face_group_compare_done) {
                    mProgressStatus += 33;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            mLoadingText.setVisibility(View.INVISIBLE);
                            mLoadingTextTwo.setVisibility(View.VISIBLE);
                        }
                    });

                    face_group_compare_done = true;
                }

                if (id_group_compare.isDone() && !id_group_compare_done) {
                    mProgressStatus += 34;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            mLoadingTextTwo.setVisibility(View.INVISIBLE);
                            mLoadingTextThree.setVisibility(View.VISIBLE);
                        }
                    });

                    id_group_compare_done = true;
                }

                mProgressBar.setProgress(mProgressStatus);

                if (mProgressStatus == 100) {
                    // We change screens when all the face compares are done.
                    Intent intent = new Intent(LoadingInformations.this, ReceivedInfoActivity.class);
                    startActivity(intent);
                }
            }

        }).start();
    }
}
