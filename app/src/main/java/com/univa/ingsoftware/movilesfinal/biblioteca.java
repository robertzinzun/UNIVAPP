package com.univa.ingsoftware.movilesfinal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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

/**
 * Created by roberto on 07/11/17.
 */

public class biblioteca extends AppCompatActivity {
    EditText id,nombre,email;
    TextView tvError,tvLibros;
    String idUsuario,Libro,FechaRegistro,FechaEntrega;
    Context ctx;
    TableLayout table;
    private static final String BIBLIOTECA_URL = "http://aguilar.x10host.com/SDProject/consultaLibros.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biblioteca_activity);
        idUsuario=getIntent().getStringExtra("id");
        table = (TableLayout) findViewById(R.id.tablelayout);
        biblioteca.BackGround b = new biblioteca.BackGround();
        b.execute(idUsuario);
        ctx=this;

    }
    public void consultaLibros(View v){
        idUsuario = id.getText().toString();
        biblioteca.BackGround b = new biblioteca.BackGround();
        b.execute(idUsuario);
        ctx=this;
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String idUsuario = params[0];
            String data="";
            int tmp;

            try {
                URL url = new URL(BIBLIOTECA_URL);

                //URL url = new URL("http://10.211.55.3:81/Biblioteca/consultaLibros.php");
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
                    tv1.setText(user_data.getString("libro")+" ");
                    tableRow.addView(tv1);
                    TextView tv2=new TextView(ctx);
                    tv2.setText(user_data.getString("fecharegistro"));
                    tableRow.addView(tv2);
                    TextView tv3=new TextView(ctx);
                    tv3.setText(user_data.getString("fechaentrega"));
                    tableRow.addView(tv3);

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
