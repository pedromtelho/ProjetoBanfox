package br.edu.insper.al.vitorge.banfox;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class PostRequest extends AsyncTask<Void, Void, Void> {
    boolean done = false;
    int statusCode;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String url = "https://banfox.herokuapp.com/app/info";
            Float faceMatch = ((Global) LoadingInformations.getmContext().getApplication()).getFaceMatch();
            Float infoScore = ((Global) LoadingInformations.getmContext().getApplication()).getInfoMatch();
            double latitude = ((Global) LoadingInformations.getmContext().getApplication()).getUserLatitude();
            double longitude = ((Global) LoadingInformations.getmContext().getApplication()).getUserLongitude();

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            JsonObject userInfo = ((Global) LoadingInformations.getmContext().getApplication()).getUserInfo();
            String name = String.valueOf(userInfo.get("name"));
            String id = String.valueOf(userInfo.get("id"));
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("name", name);
            jsonObject.addProperty("score", String.valueOf((faceMatch + infoScore) / 2));
            jsonObject.addProperty("faceMatch", String.valueOf(faceMatch > 0.5));
            jsonObject.addProperty("nameMatch", String.valueOf(infoScore > 0.5));
            jsonObject.addProperty("longitude", String.valueOf(longitude));
            jsonObject.addProperty("latitude", String.valueOf(latitude));
            byte[] outputBytes = jsonObject.toString().getBytes();
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(outputBytes);
            outputStream.flush();
            outputStream.close();


            statusCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            View icon_view = LoadingInformations.getmContext().findViewById(R.id.icon_view);
            TextView done_textView = LoadingInformations.getmContext().findViewById(R.id.done_textView);
            TextView info_textView = LoadingInformations.getmContext().findViewById(R.id.info_textView);
            TextView thank_textView = LoadingInformations.getmContext().findViewById(R.id.thank_textView);

            //print result
            System.out.println("[LOGG] Resposta do PostRequest:");
            System.out.println("[LOGG]\n" + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        boolean sentInfoSuccessfully;

        if (statusCode == 200) {
            sentInfoSuccessfully = true;
        }
        else {
            sentInfoSuccessfully = false;
        }

        ((Global) LoadingInformations.getmContext().getApplication()).setInfoSentSuccessfully(sentInfoSuccessfully);

        // Sets this task as done.
        this.done = true;
    }

    public boolean isDone() {
        return done;
    }
}
