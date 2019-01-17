package com.example.naray.knowyourgovernment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by naray on 4/9/2017.
 */

public class RepresentativeActivity extends AppCompatActivity {

    private  TextView locationDisplay;
    private TextView officesName;
    private TextView officialName;
    private TextView party;
    private ImageView officialImage;
    private TextView addressLabel;
    private TextView officialAddress;
    private TextView phoneLabel;
    private TextView officialPhone;
    private TextView emailLabel;
    private TextView officialEmail;
    private TextView website;
    private TextView officialWebsite;
    private ImageView Youtube;
    private ImageView GooglePlus;
    private ImageView Twitter;
    private ImageView Facebook;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String webUrl;
    private String gPlusID;
    private String fbID;
    private String tweetID;
    private String youtubeID;
    private String imageUrl;
    private String location;
    Representative rep;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep);

         locationDisplay = (TextView)findViewById(R.id.rdisplay);
         officesName = (TextView)findViewById(R.id.rOfficesName);
         officialName = (TextView)findViewById(R.id.rOfficialName);
         party = (TextView)findViewById(R.id.rParty);
         officialImage = (ImageView)findViewById(R.id.rimageView);
         addressLabel = (TextView)findViewById(R.id.rAddress);
         officialAddress = (TextView)findViewById(R.id.rAddressLines);
         phoneLabel = (TextView)findViewById(R.id.rPhone);
         officialPhone = (TextView)findViewById(R.id.rPhoneLine);
         emailLabel = (TextView)findViewById(R.id.rEmail);
         officialEmail = (TextView)findViewById(R.id.rEmailLine);
         website = (TextView)findViewById(R.id.rWebsite);
         officialWebsite = (TextView)findViewById(R.id.rWebsiteLine);
         Youtube = (ImageView)findViewById(R.id.rYouTube);
         GooglePlus = (ImageView)findViewById(R.id.rGooglePlus);
         Twitter = (ImageView)findViewById(R.id.rTwitter);
         Facebook = (ImageView)findViewById(R.id.rFacebook);

        Intent intent = getIntent();
        if(intent.hasExtra("heading")){
            location = intent.getStringExtra("heading");
            locationDisplay.setText(location);
        }
        if(intent.hasExtra("Official")) {
            rep = (Representative) getIntent().getSerializableExtra("Official");
            String designation = rep.getrepofficesName().toString();
            officesName.setText(designation);
            String name = rep.getrepofficialName().toString();
            officialName.setText(name);
            String ofcparty = rep.getrepParty().toString();
            party.setText("("+ofcparty+")");
            if (ofcparty.equals("Democratic") || ofcparty.equals("Democrat"))
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            if (ofcparty.equals("Republican"))
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            if ((!(ofcparty.equals("Republican"))) && (!(ofcparty.equals("Democratic"))) && (!(ofcparty.equals("Democrat"))))
                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
            imageUrl = rep.getrepphotoUrl().toString();
            if (!(imageUrl.equals("No Data Provided")) && (imageUrl!=null)) {
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
                        final String changedUrl = imageUrl.replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .into(officialImage);
                    }
                }).build();
                picasso.load(imageUrl)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.placeholder)
                        .into(officialImage);
            } else {
                Picasso.with(this).load(imageUrl)
                        //.error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missing)
                        //.error(R.drawable.brokenimage)
                        .into(officialImage);
            }
            String addressLine1 = rep.getrepaddressLine1().toString();
            String addressLine2 = rep.getrepaddressLine2().toString();
            String addressLine3 = rep.getrepaddressLine3().toString();
              if((addressLine1 !="No Data Provided")&&(addressLine2!="No Data Provided")&&(addressLine3 !="No Data Provided")) {
                  address = addressLine1+addressLine2+"\n"+addressLine3;
                  officialAddress.setPaintFlags(officialAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                  officialAddress.setText(address);
              }
              if((addressLine3.equals("No Data Provided"))&&(addressLine1!="No Data Provided")&&(addressLine2!="No Data Provided")){
                  address = addressLine1+addressLine2;
                  officialAddress.setPaintFlags(officialAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                  officialAddress.setText(address);
              }
              if((addressLine2.equals("No Data Provided"))&&(addressLine1!="No Data Provided")&&(addressLine3!="No Data Provided")){
                  address = addressLine1+"\n"+addressLine3;
                  officialAddress.setPaintFlags(officialAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                  officialAddress.setText(address);
              }
              if((addressLine1.equals("No Data Provided"))&&(addressLine2!="No Data Provided")&&(addressLine3!="No Data Provided")){
                  address = addressLine2+"\n"+addressLine3;
                  officialAddress.setPaintFlags(officialAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                  officialAddress.setText(address);
              }
              if((addressLine1.equals("No Data Provided"))&&(addressLine2.equals("No Data Provided"))&&(addressLine3.equals("No Data Provided")))
              {
                  address = "No Data Provided";
                  officialAddress.setText(address);
              }

              phoneNumber = rep.getrepPhones();
              if(phoneNumber!=null && (phoneNumber!= "No Data Provided")) {
                  officialPhone.setPaintFlags(officialPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                  officialPhone.setText(phoneNumber);
              }
              else{
                  officialPhone.setText("No Data Provided");
              }
              emailAddress = rep.getrepEmails().toString();
              officialEmail.setText(emailAddress);
              webUrl = rep.getrepUrls();
              officialWebsite.setText(webUrl);
              String gPlusType = rep.getgoogleplusType();
              gPlusID = rep.getgoogleplusID();
              if((gPlusType.equals("No Data Provided"))&&(gPlusID.equals("No Data Provided"))){
                  GooglePlus.setVisibility(View.INVISIBLE);
              }
              String youtubeType = rep.getrepyoutubeType();
              youtubeID = rep.getrepyoutubeID();
              if((youtubeType.equals("No Data Provided"))&&(youtubeID.equals("No Data Provided"))){
                  Youtube.setVisibility(View.INVISIBLE);
              }
              String fbType = rep.getrepfacebookType();
              fbID = rep.getrepfacebookID();
              if((fbType.equals("No Data Provided"))&&(fbID.equals("No Data Provided"))){
                  Facebook.setVisibility(View.INVISIBLE);
              }
              String tweetType = rep.getreptwitterType();
              tweetID = rep.getreptwitterID();
              if((tweetType.equals("No Data Provided"))&&(tweetID.equals("No Data Provided"))){
                  Twitter.setVisibility(View.INVISIBLE);
              }
        }
    }

   public void openPhotoActivity(View v){
       if(!imageUrl.equals("No Data Provided")){
         Intent intent = new Intent(RepresentativeActivity.this,PhotoActivity.class);
         intent.putExtra("pHeading",location);
         intent.putExtra("pOfficial",rep);
         startActivity(intent);
       }

   }

    public void facebookClicked(View v) {
        if (!(fbID.equals("No Data Provided"))) {
            String FACEBOOK_URL = "https://www.facebook.com/" + fbID;
            String urlToUse;
            PackageManager packageManager = getPackageManager();
            try {
                int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) { //newer versions of fb app
                    urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                } else { //older versions of fb app
                    urlToUse = "fb://page/" + fbID;
                }
            } catch (PackageManager.NameNotFoundException e) {
                urlToUse = FACEBOOK_URL; //normal web url
            }
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            facebookIntent.setData(Uri.parse(urlToUse));
            startActivity(facebookIntent);
        }

    }

    public void twitterClicked(View v) {
        if (!(tweetID.equals("No Data Provided"))) {
            Intent intent = null;
            String name = tweetID;
            try {
                // get the Twitter app if possible
                getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
            }
            startActivity(intent);
        }
    }

    public void googlePlusClicked(View v) {
        if (!(gPlusID.equals("No Data Provided"))) {
            String name = gPlusID;
            Intent intent = null;
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setClassName("com.google.android.apps.plus",
                        "com.google.android.apps.plus.phone.UrlGatewayActivity");
                intent.putExtra("customAppUri", name);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://plus.google.com/" + name)));
            }
        }
    }

    public void youTubeClicked(View v) {
        if (!(youtubeID.equals("No Data Provided"))) {
            String name = youtubeID;
            Intent intent = null;
            try {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/" + name));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/" + name)));
            }
        }
    }

    public void onClickAddress (View v) {

        if (!(address.equals("No Data Provided"))) {
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

            Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                makeErrorAlert("No Application found that handles ACTION_VIEW (geo) intents");
               // Toast.makeText(this,"No Application found that handles the action",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickPhone(View v) {

        if (!(phoneNumber.equals("No Data Provided"))) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                makeErrorAlert("No Application found that handles ACTION_DIAL (tel) intents");
            }
        }
    }

    public void onClickEmail (View v) {

        if (!(emailAddress.equals("No Data Provided"))) {
            String[] addresses = new String[]{emailAddress};

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email from Sample Implied Intent App");
            intent.putExtra(Intent.EXTRA_TEXT, "Email text body...");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                makeErrorAlert("No Application found that handles SENDTO (mailto) intents");
            }
        }
    }

    public void onClickWeb(View v) {
        if(!(webUrl.equals("No Data Provided"))) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(webUrl));
            startActivity(i);
        }
    }


    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
