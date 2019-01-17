package com.example.naray.knowyourgovernment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener,AsyncInterface {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<Representative> repList = new ArrayList<>();
    private RepresentativeAdapter repAdapter;
    private MainActivity ma;
    private Locator locator;
    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(networkCheck()) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);

            repAdapter = new RepresentativeAdapter(repList, this);

            recyclerView.setAdapter(repAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            locator = new Locator(this);
        }
        else{
            setContentView(R.layout.nonetwork);
            ((TextView)findViewById(R.id.ndisplay)).setText("No Data For Location");
            //((TextView)findViewById(R.id.nText1)).setText("No Network Connection");
            //((TextView)findViewById(R.id.nText2)).setText("Data cannot be accessed/loaded without an internet connection");
            //((TextView)findViewById(R.id.display)).setText("No Data For Location");
        }

    }

    /*@Override
    protected void onDestroy() {
        if(locator!=null || networkCheck()) {
            Log.d(TAG, "onDestroy: ");
            locator.shutDown();
            super.onDestroy();
        }
    }*/

    /*@Override
    protected void onResume(){
       // locator = new Locator(this);
        super.onResume();
    }*/

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildLayoutPosition(v);
        onItemClick(v,position);
    }

    public void onItemClick(View v,int idx){
       Intent intent = new Intent(MainActivity.this,RepresentativeActivity.class);
       Representative rep = repList.get(idx);
       intent.putExtra("heading",currentLocation);
       intent.putExtra("Official",rep);
       startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        int position = recyclerView.getChildLayoutPosition(v);
        onItemClick(v,position);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                //et.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                et.setGravity(Gravity.CENTER_HORIZONTAL);

                builder.setView(et);
                builder.setMessage("Enter a City,State or a Zip Code:");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String userEntry = et.getText().toString().trim();
                        if(userEntry.matches("\\d+")) {
                            CivicInfoDownloader cid;
                            if(networkCheck()){
                            cid = new CivicInfoDownloader(MainActivity.this);
                            cid.execute(userEntry);}

                        }
                        else {
                            Geocoder geocoder = new Geocoder(MainActivity.this);
                            try {
                                List<Address> addresses = geocoder.getFromLocationName(userEntry, 10);
                                for(Address a : addresses){
                                    if(a.hasLatitude()&&a.hasLongitude())
                                        doLocationWork(a.getLatitude(),a.getLongitude());
                                }
                            }
                            catch (IOException e){
                                e.toString();
                            }
                        }

                        //AsyncLoaderTask asyncTask = new AsyncLoaderTask(MainActivity.this);
                        //asyncTask.execute(userEnteredStockSymbol);

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.about:
                aboutActivity();
                break;

            default:
               return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void aboutActivity() {
        Toast.makeText(MainActivity.this, "About Selected", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Address cannot be acquired from provided latitude/longitude",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Calling super onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }

    public boolean networkCheck(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }


    public void doLocationWork(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try{
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            for(Address address:addresses){
                //for(int i=0;i<address.getMaxAddressLineIndex();i++) {
                    String postalCode = address.getPostalCode();
                    //((TextView) findViewById(R.id.display)).setText(postalCode);
                    CivicInfoDownloader cid = new CivicInfoDownloader(MainActivity.this);
                    cid.execute(postalCode);
                //}

            }

        }
        catch(IOException e){
           Toast.makeText(this,"Address cannot be acquired from provide latitude/longitude",Toast.LENGTH_SHORT).show();
        }

    }

    public void setOfficialList(Object[] results){

             if(results!=null ){
                 currentLocation = results[0].toString();
                 ((TextView)findViewById(R.id.display)).setText(results[0].toString());
                 repList.clear();
                 //Representative rep = ((Representative)results[1]);
                 //repList = (List<Representative>)results[1];
                 repList.addAll((List<Representative>)results[1]);
                 repAdapter.notifyDataSetChanged();
             }
             else{
                 ((TextView)findViewById(R.id.display)).setText("No Data For Location");
                 repList.clear();
                 repAdapter.notifyDataSetChanged();
             }

    }


}
