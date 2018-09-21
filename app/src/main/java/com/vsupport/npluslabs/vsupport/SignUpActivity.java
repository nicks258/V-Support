package com.vsupport.npluslabs.vsupport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {
    EditText emailET,passwordET,nameET,phoneET;
    RequestQueue queue;
    ProgressDialog progressDialog;
    CardView _loginButton;
    TextView loginHere;
    private String URL="http://almaland.net/vsupport_api/registeration";
    private View mProgressView;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;

    private View mLoginFormView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //if logged in then go to MainActivity
        SharedPreferences prefs = this.getSharedPreferences("user_info", MODE_PRIVATE);
        String uuid = prefs.getString("user_id", null);
        if (uuid!=null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        //

        loginHere = findViewById(R.id.textView2);
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                SignUpActivity.this.finish();
            }
        });
        nameET     = findViewById(R.id.name);
        phoneET    = findViewById(R.id.phone);
        emailET    = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        _loginButton = findViewById(R.id.other_intern_btn);
      //  sharedPreferences = SignUpActivity.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        Log.d("LoginActivity", "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = emailET.getText().toString();
        final String password = passwordET.getText().toString();
        final String name = nameET.getText().toString();
        final String phoneNumber = phoneET.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        authenticateUser(email,password,name,phoneNumber);

                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String name = nameET.getText().toString();
        String phone = phoneET.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignUpActivity.this,"enter a valid email address",Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(SignUpActivity.this," Enter a password between 4 and 10 alphanumeric characters",Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (name.isEmpty()) {
            Toast.makeText(SignUpActivity.this,"Name should not be empty",Toast.LENGTH_LONG).show();
            valid = false;
        }

        if (phone.isEmpty() ||  phone.length() != 10) {
            Toast.makeText(SignUpActivity.this,"Phone Number is invalid",Toast.LENGTH_LONG).show();
            valid = false;
        }


        return valid;
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
//        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    private void authenticateUser(final String email, final String password, final String name,final String phoneNumber ) {
        String url = URL;
        Log.i("URL->",url);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
//                        Intent loginActivityIntent = new Intent(SignUpActivity.this,LoginActivity.class);
//                        startActivity(loginActivityIntent);
//                        SignUpActivity.this.finish();
//                        TODO SignUp Logic
                        Log.d("Response-> ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (java.util.Objects.equals(jsonObject.getString("status"), "TRUE"))
                            {
                                progressDialog.dismiss();
//                                TODO Adjust me according to response
                               // JSONArray userDetails = jsonObject.getJSONArray("user_detail");

//                                for(int i=0;i<userDetails.length();i++)
//                                {
//                                    JSONObject userInfo = userDetails.getJSONObject(i);
//
                                    Intent loginActivityIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(loginActivityIntent);
                                    SignUpActivity.this.finish();
//                                }

                            }
                            else {
                                progressDialog.dismiss();



                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("" + jsonObject.getString("reason"))
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        progressDialog.dismiss();
                        new SweetAlertDialog(SignUpActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", name);
                params.put("phone", phoneNumber);
                return params;
            }
        };
        postRequest.setShouldCache(false);
        queue.getCache().clear();
        queue.add(postRequest);
    }


}