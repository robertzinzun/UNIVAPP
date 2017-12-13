package com.univa.ingsoftware.movilesfinal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class SeleccionarMarca extends AppCompatActivity {
    ListView ListaParaMostrar;
    private ArrayList<ModeloMarca> Marcas = new ArrayList<ModeloMarca>();
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seleccionar_marca);

        this.ListaParaMostrar=(ListView) findViewById(R.id.MarcaLvwMarcas);

        this.ListaParaMostrar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocalizarMarca(position);
            }
        });

        CargarMarcas(null);

        ctx=this;
    }

    private void CargarMarcas(View view){
        BackGround b = new BackGround();
        b.execute();
    }

    private  void LlenarListView(){
        ListaParaMostrar.setAdapter(
                new ArrayAdapter<>(ctx,
                        android.R.layout.simple_list_item_1,
                        this.Marcas.toArray(new ModeloMarca[this.Marcas.size()])));
    }

    private void LocalizarMarca(int IndexMarca){
        if(IndexMarca==-1)
        {

        } else {
            ModeloMarca Marca=this.Marcas.get(IndexMarca);
            Intent i=new Intent(ctx, MapsActivity.class);

            i.putExtra("Nombre",Marca.Nombre);
            i.putExtra("Latitud", Double.parseDouble(Marca.Latitud));
            i.putExtra("Longitud", Double.parseDouble(Marca.Longitud));

            startActivity(i);
        }
    }





    class BackGround extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String data = "";
            int tmp;

            try {
                URL url = new URL(Constantes.DireccionURLServidor);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }

        }

        protected void onPostExecute(String s) {
            String err=null;
            try {
                //creamos el objeto json
                //JSONObject json = new JSONObject(s);
                //JSONObject MarcasBD = json.getJSONObject(Constantes.NombreRespuestaJSONServidor);

                JSONArray MarcasBD = new JSONArray(s);

                //Declaro las variables para capturar los valores del JSON
                int jsonId;
                String jsonNombre;
                String jsonLat;
                String jsonLng;
                //creo un array de peces
                Marcas=new ArrayList<ModeloMarca>();
                for(int i=0; i<MarcasBD.length();i++) {
                    JSONObject marca = MarcasBD.getJSONObject(i);

                    //sacamos los datos y vamos creando los objetos
                    jsonId=marca.getInt(Constantes.RespuestaJSONColumnaID);
                    jsonNombre=marca.getString(Constantes.RespuestaJSONColumnaNombre);
                    jsonLat=marca.getString(Constantes.RespuestaJSONColumnaLatitud);
                    jsonLng=marca.getString(Constantes.RespuestaJSONColumnaLongitud);
                    //creo objetos tipo pez
                    ModeloMarca Marca=new ModeloMarca(jsonId,jsonNombre,jsonLat,jsonLng);
                    Marcas.add(Marca);
                }

                LlenarListView();

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            if(!"".equals(err)){
            }

        }

    }

    private class ModeloMarca {
        public int id;
        public String Nombre;
        public String Latitud;
        public String Longitud;

        public ModeloMarca(){

        }

        public ModeloMarca(int id, String nombre, String latitud, String longitud) {
            this.id = id;
            Nombre = nombre;
            Latitud = latitud;
            Longitud = longitud;
        }

        @Override
        public String toString() {
            return Nombre;
        }
    }





    public class Constantes{
        public final static String DireccionURLServidor="http://10.211.55.3:81/biblioteca/ConsultarMarcas.php";
        public final static String NombreRespuestaJSONServidor="resp";
        public final static String RespuestaJSONColumnaID="id";
        public final static String RespuestaJSONColumnaNombre="Nombre";
        public final static String RespuestaJSONColumnaLatitud="Latitud";
        public final static String RespuestaJSONColumnaLongitud="Longitud";
    }

}
