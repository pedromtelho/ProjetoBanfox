package br.edu.insper.al.vitorge.banfox;

import android.os.AsyncTask;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

class FaceCompare extends AsyncTask<String, Void, Float> {

    private boolean done = false;

    FaceCompare() {
        super();
    }

    @Override
    public Float doInBackground(String... strings) {
        String sourceImage = strings[0];
        String targetImage = strings[1];
        Integer matches = Integer.valueOf(strings[2]);
        Float similarityThreshold = 55F;
        ByteBuffer sourceImageBytes = null;
        ByteBuffer targetImageBytes = null;

        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(new BasicAWSCredentials(BuildConfig.AccessKey, BuildConfig.SecretKey));

        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + sourceImage);
            System.exit(1);
        }
        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }


        Image source = new Image()
                .withBytes(sourceImageBytes);
        Image target = new Image()
                .withBytes(targetImageBytes);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);


        // Display results
        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

        for (CompareFacesMatch match: faceDetails) {
            ComparedFace face = match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("[LOGG] Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + face.getConfidence().toString()
                    + "% confidence.");
        }

        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

        System.out.println("[LOGG] There was " + uncompared.size() + " face(s) that did not match");
        //System.out.println("Source image rotation: " + compareFacesResult.getSourceImageOrientationCorrection());
        //System.out.println("target image rotation: " + compareFacesResult.getTargetImageOrientationCorrection());

        if (faceDetails.size() == matches) {
            if (matches == 2) {
                return (float) 1;
            } else {
                return (float) 2;
            }
        } else {
            return (float) 0;
        }
    }

    @Override
    protected void onPostExecute(Float result) {
        // Getting the previous result.
        Float previusScore = ((Global) LoadingInformations.getmContext().getApplication()).getFaceMatch();
        float newScore = previusScore + result;
        ((Global) LoadingInformations.getmContext().getApplication()).setFaceMatch(newScore);

        // Sets this task as done.
        this.done = true;
    }

    public boolean isDone() {
        return done;
    }
}
