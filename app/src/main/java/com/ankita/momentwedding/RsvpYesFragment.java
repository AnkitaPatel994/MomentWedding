package com.ankita.momentwedding;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RsvpYesFragment extends Fragment implements View.OnClickListener {

    View v;
    String text = "1",departArrival = "car",departDeparture = "car",AttemptProgram = "Yes";
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9;
    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;
    RelativeLayout rlArrivalCar,rlArrivalBus,rlArrivalRail,rlArrivalAirplane,rlDepartureCar,rlDepartureBus,rlDepartureRail,rlDepartureAirplane;
    ImageView ivArrivalCar,ivArrivalBus,ivArrivalRail,ivArrivalAirplane,ivDepartureCar,ivDepartureBus,ivDepartureRail,ivDepartureAirplane;
    ScrollView scrollView;

    RadioGroup rgYesNo;
    RadioButton rbYes,rbNo;

    ArrayList<String> programIdArray=new ArrayList<>();
    String listString="";

    boolean clicked = true;
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    public RsvpYesFragment() {
        // Required empty public constructor
    }

    public static RsvpYesFragment newInstance(String param1, String param2) {
        RsvpYesFragment fragment = new RsvpYesFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_rsvp_yes, container, false);

        scrollView = (ScrollView)v.findViewById(R.id.Scroll);
        scrollView.fullScroll(ScrollView.FOCUS_UP);

        ll1 = (LinearLayout)v.findViewById(R.id.ll1);
        txt1 = (TextView)v.findViewById(R.id.txt1);
        ll1.setOnClickListener(this);

        ll1.setBackgroundResource(R.drawable.box_border_selected);
        txt1.setTextColor(getResources().getColor(R.color.colorWrite));

        ll2 = (LinearLayout)v.findViewById(R.id.ll2);
        txt2 = (TextView)v.findViewById(R.id.txt2);
        ll2.setOnClickListener(this);

        ll3 = (LinearLayout)v.findViewById(R.id.ll3);
        txt3 = (TextView)v.findViewById(R.id.txt3);
        ll3.setOnClickListener(this);

        ll4 = (LinearLayout)v.findViewById(R.id.ll4);
        txt4 = (TextView)v.findViewById(R.id.txt4);
        ll4.setOnClickListener(this);

        ll5 = (LinearLayout)v.findViewById(R.id.ll5);
        txt5 = (TextView)v.findViewById(R.id.txt5);
        ll5.setOnClickListener(this);

        ll6 = (LinearLayout)v.findViewById(R.id.ll6);
        txt6 = (TextView)v.findViewById(R.id.txt6);
        ll6.setOnClickListener(this);

        ll7 = (LinearLayout)v.findViewById(R.id.ll7);
        txt7 = (TextView)v.findViewById(R.id.txt7);
        ll7.setOnClickListener(this);

        ll8 = (LinearLayout)v.findViewById(R.id.ll8);
        txt8 = (TextView)v.findViewById(R.id.txt8);
        ll8.setOnClickListener(this);

        ll9 = (LinearLayout)v.findViewById(R.id.ll9);
        txt9 = (TextView)v.findViewById(R.id.txt9);
        ll9.setOnClickListener(this);

        LinearLayout llArrival = (LinearLayout)v.findViewById(R.id.llArrival);
        final TextView txtArrivalDate = (TextView)v.findViewById(R.id.txtArrivalDate);
        final TextView txtArrivalTime = (TextView)v.findViewById(R.id.txtArrivalTime);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        txtArrivalDate.setText(sdfDate.format(new Date()));

        final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm a");
        txtArrivalTime.setText(sdfTime.format(new Date()));

        llArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String AM_PM ;
                        String sHour = "00";
                        String sMinute = "00";

                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        if(selectedHour < 10)
                        {
                            sHour = "0"+selectedHour;
                        }
                        else
                        {
                            sHour = String.valueOf(selectedHour);
                        }

                        if(selectedMinute < 10)
                        {
                            sMinute = "0"+selectedMinute;
                        }
                        else
                        {
                            sMinute = String.valueOf(selectedMinute);
                        }

                        txtArrivalTime.setText(sHour + ":" + sMinute + " "+ AM_PM);

                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.show();

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {


                        selectedmonth = selectedmonth + 1;

                        txtArrivalDate.setText(selectedday + "-" + selectedmonth + "-" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        rlArrivalCar = (RelativeLayout)v.findViewById(R.id.rlArrivalCar);
        ivArrivalCar = (ImageView)v.findViewById(R.id.ivArrivalCar);
        rlArrivalCar.setOnClickListener(this);
        ivArrivalCar.setImageResource(R.drawable.ic_car_write_24dp);

        rlArrivalBus = (RelativeLayout)v.findViewById(R.id.rlArrivalBus);
        ivArrivalBus = (ImageView)v.findViewById(R.id.ivArrivalBus);
        rlArrivalBus.setOnClickListener(this);

        rlArrivalRail = (RelativeLayout)v.findViewById(R.id.rlArrivalRail);
        ivArrivalRail = (ImageView)v.findViewById(R.id.ivArrivalRail);
        rlArrivalRail.setOnClickListener(this);

        rlArrivalAirplane = (RelativeLayout)v.findViewById(R.id.rlArrivalAirplane);
        ivArrivalAirplane = (ImageView)v.findViewById(R.id.ivArrivalAirplane);
        rlArrivalAirplane.setOnClickListener(this);

        LinearLayout llDeparture = (LinearLayout)v.findViewById(R.id.llDeparture);
        final TextView txtDepartureDate = (TextView)v.findViewById(R.id.txtDepartureDate);
        final TextView txtDepartureTime = (TextView)v.findViewById(R.id.txtDepartureTime);

        SimpleDateFormat sdfDate1 = new SimpleDateFormat("dd/MM/yyyy");
        txtDepartureDate.setText(sdfDate.format(new Date()));

        SimpleDateFormat sdfTime1 = new SimpleDateFormat("HH:mm");
        txtDepartureTime.setText(sdfTime.format(new Date()));

        llDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String AM_PM ;
                        String sHour = "00";
                        String sMinute = "00";

                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }

                        if(selectedHour < 10)
                        {
                            sHour = "0"+selectedHour;
                        }
                        else
                        {
                            sHour = String.valueOf(selectedHour);
                        }

                        if(selectedMinute < 10)
                        {
                            sMinute = "0"+selectedMinute;
                        }
                        else
                        {
                            sMinute = String.valueOf(selectedMinute);
                        }

                        txtDepartureTime.setText(sHour + ":" + sMinute + " "+ AM_PM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        txtDepartureDate.setText("" + selectedday + "-" + selectedmonth + "-" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(c.getTimeInMillis());
                mDatePicker.show();
            }
        });

        rlDepartureCar = (RelativeLayout)v.findViewById(R.id.rlDepartureCar);
        ivDepartureCar = (ImageView)v.findViewById(R.id.ivDepartureCar);
        rlDepartureCar.setOnClickListener(this);
        ivDepartureCar.setImageResource(R.drawable.ic_car_write_24dp);

        rlDepartureBus = (RelativeLayout)v.findViewById(R.id.rlDepartureBus);
        ivDepartureBus = (ImageView)v.findViewById(R.id.ivDepartureBus);
        rlDepartureBus.setOnClickListener(this);

        rlDepartureRail = (RelativeLayout)v.findViewById(R.id.rlDepartureRail);
        ivDepartureRail = (ImageView)v.findViewById(R.id.ivDepartureRail);
        rlDepartureRail.setOnClickListener(this);

        rlDepartureAirplane = (RelativeLayout)v.findViewById(R.id.rlDepartureAirplane);
        ivDepartureAirplane = (ImageView)v.findViewById(R.id.ivDepartureAirplane);
        rlDepartureAirplane.setOnClickListener(this);

        final EditText txtSR = (EditText)v.findViewById(R.id.txtSR);
        final EditText txtPNRNoA = (EditText)v.findViewById(R.id.txtPNRNoA);
        final EditText txtPNRNoD = (EditText)v.findViewById(R.id.txtPNRNoD);

        rgYesNo = (RadioGroup)v.findViewById(R.id.rgYesNo);
        rbYes = (RadioButton) v.findViewById(R.id.rbYes);
        rbYes.setChecked(true);
        GetProgramId getProgramId = new GetProgramId();
        getProgramId.execute();

        rbNo = (RadioButton) v.findViewById(R.id.rbNo);
        /*int selectedId = rgYesNo.getCheckedRadioButtonId();
        rbYesNo = (RadioButton)v.findViewById(selectedId);*/
        rgYesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                int selectedId = rgYesNo.getCheckedRadioButtonId();
                switch (i)
                {
                    case R.id.rbYes:
                        listString = "";
                        AttemptProgram = "Yes";
                        GetProgramId getProgramId = new GetProgramId();
                        getProgramId.execute();

                        break;
                    case R.id.rbNo:
                        ProgramActivity.listString = "";
                        AttemptProgram = "No";
                        Intent intent = new Intent(getActivity(),ProgramActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });

        Button btnSR = (Button)v.findViewById(R.id.btnSR);

        btnSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String guestList = text;

                String ArrivalDate= txtArrivalDate.getText().toString();
                String ArrivalTime= txtArrivalTime.getText().toString();
                String ArrivalDateTime = ArrivalDate+","+ArrivalTime;

                String DepartureDate= txtDepartureDate.getText().toString();
                String DepartureTime= txtDepartureTime.getText().toString();
                String DepartureDateTime = DepartureDate+","+DepartureTime;

                String DepartArrival = departArrival;
                String DepartDeparture = departDeparture;

                String SpecialRemark= txtSR.getText().toString();
                String PNRNoArrival= txtPNRNoA.getText().toString();
                String PNRNoDeparture= txtPNRNoD.getText().toString();

                String event_access = "";

                if(AttemptProgram == "Yes")
                {
                    event_access = listString;
                }
                else if(AttemptProgram == "No")
                {
                    event_access = ProgramActivity.listString;
                }

                GetRSVPList getRSVPList = new GetRSVPList(guestList,ArrivalDateTime,DepartureDateTime,DepartArrival,DepartDeparture,SpecialRemark,event_access,PNRNoArrival,PNRNoDeparture);
                getRSVPList.execute();
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == ll1)
        {
            if(!clicked)
            {
                /*ll1.setBackgroundResource(R.drawable.box_border);
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll1.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt1.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;

                text = "1";
            }
        }
        else if(v == ll2)
        {
            if(!clicked)
            {
                /*ll2.setBackgroundResource(R.drawable.box_border);
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll2.setBackgroundResource(R.drawable.box_border_selected);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt2.setTextColor(getResources().getColor(R.color.colorWrite));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "2";
            }
        }
        else if(v == ll3)
        {
            if(!clicked)
            {
                /*ll3.setBackgroundResource(R.drawable.box_border);
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll3.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt3.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "3";
            }
        }
        else if(v == ll4)
        {
            if(!clicked)
            {
                /*ll4.setBackgroundResource(R.drawable.box_border);
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll4.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt4.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "4";
            }
        }
        else if(v == ll5)
        {
            if(!clicked)
            {
                /*ll5.setBackgroundResource(R.drawable.box_border);
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll5.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt5.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "5";
            }
        }
        else if(v == ll6)
        {
            if(!clicked)
            {
                /*ll6.setBackgroundResource(R.drawable.box_border);
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll6.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt6.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "6";
            }
        }
        else if(v == ll7)
        {
            if(!clicked)
            {
                /*ll7.setBackgroundResource(R.drawable.box_border);
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll7.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt7.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "7";
            }
        }
        else if(v == ll8)
        {
            if(!clicked)
            {
                /*ll8.setBackgroundResource(R.drawable.box_border);
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll8.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);
                ll9.setBackgroundResource(R.drawable.box_border);

                txt8.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "8";
            }
        }
        else if(v == ll9)
        {
            if(!clicked)
            {
                /*ll9.setBackgroundResource(R.drawable.box_border);
                txt9.setTextColor(getResources().getColor(R.color.colorPrimary));*/
                clicked=true;
            }
            else
            {
                ll9.setBackgroundResource(R.drawable.box_border_selected);
                ll2.setBackgroundResource(R.drawable.box_border);
                ll3.setBackgroundResource(R.drawable.box_border);
                ll4.setBackgroundResource(R.drawable.box_border);
                ll5.setBackgroundResource(R.drawable.box_border);
                ll6.setBackgroundResource(R.drawable.box_border);
                ll7.setBackgroundResource(R.drawable.box_border);
                ll8.setBackgroundResource(R.drawable.box_border);
                ll1.setBackgroundResource(R.drawable.box_border);

                txt9.setTextColor(getResources().getColor(R.color.colorWrite));
                txt2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt4.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt5.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt6.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt7.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt8.setTextColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.colorPrimary));

                clicked=false;
                text = "9";
            }
        }

        if(v == rlArrivalCar)
        {
            if(!clicked)
            {
                /*ivArrivalCar.setImageResource(R.drawable.ic_car_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivArrivalCar.setImageResource(R.drawable.ic_car_write_24dp);
                ivArrivalBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivArrivalRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivArrivalAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departArrival = "car";
            }
        }
        else if(v == rlArrivalBus)
        {
            if(!clicked)
            {
                /*ivArrivalBus.setImageResource(R.drawable.ic_bus_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivArrivalBus.setImageResource(R.drawable.ic_bus_write_24dp);
                ivArrivalCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivArrivalRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivArrivalAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departArrival = "bus";
            }
        }
        else if(v == rlArrivalRail)
        {
            if(!clicked)
            {
                /*ivArrivalRail.setImageResource(R.drawable.ic_rail_black_24dp);*/

                clicked=true;
            }
            else
            {
                ivArrivalRail.setImageResource(R.drawable.ic_rail_write_24dp);
                ivArrivalCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivArrivalBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivArrivalAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departArrival = "train";
            }
        }
        else if(v == rlArrivalAirplane)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivArrivalRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivArrivalCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivArrivalBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivArrivalAirplane.setImageResource(R.drawable.ic_airplane_write_24dp);
                clicked=false;
                departArrival = "flight";
            }
        }

        if(v == rlDepartureCar)
        {
            if(!clicked)
            {
                /*ivDepartureCar.setImageResource(R.drawable.ic_car_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivDepartureCar.setImageResource(R.drawable.ic_car_write_24dp);
                ivDepartureBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivDepartureRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivDepartureAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departDeparture = "car";
            }
        }
        else if(v == rlDepartureBus)
        {
            if(!clicked)
            {
                /*ivDepartureBus.setImageResource(R.drawable.ic_bus_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivDepartureBus.setImageResource(R.drawable.ic_bus_write_24dp);
                ivDepartureCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivDepartureRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivDepartureAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departDeparture = "bus";
            }
        }
        else if(v == rlDepartureRail)
        {
            if(!clicked)
            {
                /*ivDepartureRail.setImageResource(R.drawable.ic_rail_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivDepartureRail.setImageResource(R.drawable.ic_rail_write_24dp);
                ivDepartureCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivDepartureBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivDepartureAirplane.setImageResource(R.drawable.ic_airplane_black_24dp);
                clicked=false;
                departDeparture = "train";
            }
        }
        else if(v == rlDepartureAirplane)
        {
            if(!clicked)
            {
                /*ivDepartureRail.setImageResource(R.drawable.ic_rail_black_24dp);*/
                clicked=true;
            }
            else
            {
                ivDepartureRail.setImageResource(R.drawable.ic_rail_black_24dp);
                ivDepartureCar.setImageResource(R.drawable.ic_car_black_24dp);
                ivDepartureBus.setImageResource(R.drawable.ic_bus_black_24dp);
                ivDepartureAirplane.setImageResource(R.drawable.ic_airplane_write_24dp);
                clicked=false;
                departDeparture = "flight";
            }
        }
    }

    private class GetRSVPList extends AsyncTask<String,Void,String> {

        String status,message,guestList,arrivalDateTime,departureDateTime,departArrival,departDeparture,specialRemark,event_access,PNRNoArrival,PNRNoDeparture;
        ProgressDialog dialog;

        public GetRSVPList(String guestList, String arrivalDateTime, String departureDateTime, String departArrival, String departDeparture, String specialRemark, String event_access, String PNRNoArrival,String PNRNoDeparture) {
            this.guestList = guestList;
            this.arrivalDateTime = arrivalDateTime;
            this.departureDateTime = departureDateTime;
            this.departArrival = departArrival;
            this.departDeparture = departDeparture;
            this.specialRemark = specialRemark;
            this.event_access = event_access;
            this.PNRNoArrival = PNRNoArrival;
            this.PNRNoDeparture = PNRNoDeparture;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject joRsvp=new JSONObject();
            try {

                joRsvp.put("guest_id",HomeActivity.guest_id);
                joRsvp.put("attending","yes");
                joRsvp.put("guest_count",guestList);
                joRsvp.put("arriving_on",arrivalDateTime);
                joRsvp.put("arriving_by",departArrival);
                joRsvp.put("departing_on",departureDateTime);
                joRsvp.put("departing_by",departDeparture);
                joRsvp.put("remarks",specialRemark);
                joRsvp.put("event_access",event_access);
                joRsvp.put("arriving_pnr",PNRNoArrival);
                joRsvp.put("departing_pnr",PNRNoDeparture);

                Postdata postdata=new Postdata();
                String pdRsvp=postdata.post(MainActivity.mainUrl+"guestRsvp",joRsvp.toString());
                JSONObject j=new JSONObject(pdRsvp);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                }
                else
                {
                    message=j.getString("message");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if(status.equals("1"))
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Thank You!");
                alert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                alert.show();
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetProgramId extends AsyncTask<String,Void,String>{

        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            programIdArray.clear();
            JSONObject joSchedule=new JSONObject();
            try {

                joSchedule.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdSchedule=postdata.post(MainActivity.mainUrl+"getEventList",joSchedule.toString());
                JSONObject j=new JSONObject(pdSchedule);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONArray JsArry=j.getJSONArray("event_list");
                    for (int i=0;i<JsArry.length();i++)
                    {
                        JSONObject jo=JsArry.getJSONObject(i);

                        String id =jo.getString("id");

                        programIdArray.add(id);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            for (String ss : programIdArray)
            {
                if(listString == ""){
                    listString += ss;
                }else{
                    listString += "," + ss;
                }

            }
        }
    }
}
