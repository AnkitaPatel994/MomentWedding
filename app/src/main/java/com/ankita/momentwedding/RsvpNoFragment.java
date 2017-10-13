package com.ankita.momentwedding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        txtReason = (EditText)v.findViewById(R.id.txtReason);
        txtWishes = (EditText)v.findViewById(R.id.txtWishes);
        btnSRNo = (Button) v.findViewById(R.id.btnSRNo);

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

        public GetRSVPListNo(String reason, String wishes) {

            this.reason = reason;
            this.wishes = wishes;

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
            if(status.equals("1"))
            {
                Intent i = new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
