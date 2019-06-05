package br.edu.insper.al.vitorge.banfox;

import android.content.Intent;
import android.os.Handler;
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
    private TextView mLoadingTextFour;
    private int mProgressStatus = 0;
    private final Handler mHandler = new Handler();
    private static LoadingInformations mContext;

    private boolean face_id_compare_done = false;
    private boolean face_group_compare_done = false;
    private boolean id_group_compare_done = false;
    private boolean text_detection_done = false;
    private boolean postRequestInitiaded = false;

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
        String textImage = ((Global) this.getApplication()).getTextPicture();

        // Print user's location.
        Double latitude = ((Global) this.getApplication()).getUserLatitude();
        Double longitude = ((Global) this.getApplication()).getUserLongitude();
        System.out.println("[LOGG] Localização do usuário: " + latitude + "   " + longitude);

        //new FaceCompare().execute(faceImage, idImage, "1");
        FaceCompare face_id_compare = new FaceCompare();
        face_id_compare.execute(faceImage, idImage, "1");
        System.out.println("[LOGG] Começou a comparar CARA e ID.");

        //new FaceCompare().execute(faceImage, groupImage, "2");
        FaceCompare face_group_compare = new FaceCompare();
        face_group_compare.execute(faceImage, groupImage, "2");
        System.out.println("[LOGG] Começou a comparar CARA e GRUPO.");

        //new FaceCompare().execute(idImage, groupImage, "2");
        FaceCompare id_group_compare = new FaceCompare();
        id_group_compare.execute(idImage, groupImage, "2");
        System.out.println("[LOGG] Começou a comparar ID e GRUPO.");

        TextDetection text_detection = new TextDetection();
        text_detection.execute(textImage);
        System.out.println("[LOGG] Começou a procurar texto no DOCUMENTO.");

        PostRequest postRequest = new PostRequest();

        mProgressBar = findViewById(R.id.progressbar);
        mLoadingText = findViewById(R.id.LoadingSendingTextView);
        mLoadingTextTwo = findViewById(R.id.LoadingFirstTextView);
        mLoadingTextThree = findViewById(R.id.LoadingSecondTextView);
        mLoadingTextFour = findViewById(R.id.LoadingThirdTextView);


        new Thread(() -> {
            runOnUiThread(() -> mLoadingText.setVisibility(View.VISIBLE));
            while (mProgressStatus < 100) {
                if (face_id_compare.isDone() && !face_id_compare_done) {
                    mProgressStatus += 22.5;

                    runOnUiThread(() -> {
                        // Stuff that updates the UI
                        mLoadingText.setVisibility(View.INVISIBLE);
                        mLoadingTextTwo.setVisibility(View.VISIBLE);
                    });

                    face_id_compare_done = true;
                    System.out.println("[LOGG] Terminou de comparar CARA e ID.");
                }

                if (face_group_compare.isDone() && !face_group_compare_done) {
                    mProgressStatus += 22.5;

                    runOnUiThread(() -> {
                        // Stuff that updates the UI
                        mLoadingTextTwo.setVisibility(View.INVISIBLE);
                        mLoadingTextThree.setVisibility(View.VISIBLE);
                    });

                    face_group_compare_done = true;
                    System.out.println("[LOGG] Terminou de comparar CARA e GRUPO.");
                }

                if (id_group_compare.isDone() && !id_group_compare_done) {
                    mProgressStatus += 22.5;

                    runOnUiThread(() -> {
                        // Stuff that updates the UI
                        mLoadingTextThree.setVisibility(View.INVISIBLE);
                        mLoadingTextFour.setVisibility(View.VISIBLE);
                    });

                    id_group_compare_done = true;
                    System.out.println("[LOGG] Terminou de comparar ID e GRUPO.");
                }

                if (text_detection.isDone() && !text_detection_done) {
                    mProgressStatus += 22.5;
                    text_detection_done = true;

                    System.out.println("[LOGG] Terminou TextDetection.");
                }

                if (face_id_compare_done
                        && face_group_compare_done
                        && id_group_compare_done
                        && text_detection_done
                        && !postRequestInitiaded) {
                    postRequest.execute();

                    postRequestInitiaded = true;

                    System.out.println("[LOGG] Iniciou PostRequest.");
                }

                if (postRequest.isDone()) {
                    mProgressStatus += 10;

                    System.out.println("[LOGG] Terminou o PostRequest.");

                    // We change screens when all the face compares are done.
                    Intent intent = new Intent(LoadingInformations.this, ReceivedInfoActivity.class);
                    startActivity(intent);
                }

                mProgressBar.setProgress(mProgressStatus);
            }

        }).start();
    }
}
