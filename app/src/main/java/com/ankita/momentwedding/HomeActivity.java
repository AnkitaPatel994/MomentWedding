package com.ankita.momentwedding;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager vpAdminEvent;
    TabLayout tabAdminEventLayout;
    ImageView ivClock;
    TextView txtRsvp,txtDate,txtDays,txtHours,txtMins,txtSecs,txtGuestName;
    SessionManager session;
    Dialog dialog;
    CircleImageView ivGuestPic;

    Bitmap bitmap = null;
    String str_imgpath,encodedImgpath;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    public static String wedding_id,profile_id,guest_id;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setStatusBarColor(Color.parseColor(GetTheme.colorPrimaryDark));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(GetTheme.colorPrimary)));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Profile");

        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        guest_id = user.get(SessionManager.guest_id);
        wedding_id = user.get(SessionManager.wedding_id);
        profile_id = user.get(SessionManager.profile_id);

        /*------------------ Navigation Header ------------------*/

        View header=navigationView.getHeaderView(0);

        LinearLayout llBGHeaderColor = (LinearLayout)header.findViewById(R.id.llBGHeaderColor);
        llBGHeaderColor.setBackgroundColor(Color.parseColor(GetTheme.colorPrimary));

        txtGuestName = (TextView)header.findViewById(R.id.txtGuestName);
        txtGuestName.setTextColor(Color.parseColor(GetTheme.colorTextLight));

        ivGuestPic = (CircleImageView)header.findViewById(R.id.ivGuestPic);
        ivGuestPic.setBorderColor(Color.parseColor(GetTheme.colorPrimaryDark));

        GetGuestListone getGuestListone = new GetGuestListone();
        getGuestListone.execute();

        ivGuestPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i("click","yes");

                if (ContextCompat.checkSelfPermission(HomeActivity.this,

                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        selectImage();
                    } else {
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                        // No explanation needed, we can request the permission.
                    }
                } else {
                    selectImage();
                }

                return false;
            }
        });

        RelativeLayout rlBgColor = (RelativeLayout)findViewById(R.id.rlBgColor);
        rlBgColor.setBackgroundColor(Color.parseColor(GetTheme.colorBg));

        RelativeLayout rlBgImgHome = (RelativeLayout)findViewById(R.id.rlBgImgHome);
        rlBgImgHome.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.imgi));

        vpAdminEvent = (ViewPager)findViewById(R.id.vpAdminEvent);
        setupViewPager(vpAdminEvent);

        tabAdminEventLayout =(TabLayout)findViewById(R.id.tabAdminEventLayout);
        tabAdminEventLayout.setSelectedTabIndicatorColor(Color.parseColor(GetTheme.colorTextLight));
        tabAdminEventLayout.setBackgroundColor(Color.parseColor(GetTheme.colorPrimary));

        tabAdminEventLayout.setupWithViewPager(vpAdminEvent);

        vpAdminEvent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0)
                {
                    setTitle("Profile");
                }
                else if(position == 1)
                {
                    setTitle("Functions");
                    navigationView.getMenu().getItem(2).setChecked(true);
                }
                else if(position == 2)
                {
                    setTitle("Gallery");
                    navigationView.getMenu().getItem(3).setChecked(true);
                }
                else if(position == 3)
                {
                    setTitle("Invitation");
                    navigationView.getMenu().getItem(4).setChecked(true);
                }
                else if(position == 4)
                {
                    setTitle("Family Member");
                    navigationView.getMenu().getItem(5).setChecked(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Drawable dProfile = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profile_black_24dp).mutate();
        dProfile.setColorFilter(Color.parseColor(GetTheme.colorTextLight), PorterDuff.Mode.SRC_ATOP);

        Drawable dSchedule = ContextCompat.getDrawable(getApplicationContext(), R.drawable.schedule).mutate();
        dSchedule.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_ATOP);

        Drawable dGallery = ContextCompat.getDrawable(getApplicationContext(), R.drawable.gallery).mutate();
        dGallery.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_ATOP);

        Drawable dInvite = ContextCompat.getDrawable(getApplicationContext(), R.drawable.invite).mutate();
        dInvite.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_ATOP);

        Drawable dMembers = ContextCompat.getDrawable(getApplicationContext(), R.drawable.members).mutate();
        dMembers.setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_ATOP);

        tabAdminEventLayout.getTabAt(0).setIcon(dProfile);
        tabAdminEventLayout.getTabAt(1).setIcon(dSchedule);
        tabAdminEventLayout.getTabAt(2).setIcon(dGallery);
        tabAdminEventLayout.getTabAt(3).setIcon(dInvite);
        tabAdminEventLayout.getTabAt(4).setIcon(dMembers);

        tabAdminEventLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor(GetTheme.colorTextLight), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor(GetTheme.colorIcon), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*---------------- RSVP ------------------*/
        txtRsvp =(TextView) findViewById(R.id.txtRsvp);
        txtRsvp.setTextColor(Color.parseColor(GetTheme.colorTextLight));
        txtRsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RSVPActivity.class);
                startActivity(i);
            }
        });

        /*---------------- Clock ------------------*/
        ivClock =(ImageView)findViewById(R.id.ivClock);
        ivClock.setColorFilter(Color.parseColor(GetTheme.colorTextLight), PorterDuff.Mode.SRC_IN);
        ivClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(HomeActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.clock_dialog);
                dialog.setCancelable(true);

                TextView countdown = (TextView)dialog.findViewById(R.id.countdown);
                countdown.setTextColor(Color.parseColor(GetTheme.colorPrimary));

                txtDate =(TextView)dialog.findViewById(R.id.txtDate);
                txtDate.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                txtDays =(TextView)dialog.findViewById(R.id.txtDays);
                txtDays.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                txtHours =(TextView)dialog.findViewById(R.id.txtHours);
                txtHours.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                txtMins =(TextView)dialog.findViewById(R.id.txtMins);
                txtMins.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                txtSecs =(TextView)dialog.findViewById(R.id.txtSecs);
                txtSecs.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                TextView tvDays =(TextView)dialog.findViewById(R.id.tvDays);
                tvDays.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                TextView tvHours =(TextView)dialog.findViewById(R.id.tvHours);
                tvHours.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                TextView tvMins =(TextView)dialog.findViewById(R.id.tvMins);
                tvMins.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                TextView tvSecs =(TextView)dialog.findViewById(R.id.tvSecs);
                tvSecs.setTextColor(Color.parseColor(GetTheme.colorTextLight));

                LinearLayout llDialogOther = (LinearLayout)dialog.findViewById(R.id.llDialogOther);
                llDialogOther.setBackgroundColor(Color.parseColor(GetTheme.colorTransparentDark));

                LinearLayout llTopDialog = (LinearLayout)dialog.findViewById(R.id.llTopDialog);

                GradientDrawable shapeTop =  new GradientDrawable();
                shapeTop.setCornerRadii(new float[] { 18, 18, 18, 18, 0, 0, 0, 0 });
                shapeTop.setColor(Color.parseColor(GetTheme.colorClockTopBg));
                llTopDialog.setBackground(shapeTop);

                LinearLayout llBottomDialog = (LinearLayout)dialog.findViewById(R.id.llBottomDialog);
                GradientDrawable shapeBottom =  new GradientDrawable();
                shapeBottom.setCornerRadii(new float[] { 0, 0, 0, 0, 18, 18, 18, 18 });
                shapeBottom.setColor(Color.parseColor(GetTheme.colorPrimary));
                llBottomDialog.setBackground(shapeBottom);

                LinearLayout llDialog = (LinearLayout)dialog.findViewById(R.id.llDialog);

                llDialogOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                llDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                    }
                });

                dialog.show();

                GetCountDown getCountDown = new  GetCountDown();
                getCountDown.execute();

            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

                Uri tempUri = getImageUri(HomeActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivGuestPic.setImageBitmap(bitmap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                InputStream in = null;
                try {
                    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                    in = HomeActivity.this.getContentResolver().openInputStream(selectedImageUri);

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
                    in = HomeActivity.this.getContentResolver().openInputStream(selectedImageUri);
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

                Uri tempUri = getImageUri(HomeActivity.this, bitmap);
                str_imgpath = getRealPathFromURI(tempUri);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                byte[] b = bytes.toByteArray();
                encodedImgpath = Base64.encodeToString(b, Base64.DEFAULT);

                ivGuestPic.setImageBitmap(bitmap);
            }
            GetImgPicUpload imgPicUpload = new GetImgPicUpload();
            imgPicUpload.execute();
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = HomeActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Groom_profile)
        {
            Intent intent = new Intent(getApplicationContext(),OneProfileActivity.class);
            intent.putExtra("profile_id",ProfileFragment.groomId);
            intent.putExtra("GB","Groom");
            startActivity(intent);
        }
        else if (id == R.id.nav_Bride_profile)
        {
            Intent intent = new Intent(getApplicationContext(),OneProfileActivity.class);
            intent.putExtra("profile_id",ProfileFragment.brideId);
            intent.putExtra("GB","Bride");
            startActivity(intent);
        }
        else if (id == R.id.nav_functions)
        {
            vpAdminEvent.setCurrentItem(1);
        }
        else if (id == R.id.nav_gallery)
        {
            vpAdminEvent.setCurrentItem(2);
        }
        else if (id == R.id.nav_invitation)
        {
            vpAdminEvent.setCurrentItem(3);
        }
        else if (id == R.id.nav_fm)
        {
            vpAdminEvent.setCurrentItem(4);
        }
        else if (id == R.id.nav_vendor)
        {
            Intent intent = new Intent(getApplicationContext(),VendorActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.ankita.momentwedding";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }
        /*else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.momentwedding"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ankita.momentwedding"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }*/
        else if (id == R.id.nav_logout)
        {
            session.logoutUser();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {

        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());

        adapter.addFrag(new ProfileFragment());
        adapter.addFrag(new ScheduleFragment());
        adapter.addFrag(new GalleryFragment());
        adapter.addFrag(new InviteFragment());
        adapter.addFrag(new MembarFragment());

        viewPager.setAdapter(adapter);
    }

    private class GetCountDown extends AsyncTask<String,Void,String> {

        String status,message,wedding_date;
        long countdown;

        @Override
        protected String doInBackground(String... strings) {

            JSONObject joCount=new JSONObject();
            try {

                joCount.put("wedding_id",HomeActivity.wedding_id);
                Postdata postdata=new Postdata();
                String pdPro=postdata.post(MainActivity.mainUrl+"weddingCountDown",joCount.toString());
                JSONObject j=new JSONObject(pdPro);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    countdown=j.getLong("countdown");
                    wedding_date=j.getString("wedding_date");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            txtDate.setText(wedding_date);

            CountDownTimer timer = new CountDownTimer(countdown, 1000) {
                @Override
                public void onTick(long l) {

                   long count = l;

                    txtDays.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toDays(l)));

                    count = count-(Long.parseLong(txtDays.getText().toString())*1000*60*60*24);

                    txtHours.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toHours(count)));

                    count = count-(Long.parseLong(txtHours.getText().toString())*1000*60*60);

                    txtMins.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toMinutes(count)));

                    txtSecs.setText(""+String.format("%d",
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes(l))));
                }

                @Override
                public void onFinish() {
                    ivClock.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            };
            timer.start();
        }
    }

    private class GetGuestListone extends AsyncTask<String,Void,String> {
        String status,message,name,image;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject jogl=new JSONObject();
            try {

                jogl.put("guest_id",HomeActivity.guest_id);
                Postdata postdata=new Postdata();
                String pdgl=postdata.post(MainActivity.mainUrl+"guestDetails",jogl.toString());
                JSONObject j=new JSONObject(pdgl);
                status=j.getString("status");
                if(status.equals("1"))
                {
                    Log.d("Like","Successfully");
                    message=j.getString("message");
                    JSONObject jo=j.getJSONObject("guest");

                    name =jo.getString("name");
                    image =jo.getString("image");
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
                txtGuestName.setText(name);
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheOnDisc(true).cacheInMemory(true)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .displayer(new FadeInBitmapDisplayer(300)).build();
                final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(HomeActivity.this)
                        .defaultDisplayImageOptions(defaultOptions)
                        .memoryCache(new WeakMemoryCache())
                        .discCacheSize(100 * 1024 * 1024).build();

                ImageLoader.getInstance().init(config);

                ImageLoader imageLoader = ImageLoader.getInstance();
                int fallback = 0;
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(fallback)
                        .showImageOnFail(fallback)
                        .showImageOnLoading(fallback).build();


                imageLoader.displayImage(image,ivGuestPic, options);

            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetImgPicUpload extends AsyncTask<String,Void,String> {

        String status,message;
        @Override
        protected String doInBackground(String... strings) {

            JSONObject joImg=new JSONObject();
            try {

                joImg.put("guest_id",HomeActivity.guest_id);
                joImg.put("guest_image",encodedImgpath);
                Postdata postdata=new Postdata();
                String pdImg=postdata.post(MainActivity.mainUrl+"guestImageUpdate",joImg.toString());
                JSONObject j=new JSONObject(pdImg);
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
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
