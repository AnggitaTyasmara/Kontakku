package com.example.kontakku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TambahTeman extends AppCompatActivity {

    private EditText editNama, editTelpon;
    private Button simpanbtn;
    String nm,tlp;
    int success;

    private static String url_insert = "http://10.0.2.2:8080/umyTI/tambahtm.php";
    private static final String TAG = TambahTeman.class.getSimpleName();
    private final String TAG_SUCCES = "success";

    public void SimpanData(){
        if (editNama.getText().toString().equals("")||editTelpon.getText().toString().equals("")){
            Toast.makeText(TambahTeman.this,"Harap isi Semua data", Toast.LENGTH_SHORT).show();
        }
        else {
            nm = editNama.getText().toString();
            tlp = editTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Response : " + response.toString());

                    try {
                        JSONObject JObj = new JSONObject(response);
                        success = JObj.getInt(TAG_SUCCES);
                        if (success == 1) {
                            Toast.makeText(TambahTeman.this, "Data Berhasill di Simpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TambahTeman.this, "Maaf Gagal!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error : "+error.getMessage());
                    Toast.makeText(TambahTeman.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();

                    params.put("nama", nm);
                    params.put("telpon", tlp);
                    return params;
                }
            };
            requestQueue.add(strReq);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);

        editNama = findViewById(R.id.edNama);
        editTelpon = findViewById(R.id.edTelpon);
        simpanbtn = findViewById(R.id.btnSimpan);

        simpanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanData();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}