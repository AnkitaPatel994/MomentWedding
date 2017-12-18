package com.ankita.momentwedding;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    ImageView img_photo_id,ivArrivalCar,ivArrivalBus,ivArrivalRail,ivArrivalAirplane,ivDepartureCar,ivDepartureBus,ivDepartureRail,ivDepartureAirplane;
    ScrollView scrollView;

    RadioGroup rgYesNo;
    RadioButton rbYes,rbNo;

    ArrayList<String> programIdArray=new ArrayList<>();
    String listString="";

    Bitmap bitmap = null;
    String str_imgpath,encodedImgpath="";
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    boolean clicked = true;
    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    GradientDrawable shapeBgSelect,shapeBg;

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

        v =inflater.inflate(R.layout.fragment_rsvp_yes, container, false);

        TextView tvGuest = (TextView)v.findViewById(R.id.tvGuest);
        tvGuest.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        TextView tvArrival = (TextView)v.findViewById(R.id.tvArrival);
        tvArrival.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        TextView tvDeparture = (TextView)v.findViewById(R.id.tvDeparture);
        tvDeparture.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        LinearLayout llBgBorsvp = (LinearLayout)v.findViewById(R.id.llBgBorsvp);
        llBgBorsvp.setBackgroundColor(Color.parseColor(GetTheme.colorPrimary));

        scrollView = (ScrollView)v.findViewById(R.id.Scroll);
        scrollView.fullScroll(ScrollView.FOCUS_UP);

        ll1 = (LinearLayout)v.findViewById(R.id.ll1);
        txt1 = (TextView)v.findViewById(R.id.txt1);
        ll1.setOnClickListener(this);

        shapeBgSelect =  new GradientDrawable();
        shapeBgSelect.setStroke(4,Color.parseColor(GetTheme.colorTextLight));
        shapeBgSelect.setCornerRadius(3);
        shapeBgSelect.setColor(Color.parseColor(GetTheme.colorPrimary));
        ll1.setBackground(shapeBgSelect);

        txt1.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        ll2 = (LinearLayout)v.findViewById(R.id.ll2);
        txt2 = (TextView)v.findViewById(R.id.txt2);
        ll2.setOnClickListener(this);

        shapeBg =  new GradientDrawable();
        shapeBg.setStroke(4,Color.parseColor(GetTheme.colorPrimary));
        shapeBg.setCornerRadius(3);
        shapeBg.setColor(Color.parseColor(GetTheme.colorTextLight));
        ll2.setBackground(shapeBg);
        txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll3 = (LinearLayout)v.findViewById(R.id.ll3);
        txt3 = (TextView)v.findViewById(R.id.txt3);
        ll3.setOnClickListener(this);
        ll3.setBackground(shapeBg);
        txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll4 = (LinearLayout)v.findViewById(R.id.ll4);
        txt4 = (TextView)v.findViewById(R.id.txt4);
        ll4.setOnClickListener(this);
        ll4.setBackground(shapeBg);
        txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll5 = (LinearLayout)v.findViewById(R.id.ll5);
        txt5 = (TextView)v.findViewById(R.id.txt5);
        ll5.setOnClickListener(this);
        ll5.setBackground(shapeBg);
        txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll6 = (LinearLayout)v.findViewById(R.id.ll6);
        txt6 = (TextView)v.findViewById(R.id.txt6);
        ll6.setOnClickListener(this);
        ll6.setBackground(shapeBg);
        txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll7 = (LinearLayout)v.findViewById(R.id.ll7);
        txt7 = (TextView)v.findViewById(R.id.txt7);
        ll7.setOnClickListener(this);
        ll7.setBackground(shapeBg);
        txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll8 = (LinearLayout)v.findViewById(R.id.ll8);
        txt8 = (TextView)v.findViewById(R.id.txt8);
        ll8.setOnClickListener(this);
        ll8.setBackground(shapeBg);
        txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        ll9 = (LinearLayout)v.findViewById(R.id.ll9);
        txt9 = (TextView)v.findViewById(R.id.txt9);
        ll9.setOnClickListener(this);
        ll9.setBackground(shapeBg);
        txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

        LinearLayout llArrival = (LinearLayout)v.findViewById(R.id.llArrival);
        GradientDrawable shapeBgDT =  new GradientDrawable();
        shapeBgDT.setCornerRadius(10);
        shapeBgDT.setColor(Color.parseColor(GetTheme.colorClockTopBg));
        llArrival.setBackground(shapeBgDT);

        final TextView txtArrivalDate = (TextView)v.findViewById(R.id.txtArrivalDate);
        txtArrivalDate.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        final TextView txtArrivalTime = (TextView)v.findViewById(R.id.txtArrivalTime);
        txtArrivalTime.setTextColor(Color.parseColor(GetTheme.colorTextDark));

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

        GradientDrawable shapeRoundBg =  new GradientDrawable();
        shapeRoundBg.setShape(GradientDrawable.OVAL);
        shapeRoundBg.setSize(120,120);
        shapeRoundBg.setStroke(1,Color.parseColor(GetTheme.colorLableText));
        shapeRoundBg.setCornerRadius(10);
        shapeRoundBg.setColor(Color.parseColor(GetTheme.colorPrimaryDark));

        ImageView ivACRound = (ImageView)v.findViewById(R.id.ivACRound);
        ivACRound.setBackground(shapeRoundBg);

        ImageView ivABRound = (ImageView)v.findViewById(R.id.ivABRound);
        ivABRound.setBackground(shapeRoundBg);

        ImageView ivARRound = (ImageView)v.findViewById(R.id.ivARRound);
        ivARRound.setBackground(shapeRoundBg);

        ImageView ivAARound = (ImageView)v.findViewById(R.id.ivAARound);
        ivAARound.setBackground(shapeRoundBg);

        rlArrivalCar = (RelativeLayout)v.findViewById(R.id.rlArrivalCar);
        ivArrivalCar = (ImageView)v.findViewById(R.id.ivArrivalCar);
        rlArrivalCar.setOnClickListener(this);
        ivArrivalCar.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);

        rlArrivalBus = (RelativeLayout)v.findViewById(R.id.rlArrivalBus);
        ivArrivalBus = (ImageView)v.findViewById(R.id.ivArrivalBus);
        rlArrivalBus.setOnClickListener(this);
        ivArrivalBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        rlArrivalRail = (RelativeLayout)v.findViewById(R.id.rlArrivalRail);
        ivArrivalRail = (ImageView)v.findViewById(R.id.ivArrivalRail);
        rlArrivalRail.setOnClickListener(this);
        ivArrivalRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        rlArrivalAirplane = (RelativeLayout)v.findViewById(R.id.rlArrivalAirplane);
        ivArrivalAirplane = (ImageView)v.findViewById(R.id.ivArrivalAirplane);
        rlArrivalAirplane.setOnClickListener(this);
        ivArrivalAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        LinearLayout llDeparture = (LinearLayout)v.findViewById(R.id.llDeparture);
        llDeparture.setBackground(shapeBgDT);

        final TextView txtDepartureDate = (TextView)v.findViewById(R.id.txtDepartureDate);
        txtDepartureDate.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        final TextView txtDepartureTime = (TextView)v.findViewById(R.id.txtDepartureTime);
        txtDepartureTime.setTextColor(Color.parseColor(GetTheme.colorTextDark));

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

        ImageView ivDCRound = (ImageView)v.findViewById(R.id.ivDCRound);
        ivDCRound.setBackground(shapeRoundBg);

        ImageView ivDBRound = (ImageView)v.findViewById(R.id.ivDBRound);
        ivDBRound.setBackground(shapeRoundBg);

        ImageView ivDRRound = (ImageView)v.findViewById(R.id.ivDRRound);
        ivDRRound.setBackground(shapeRoundBg);

        ImageView ivDARound = (ImageView)v.findViewById(R.id.ivDARound);
        ivDARound.setBackground(shapeRoundBg);

        rlDepartureCar = (RelativeLayout)v.findViewById(R.id.rlDepartureCar);
        ivDepartureCar = (ImageView)v.findViewById(R.id.ivDepartureCar);
        rlDepartureCar.setOnClickListener(this);
        ivDepartureCar.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);

        rlDepartureBus = (RelativeLayout)v.findViewById(R.id.rlDepartureBus);
        ivDepartureBus = (ImageView)v.findViewById(R.id.ivDepartureBus);
        rlDepartureBus.setOnClickListener(this);
        ivDepartureBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        rlDepartureRail = (RelativeLayout)v.findViewById(R.id.rlDepartureRail);
        ivDepartureRail = (ImageView)v.findViewById(R.id.ivDepartureRail);
        rlDepartureRail.setOnClickListener(this);
        ivDepartureRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        rlDepartureAirplane = (RelativeLayout)v.findViewById(R.id.rlDepartureAirplane);
        ivDepartureAirplane = (ImageView)v.findViewById(R.id.ivDepartureAirplane);
        rlDepartureAirplane.setOnClickListener(this);
        ivDepartureAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);

        TextView tvSR = (TextView)v.findViewById(R.id.tvSR);
        tvSR.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        TextView tvAllProgram = (TextView)v.findViewById(R.id.tvAllProgram);
        tvAllProgram.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        TextView tvUpload = (TextView)v.findViewById(R.id.tvUpload);
        tvUpload.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        final EditText txtSR = (EditText)v.findViewById(R.id.txtSR);
        txtSR.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        if(Build.VERSION.SDK_INT >= 21)
        {
            txtSR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }

        final EditText txtPNRNoA = (EditText)v.findViewById(R.id.txtPNRNoA);
        txtPNRNoA.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        if(Build.VERSION.SDK_INT >= 21)
        {
            txtPNRNoA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }


        final EditText txtPNRNoD = (EditText)v.findViewById(R.id.txtPNRNoD);
        txtPNRNoD.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        if(Build.VERSION.SDK_INT >= 21)
        {
            txtPNRNoD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }

        rgYesNo = (RadioGroup)v.findViewById(R.id.rgYesNo);
        rbYes = (RadioButton) v.findViewById(R.id.rbYes);
        if(Build.VERSION.SDK_INT >= 21)
        {
            rbYes.setButtonTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }

        rbYes.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        rbYes.setChecked(true);
        GetProgramId getProgramId = new GetProgramId();
        getProgramId.execute();

        rbNo = (RadioButton) v.findViewById(R.id.rbNo);
        rbNo.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        if(Build.VERSION.SDK_INT >= 21)
        {
            rbNo.setButtonTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));
        }

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
        btnSR.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        GradientDrawable shapeBtn =  new GradientDrawable();
        shapeBtn.setCornerRadius(10);
        shapeBtn.setColor(Color.parseColor(GetTheme.colorPrimary));
        btnSR.setBackground(shapeBtn);

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

        img_photo_id=(ImageView) v.findViewById(R.id.img_photo_id);

        img_photo_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i("click","yes");

                if (ContextCompat.checkSelfPermission(getActivity(),

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    selectImage();
                }
                return false;
            }
        });

        return v;
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if(v == ll1)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll1.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt1.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;

                text = "1";
            }
        }
        else if(v == ll2)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll2.setBackground(shapeBgSelect);
                ll1.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt2.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "2";
            }
        }
        else if(v == ll3)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll3.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt3.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "3";
            }
        }
        else if(v == ll4)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll4.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt4.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "4";
            }
        }
        else if(v == ll5)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll5.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt5.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "5";
            }
        }
        else if(v == ll6)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll6.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt6.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "6";
            }
        }
        else if(v == ll7)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll7.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt7.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "7";
            }
        }
        else if(v == ll8)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll8.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll1.setBackground(shapeBg);
                ll9.setBackground(shapeBg);

                txt8.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt9.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "8";
            }
        }
        else if(v == ll9)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ll9.setBackground(shapeBgSelect);
                ll2.setBackground(shapeBg);
                ll3.setBackground(shapeBg);
                ll4.setBackground(shapeBg);
                ll5.setBackground(shapeBg);
                ll6.setBackground(shapeBg);
                ll7.setBackground(shapeBg);
                ll8.setBackground(shapeBg);
                ll1.setBackground(shapeBg);

                txt9.setTextColor(Color.parseColor(GetTheme.colorTextLight));
                txt2.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt3.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt4.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt5.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt6.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt7.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt8.setTextColor(Color.parseColor(GetTheme.colorPrimary));
                txt1.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                clicked=false;
                text = "9";
            }
        }

        if(v == rlArrivalCar)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivArrivalCar.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivArrivalBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departArrival = "car";
            }
        }
        else if(v == rlArrivalBus)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivArrivalBus.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivArrivalCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departArrival = "bus";
            }
        }
        else if(v == rlArrivalRail)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivArrivalRail.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivArrivalCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
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
                ivArrivalRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivArrivalAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departArrival = "flight";
            }
        }

        if(v == rlDepartureCar)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivDepartureCar.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivDepartureBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departDeparture = "car";
            }
        }
        else if(v == rlDepartureBus)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivDepartureBus.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivDepartureCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departDeparture = "bus";
            }
        }
        else if(v == rlDepartureRail)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivDepartureRail.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
                ivDepartureCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                clicked=false;
                departDeparture = "train";
            }
        }
        else if(v == rlDepartureAirplane)
        {
            if(!clicked)
            {
                clicked=true;
            }
            else
            {
                ivDepartureRail.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureCar.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureBus.setColorFilter(Color.parseColor(GetTheme.colorIconDark), PorterDuff.Mode.SRC_IN);
                ivDepartureAirplane.setColorFilter(Color.parseColor(GetTheme.colorIconLight), PorterDuff.Mode.SRC_IN);
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
                joRsvp.put("guest_image",encodedImgpath);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(
                        Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri tempUri = getImageUri(getActivity(), bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                img_photo_id.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                InputStream in = null;
                try {
                    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                    in = getActivity().getContentResolver().openInputStream(selectedImageUri);

                    // Decode image size
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(in, null, o);
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int scale = 1;
                    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                            IMAGE_MAX_SIZE) {
                        scale++;

                    }
                    in = getActivity().getContentResolver().openInputStream(selectedImageUri);
                    if (scale > 1) {
                        scale--;

                        o = new BitmapFactory.Options();
                        o.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeStream(in, null, o);

                        // resize to desired dimensions
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();

                        double y = Math.sqrt(IMAGE_MAX_SIZE
                                / (((double) width) / height));
                        double x = (y / height) * width;

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) x,
                                (int) y, true);
                        bitmap.recycle();
                        bitmap = scaledBitmap;

                        System.gc();
                    } else {
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                    in.close();

                } catch (Exception ignored) {

                }

                Uri tempUri = getImageUri(getActivity(), bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                img_photo_id.setImageBitmap(bitmap);

            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
