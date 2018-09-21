package com.vsupport.npluslabs.vsupport.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.plattysoft.leonids.ParticleSystem;
import com.vsupport.npluslabs.vsupport.MyApplication;
import com.vsupport.npluslabs.vsupport.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }

    Timer T;
    View view;
    TextView score;
    ProgressDialog progressDialog;
    ImageView hitMe;
    private static final String SAVEURL = "http://almaland.net/vsupport_api/save_record";
    private String EVENT_DATE_TIME = "2018-12-31 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String participantId;
    private LinearLayout linear_layout_1, linear_layout_2;
    //    private TextView tv_days, tv_hour;
    private TextView tv_second;
    private Handler handler = new Handler();
    private Runnable runnable;
    ParticleSystem particleSystem1,particleSystem2;
    private int counter=0;
    private static final String FORMAT = "%02d:%02d:%02d";
    int count;
    int seconds , minutes;
    String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        hitMe = view.findViewById(R.id.hit_me);
        score = view.findViewById(R.id.score);
        hitMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                score.setText(""+counter);
                if(counter==2000){
                    sendScore(String.valueOf(count));

                }
            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            participantId = bundle.getString("participant_id", null);
        }
        linear_layout_1 = view.findViewById(R.id.linear_layout_1);
        linear_layout_2 = view.findViewById(R.id.linear_layout_2);
//        tv_days =  view.findViewById(R.id.tv_days);
//        tv_hour =  view.findViewById(R.id.tv_hour);
//        tv_minute =  view.findViewById(R.id.tv_minute);
        tv_second =  view.findViewById(R.id.tv_second_title);


        SharedPreferences prefs = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        String uuid = prefs.getString("user_id", null);
        userId = uuid;
       // reverseTimer(10);
        particleSystem1 = new ParticleSystem(getActivity(), 80, R.drawable.confeti3, 10000);
        particleSystem2 = new ParticleSystem(getActivity(), 80, R.drawable.confeti2, 10000);
        timer();
//        countDownStart();
        return view;
    }

    private void timer() {

        T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        count++;
                        Log.i("Timer", ""+count);
                        int minutes = count / 60;
                        seconds = count % 60;
                        tv_second.setText( String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                    }
                });
            }
        }, 1000, 1000);

//            @Override
//            public void run() {
////                count++;
////                Log.i("Timer", ""+count);
////                int minutes = count / 60;
////                seconds = seconds % 60;
////                tv_second.setText( String.format("%02d", minutes) + ":" + String.format("%02d", count));
//                // runOnUiThread(new Runnable()
////                {
////                    @Override
////                    public void run()
////                    {
////                        myTextView.setText("count="+count);
////                        count++;
////                    }
////                }
//            }
//        }, 1000, 1000);
    }

    private void initUI() {

        timer();
    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
//                        tv_days.setText(String.format("%02d", Days));
//                        tv_hour.setText(String.format("%02d", Hours));
//                        tv_minute.setText(String.format("%02d", Minutes));
                        tv_second.setText(String.format("%02d", Seconds));
                    } else {
                        linear_layout_1.setVisibility(View.VISIBLE);
                        linear_layout_2.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public void onStop() {
        super.onStop();
        Log.d("onStop()","Its Stops");
        handler.removeCallbacks(runnable);
    }
    public static GameFragment newInstance() {
        return new GameFragment();
    }
    public void reverseTimer(int Seconds){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv_second.setText( String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                particleSystem2.setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90)
                        .emit(view.findViewById(R.id.emiter_top_right), 8);

                particleSystem1.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00005f, 90)
                        .emit(view.findViewById(R.id.emiter_top_left), 8);
                new SweetAlertDialog(getActivity())
                        .setTitleText("Your Total Score")
                        .setContentText("Score: " + counter)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Game Over")
                                        .setContentText("Want to play again")
                                        .setConfirmText("Submit Score")
                                        .setCancelText("Exit")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                new ParticleSystem(getActivity(), 80, R.drawable.confeti2, 10)
                                                        .setSpeedRange(0.2f, 0.5f)
                                                        .oneShot(getView(), 80);
                                                sDialog.dismissWithAnimation();
                                                particleSystem2.cancel();
                                                particleSystem2.stopEmitting();
                                                particleSystem1.stopEmitting();
                                                particleSystem1.cancel();
                                                submitScore();

                                            }
                                        })
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                getActivity().finishAffinity();
//                                                submitScore();
                                            }
                                        })
                                        .show();
                            }
                        })
                        .show();

            }
        }.start();
    }

    private void submitScore() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("Score Submitted!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        ShowsMenu showsMenu = new ShowsMenu();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, showsMenu).commit();
                    }
                })
                .show();
    }

    private void onFinish() {
        T.cancel();
        particleSystem2.setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(view.findViewById(R.id.emiter_top_right), 8);

        particleSystem1.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.00005f, 90)
                .emit(view.findViewById(R.id.emiter_top_left), 8);
        new SweetAlertDialog(getActivity())
                .setTitleText("You Completed Run in")
                .setContentText("Time: " + count)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Game Over")
                                .setContentText("Want to play again")
                                .setConfirmText("Submit Score")
                                .setCancelText("Exit")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        new ParticleSystem(getActivity(), 80, R.drawable.confeti2, 10)
                                                .setSpeedRange(0.2f, 0.5f)
                                                .oneShot(getView(), 80);
                                        sDialog.dismissWithAnimation();
                                        particleSystem2.cancel();
                                        particleSystem2.stopEmitting();
                                        particleSystem1.stopEmitting();
                                        particleSystem1.cancel();
                                        submitScore();

                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        getActivity().finishAffinity();
                                    }
                                })
                                .show();
                    }
                })
                .show();

    }

    private void sendScore(final String timeTaken){
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Casting vote...");
        progressDialog.show();

        String url = SAVEURL;
        Log.i("URL->",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        // response
                        // Logger.addLogAdapter(new AndroidLogAdapter());
                        Log.d("Response-> ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            onFinish();

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
                        new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
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
                params.put("user_id", userId);
                params.put("event", "2k");
                params.put("time_taken", timeTaken);
                params.put("participant_id",participantId);
               // params.put("vote", "1");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(postRequest);
    }

}


