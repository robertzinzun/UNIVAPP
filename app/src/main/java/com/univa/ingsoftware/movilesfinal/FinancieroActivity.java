package com.univa.ingsoftware.movilesfinal;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FinancieroActivity extends AppCompatActivity {
    String idUsuario;
    Context ctx;
    TableLayout table;
    private static final String ADEUDOS_URL = "http://10.211.55.3:81/biblioteca/consultaAdeudos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financiero);
        idUsuario=getIntent().getStringExtra("id");
        table = (TableLayout) findViewById(R.id.tablelayout);
        FinancieroActivity.BackGround f = new FinancieroActivity.BackGround();
        f.execute(idUsuario);
        ctx=this;
    }
    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String idUsuario = params[0];
            String data="";
            int tmp;

            try {
                URL url = new URL(ADEUDOS_URL);
                String urlParams = "id="+idUsuario;

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
            try {
                JSONArray root = new JSONArray(s);
                for(int i=0;i<root.length();i++){

                    JSONObject user_data = root.getJSONObject(i);
                    TableRow tableRow = new TableRow(ctx);
                    table.addView(tableRow);
                    TextView tv1=new TextView(ctx);
                    tv1.setText(user_data.getString("desc")+" ");
                    tableRow.addView(tv1);
                    TextView tv2=new TextView(ctx);
                    tv2.setText(user_data.getString("cant"));
                    tableRow.addView(tv2);
                    TextView tv3=new TextView(ctx);
                    tv3.setText(user_data.getString("fvto"));
                    tableRow.addView(tv3);
                    TextView tv4=new TextView(ctx);
                    tv4.setText(user_data.getString("cuatri"));
                    tableRow.addView(tv4);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception:\n "+e.getMessage();
                TableRow tableRow = new TableRow(ctx);
                TextView tv1=new TextView(ctx);
                tv1.setText(err);
                tableRow.addView(tv1);

            }
        }
    }

}
