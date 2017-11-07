package com.ankita.momentwedding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RsvpNoFragment extends Fragment {

    View v;
    EditText txtReason,txtWishes;
    Button btnSRNo;

    public RsvpNoFragment() {
        // Required empty public constructor
    }

    public static RsvpNoFragment newInstance(String param1, String param2) {
        RsvpNoFragment fragment = new RsvpNoFragment();
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
        v = inflater.inflate(R.layout.fragment_rsvp_no, container, false);

        TextView tvReason = (TextView)v.findViewById(R.id.tvReason);
        tvReason.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        TextView tvWishes = (TextView)v.findViewById(R.id.tvWishes);
        tvWishes.setTextColor(Color.parseColor(GetTheme.colorTextDark));

        txtReason = (EditText)v.findViewById(R.id.txtReason);
        txtReason.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        txtReason.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));

        txtWishes = (EditText)v.findViewById(R.id.txtWishes);
        txtWishes.setTextColor(Color.parseColor(GetTheme.colorTextDark));
        txtWishes.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(GetTheme.colorPrimary)));

        btnSRNo = (Button) v.findViewById(R.id.btnSRNo);
        btnSRNo.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        GradientDrawable shapeBtn =  new GradientDrawable();
        shapeBtn.setCornerRadius(10);
        shapeBtn.setColor(Color.parseColor(GetTheme.colorPrimary));
        btnSRNo.setBackground(shapeBtn);

        btnSRNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Reason = txtReason.getText().toString();
                String Wishes = txtWishes.getText().toString();

                GetRSVPListNo getRSVPListNo = new GetRSVPListNo(Reason,Wishes);
                getRSVPListNo.execute();

            }
        });

        return v;
    }

    private class GetRSVPListNo extends AsyncTask<String,Void,String> {

        String reason,wishes,status,message;
        ProgressDialog dialog;

        public GetRSVPListNo(String reason, String wishes) {

            this.reason = reason;
            this.wishes = wishes;

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

            JSONObject joRsvpNo=new JSONObject();
            try {

                joRsvpNo.put("guest_id",HomeActivity.guest_id);
                joRsvpNo.put("attending","no");
                joRsvpNo.put("reason",reason);
                joRsvpNo.put("wishes",wishes);

                Postdata postdata=new Postdata();
                String pdRsvpNo=postdata.post(MainActivity.mainUrl+"guestRsvp",joRsvpNo.toString());
                JSONObject j=new JSONObject(pdRsvpNo);
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
}
