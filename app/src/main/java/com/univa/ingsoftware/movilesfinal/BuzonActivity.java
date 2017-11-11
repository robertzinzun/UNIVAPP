package com.univa.ingsoftware.movilesfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class BuzonActivity extends AppCompatActivity {

    private static final String BUZON_URL = "http://aguilar.x10host.com/SDProject/buzon.php";

    private static final String[] CARRERAS = new String[] {
            "Maestría en Administración",
            "Maestría en Fiscal",
            "Maestría en Ing. de Software",
            "Maestría en Nutrición Clínica",
            "Maestría en Terapia familiar sistémico relacional",
            "Maestría en Administración de negocios MBA",
            "Maestría en en Finanzas",
            "Maestría en Desarollo organizacional y humano",
            "Maestría en Juicio de amparo",
            "Maestría en Derecho corporativo",
            "Maestría en Educación",
            "Maestría en Ingeniería industrial",
            "Maestría en Valuación",
            "Maestría en Gestión de la calidad",
            "Maestría en Especialidad en programación de tecnologías móviles",
            "Maestría en Comunicación corporativa",
            "Maestría en Medios creativos digitales",
            "Maestría en Dirección de mercadotecnia"
    };

    private static final String[] DEPARTAMENTOS = new String[] {
            "Servicios escolares",
            "Finanzas",
            "Dirección",
            "Profesores",
            "Cafetería"
    };

    private Spinner mCarreraSpinner;
    private Spinner mDepartamentoSpinner;
    private EditText mMensajeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buzon_activity);

        mCarreraSpinner = findViewById(R.id.buzon_carrera);
        mCarreraSpinner.setAdapter(new ArrayAdapter<>(this,
                android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,
                CARRERAS));

        mDepartamentoSpinner = findViewById(R.id.buzon_departamento);
        mDepartamentoSpinner.setAdapter(new ArrayAdapter<>(this,
                android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,
                DEPARTAMENTOS));

        mMensajeText = findViewById(R.id.buzon_mensaje);

        Button boton = findViewById(R.id.buzon_boton);
        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new EnviarMensajeTask().execute();
            }
        });
    }

    private class EnviarMensajeTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            ViewGroup contenido = findViewById(R.id.buzon_contenido);
            contenido.animate()
                    .alpha(0)
                    .setDuration(300)
                    .start();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(BUZON_URL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                try {
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "x-www-form-urlencoded; charset=utf-8");

                    String data = "carrera=" + URLEncoder.encode((String)mCarreraSpinner.getSelectedItem(), "UTF-8") +
                            "&departamento=" + URLEncoder.encode((String)mDepartamentoSpinner.getSelectedItem(), "UTF-8") +
                            "&mensaje=" + URLEncoder.encode(mMensajeText.getText().toString(), "UTF-8");

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    out.write(data.getBytes());
                    out.flush();

                    Scanner s = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
                    String resp = s.hasNext() ? s.next() : "";

                    System.out.println(resp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            finish();
        }
    }

}
