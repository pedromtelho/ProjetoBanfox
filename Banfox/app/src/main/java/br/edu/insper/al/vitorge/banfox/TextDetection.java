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
import java.util.LinkedList;
import java.util.List;

public class TextDetection extends AsyncTask<String, Void, LinkedList<String>> {

    public TextDetection() {

    }

    @Override
    protected LinkedList<String> doInBackground(String... strings) {
        String path = strings[0];
        ByteBuffer imageBytes = null;

        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(new BasicAWSCredentials(BuildConfig.AccessKey, BuildConfig.SecretKey));

        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(new File(path))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + path);
            System.exit(1);
        }

        Image source = new Image()
                .withBytes(imageBytes);

        DetectTextRequest request = new DetectTextRequest()
                .withImage(source);

        DetectTextResult result = rekognitionClient.detectText(request);

        List<com.amazonaws.services.rekognition.model.TextDetection> textDetections = result.getTextDetections();

        System.out.println("Detected lines and words for " + path);
        for (com.amazonaws.services.rekognition.model.TextDetection text: textDetections) {

            System.out.println("Detected: " + text.getDetectedText());
            System.out.println("Confidence: " + text.getConfidence().toString());
            System.out.println("Id : " + text.getId());
            System.out.println("Parent Id: " + text.getParentId());
            System.out.println("Type: " + text.getType());
            System.out.println();
        }

        return null;
    }
}
