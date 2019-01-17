package com.example.naray.knowyourgovernment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by naray on 4/10/2017.
 */

public class PhotoActivity extends AppCompatActivity {
    private TextView pLocationDisplay;
    private TextView pOfficesName;
    private TextView pOfficialName;
    private ImageView pOfficialImage;
    Representative rep1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        pLocationDisplay = (TextView)findViewById(R.id.pDisplay);
        pOfficesName = (TextView)findViewById(R.id.pOfficesName);
        pOfficialName = (TextView)findViewById(R.id.pOfficialName);
        pOfficialImage = (ImageView)findViewById(R.id.pImageView);

        Intent intent = getIntent();
        if(intent.hasExtra("pHeading")){
            String location = intent.getStringExtra("pHeading");
            pLocationDisplay.setText(location);
        }

        if(intent.hasExtra("pOfficial")){
            rep1 = (Representative)getIntent().getSerializableExtra("pOfficial");
            String officesName = rep1.getrepofficesName().toString();
            pOfficesName.setText(officesName);
            String officialName = rep1.getrepofficialName().toString();
            pOfficialName.setText(officialName);
            String ofcparty = rep1.getrepParty().toString();
            if (ofcparty.equals("Democratic") || ofcparty.equals("Democrat"))
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            if (ofcparty.equals("Republican"))
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            if ((!(ofcparty.equals("Republican"))) && (!(ofcparty.equals("Democratic"))) && (!(ofcparty.equals("Democrat"))))
                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
            final String imageUrl = rep1.getrepphotoUrl().toString();
            if (!(imageUrl.equals("No Data Provided")) ) {
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
// Here we try https if the http image attempt failed
                        final String changedUrl = imageUrl.replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .into(pOfficialImage);
                    }
                }).build();
                picasso.load(imageUrl)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.placeholder)
                        .into(pOfficialImage);
            } else {
                /*Picasso.with(this).load(imageUrl)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missing)
                        .into(pOfficialImage);*/
                return;
            }

        }
    }
}
