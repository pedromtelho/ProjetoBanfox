package br.edu.insper.al.vitorge.banfox;

import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class TextDetection extends AsyncTask<String, Void, Boolean> {

    private boolean done;

    TextDetection() {
        super();
        done = false;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String name = ((Global) LoadingInformations.getmContext().getApplication()).getUserName();
        String imagePath = strings[0];
        ByteBuffer imageBytes = null;
        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(new BasicAWSCredentials(BuildConfig.AccessKey, BuildConfig.SecretKey));

        try (InputStream inputStream = new FileInputStream(new File(imagePath))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + imagePath);
            System.exit(1);
        }

        Image image = new Image().withBytes(imageBytes);

        DetectTextRequest detectTextRequest = new DetectTextRequest().withImage(image);

        DetectTextResult detectTextResult = rekognitionClient.detectText(detectTextRequest);

        List<com.amazonaws.services.rekognition.model.TextDetection> textDetections = detectTextResult.getTextDetections();

        StringBuilder detected = new StringBuilder();

        for (com.amazonaws.services.rekognition.model.TextDetection textDetection : textDetections) {

            detected.append(textDetection.getDetectedText());
            detected.append("\n");
        }

        String detection = detected.toString();
        ((Global) LoadingInformations.getmContext().getApplication()).setTextDetected(detection);

        if (textDetections.size() == 0) {
            return false;
        } else {
            return detection.toLowerCase().contains(name.toLowerCase());
        }

    }

    @Override
    protected void onPostExecute(Boolean result) {
        ((Global) LoadingInformations.getmContext().getApplication()).setNameMatch(result);
        this.done = true;
    }

    public boolean isDone() {
        return done;
    }
}
