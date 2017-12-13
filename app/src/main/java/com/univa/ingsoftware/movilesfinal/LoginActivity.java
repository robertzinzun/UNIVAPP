package com.univa.ingsoftware.movilesfinal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    EditText user,pwd;
    String Name,Password,Email,ID;
    Context ctx;
    public static final String LOGIN_URL="http://10.211.55.3:81/biblioteca/login.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        user=(EditText)findViewById(R.id.etUser);
        pwd=(EditText)findViewById(R.id.etPwd);
    }
    public void main_login(View v){
        Name = user.getText().toString();
        Password = pwd.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);
        ctx=this;
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {

                //URL url = new URL("http://10.211.55.3:81/Biblioteca/login.php");
                URL url = new URL(LOGIN_URL);

                String urlParams = "name="+name+"&password="+password;

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
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                Name = user_data.getString("nombre");
                ID=user_data.getString("idUsuario");
                Email = user_data.getString("email");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            Intent i = new Intent(ctx, MenuPrincipal.class);
            i.putExtra("name", Name);
            i.putExtra("email", Email);
            i.putExtra("id",ID);
            i.putExtra("err", err);
            startActivity(i);

        }
    }


}
