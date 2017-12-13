package com.univa.ingsoftware.movilesfinal;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CalifActivity extends AppCompatActivity {

    private static final String CALIF_URL = "http://10.211.55.3:81/biblioteca/consultacalif.php";
    TextView name;
    String Name;
    Context ctx=this;
    String NAME="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calif);
        name = (TextView) findViewById(R.id.resultado);

        Name = getIntent().getStringExtra("id");

        BackGround b = new BackGround();
        b.execute(Name);

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String data="";
            int tmp;

            try {
                URL url = new URL(CALIF_URL);

                String urlParams = "name="+name;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();

                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;
            NAME = "";
            try {
                JSONObject root = new JSONObject(s);
                JSONArray jsonArray = root.getJSONArray("user_data");
                for (int i=0; i<jsonArray.length();i++){
                    NAME += " Nombre       = " + jsonArray.getJSONObject(i).getString("name")+ "\n" +
                            " Materia      = " + jsonArray.getJSONObject(i).getString("materia")+"\n" +
                            " Calificacion = " + jsonArray.getJSONObject(i).getString("calificacion")+"\n";
                }


            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            name.setText(NAME);


        }
    }

}
