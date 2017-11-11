package com.univa.ingsoftware.movilesfinal;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.*;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    ArrayList nombre=new ArrayList();
    ArrayList descripcion=new ArrayList();
    ArrayList ruta=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=(ListView)findViewById(R.id.lnly);
       descargarImagen();

    }

    private void descargarImagen() {
        nombre.clear();
        descripcion.clear();
        ruta.clear();
        //final ProgressDialog progressDialog =new ProgressDialog(MainActivity.this);
        //progressDialog.setMessage("Cargando datos...");
        //progressDialog.show();
        AsyncHttpClient client =new AsyncHttpClient();
        client.get("http://aguilar.x10host.com/SDProject/consultaGaleria.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                  // progressDialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(new String(responseBody));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            nombre.add(jsonArray.getJSONObject(i).getString("Nombre"));
                            descripcion.add(jsonArray.getJSONObject(i).getString("Descripcion"));
                            ruta.add(jsonArray.getJSONObject(i).getString("Ruta"));
                            System.out.println("hecho");
                        }
                        listview.setAdapter(new ImagenAdapter(getApplicationContext()));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    private class ImagenAdapter extends BaseAdapter{
       Context context;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView tvNombre,tvDescripcion;
        public ImagenAdapter(Context aplicationContext ){
            this.context=aplicationContext;
            layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return ruta.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.activity_main_item,null);
            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen1);
            tvNombre=(TextView)viewGroup.findViewById(R.id.tvNombre);
            tvDescripcion=(TextView)viewGroup.findViewById(R.id.tvDescripcion);
            String urlfinal="http://aguilar.x10host.com/SDProject/img/"+ruta.get(position).toString();
            Rect rect=new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setImageUrl(urlfinal,rect);
            tvNombre.setText(nombre.get(position).toString());
            tvDescripcion.setText(descripcion.get(position).toString());
            return viewGroup;
        }
    }

}



