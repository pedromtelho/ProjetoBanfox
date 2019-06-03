package br.edu.insper.al.vitorge.banfox;

import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class TextDetection extends AsyncTask<String, Void, Float> {

    private boolean done;

    TextDetection() {
        super();
        done = false;
    }

    @Override
    protected Float doInBackground(String... strings) {
        JsonObject info = ((Global) LoadingInformations.getmContext().getApplication()).getUserInfo();
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
            return (float) 0;
        } else {
            String name = String.valueOf(info.get("name"));
            boolean nameMatch = detection.toLowerCase().contains(name.split("\"")[1].toLowerCase());
            String birth = String.valueOf(info.get("birth"));
            boolean birthMatch = detection.toLowerCase().contains(birth.split("\"")[1].toLowerCase());
            String document = String.valueOf(info.get("document"));
            boolean documentMatch = detection.toLowerCase().contains(document.split("\"")[1].toLowerCase());
            float infoScore = 0;
            if (nameMatch) infoScore += 2;
            if (birthMatch) infoScore += 1;
            if (documentMatch) infoScore += 1;
            infoScore = infoScore/4;

            return infoScore;
        }

    }

    @Override
    protected void onPostExecute(Float result) {
        ((Global) LoadingInformations.getmContext().getApplication()).setInfoMatch(result);
        this.done = true;
    }

    public boolean isDone() {
        return done;
    }
}
