package com.ndlp.socialstudy.Umfragen.UmfrageAuswerten;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.ndlp.socialstudy.GeneralFileFolder.RefreshfromDatabase;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.BasicUmfragenFragment;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.OpenUmfrageToVoteFragment;
import com.ndlp.socialstudy.activity.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART;
import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART_CENTER;


public class UmfrageAuswertenFragment extends Fragment {
    public static UmfrageAuswertenFragment newInstance() {
        UmfrageAuswertenFragment umfrageAuswertenFragment = new UmfrageAuswertenFragment();
        return umfrageAuswertenFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    Integer currentpageint = 1;
    Integer umfang;
    TextView tv_topic;
    TextView tv_frage;
    TextView tv_teilnehmer;
    TextView tv_progress;
    View rootView;

    public ArrayList<String> answers = new ArrayList<>();
    public ArrayList<Integer> answerIDs = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_umfrage_auswerten, container, false);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Quicksand-Bold.otf");

        ImageView iv_next = (ImageView) rootView.findViewById(R.id.umfrageauswertennächstefrage);
        ImageView iv_previous = (ImageView) rootView.findViewById(R.id.umfrageauswertenvorherigefrage);
        tv_topic = (TextView) rootView.findViewById(R.id.tv_umfrageauswertentopic);
        tv_frage = (TextView) rootView.findViewById(R.id.tv_umfrageauswertenfrage);
        tv_progress = (TextView) rootView.findViewById(R.id.umfrageauswertenprogress);
        tv_teilnehmer = (TextView) rootView.findViewById(R.id.umfrageauswertenteilnehmerview);

        tv_topic.setTypeface(quicksand_bold);
        tv_teilnehmer.setTypeface(quicksand_regular);
        tv_progress.setTypeface(quicksand_regular);
        tv_frage.setTypeface(quicksand_regular);

        TinyDB tinyDB = new TinyDB(getContext());

        umfang = tinyDB.getInt("umfang");

        getData(currentpageint);


        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentpageint == umfang) {
                    Toast.makeText(getContext(), "There is no question after this one", Toast.LENGTH_LONG).show();
                    BasicUmfragenFragment basicUmfragenFragment = new BasicUmfragenFragment();
                    FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, basicUmfragenFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    currentpageint = currentpageint + 1;
                    getData(currentpageint);
                }

            }
        });

        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentpageint == 1) {
                    Toast.makeText(getContext(), "There is no question before this one", Toast.LENGTH_LONG).show();
                }else{
                    currentpageint = currentpageint - 1;
                    getData(currentpageint);

                }
            }
        });

        return rootView;
    }

    public void getData(Integer currentpageint) {

        //holt die aktuelle Frage
        TinyDB tinyDB = new TinyDB(getContext());
        String question = tinyDB.getString("question" + currentpageint);

        tv_topic.setText(tinyDB.getString("topic"));
        tv_frage.setText(question);
        tv_progress.setText(currentpageint + "/" + umfang);
        tv_teilnehmer.setText("Es haben " + tinyDB.getString("teilnehmerzahl") + " Personen abgestimmt bis jetzt!");

        //holt die möglichen antworten
        answers.clear();
        answerIDs.clear();
        answers = tinyDB.getListString("answers" + currentpageint);
        answerIDs = tinyDB.getListInt("answerIDs" + currentpageint);

        String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/Umfragen/UmfrageAuswerten.php";


        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {

                                JSONArray jArrayanswertextarray = jsonResponse.getJSONArray("answertextarray");
                                JSONArray jArrayumfragewertearray = jsonResponse.getJSONArray("umfragewertearray");

                                ArrayList<String> answertextarray = new ArrayList<>();
                                ArrayList<Integer> umfragewertearray = new ArrayList<>();


                                if (jArrayanswertextarray != null) {
                                    for (int i = 0; i < jArrayanswertextarray.length(); i++) {
                                        answertextarray.add(jArrayanswertextarray.getString(i));
                                    }
                                }else{
                                    Log.i("Error", "1");
                                }

                                if (jArrayumfragewertearray != null) {
                                    for (int i = 0; i < jArrayumfragewertearray.length(); i++) {
                                        umfragewertearray.add(jArrayumfragewertearray.getInt(i));
                                    }
                                }else{
                                    Log.i("Error", "2");
                                }

                                String[] text = answertextarray.toArray(new String[answertextarray.size()]);
                                Integer[] werte = umfragewertearray.toArray(new Integer[umfragewertearray.size()]);

                                Log.i("text", "" + text);
                                Log.i("werte", "" + werte);


                                List<PieEntry> pieEntries = new ArrayList<>();

                                for (int i = 0; i < umfragewertearray.size(); i++ ){
                                    pieEntries.add(new PieEntry(werte[i], text[i]));
                                    Log.i("wert text", "" + werte[i] + ""+ text[i] );

                                }

                                Log.i("PIE", "" + pieEntries);

                                Typeface quicksand_regular=Typeface.createFromAsset(getContext().getAssets(),  "fonts/Quicksand-Regular.otf");
                                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                pieDataSet.setValueFormatter(new PercentFormatter());
                                PieData pieData = new PieData(pieDataSet);

                                //formatting
                                pieDataSet.setColors(new int[]{R.color.rot, R.color.dunkelblau, R.color.grün, R.color.gelb,R.color.dunkelrot, R.color.dunkelgrün, R.color.dunkelgelb}, getContext());
                                pieData.setValueTypeface(quicksand_regular);
                                pieDataSet.setValueTypeface(quicksand_regular);


                                // this increases the values text size
                                pieData.setValueTextSize(20f);

                                PieChart pieChart = (PieChart) rootView.findViewById(R.id.umfrageauswertenpiechart);
                                pieChart.setData(pieData);
                                pieChart.animateY(2000);
                                pieChart.setDescription(null);

                                // configure pie chart
                                pieChart.setUsePercentValues(true);
                                pieChart.setTouchEnabled(false);



                                //hole
                                pieChart.setDrawHoleEnabled(false);


                                Legend legend = pieChart.getLegend();
                                legend.setEnabled(false);


                                pieChart.invalidate();

                            } else {

                                Toast.makeText(getContext(), jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Log.e(RefreshfromDatabase.class.getSimpleName(), e.getMessage());

                        }
                    }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("answerIDs", answerIDs + "");
                Log.i("answerIDs zum Server: " , answerIDs + "");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    //adds % after value
    public class PercentFormatter implements IValueFormatter, IAxisValueFormatter
    {

        protected DecimalFormat mFormat;

        public PercentFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0");
        }

        public PercentFormatter(DecimalFormat format) {
            this.mFormat = format;
        }

        // IValueFormatter
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + " %";
        }

        // IAxisValueFormatter
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " %";
        }

        public int getDecimalDigits() {
            return 1;
        }
    }




}

