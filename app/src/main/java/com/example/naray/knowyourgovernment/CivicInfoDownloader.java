package com.example.naray.knowyourgovernment;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by naray on 4/6/2017.
 */

public class CivicInfoDownloader extends AsyncTask<String,Void,String> {

    private static final String TAG = "CivicInfoDownloader";
    private MainActivity mainActivity;
    private AsyncInterface res;
    private final String civicInfourl="https://www.googleapis.com/civicinfo/v2/representatives?key="+"AIzaSyDlIhkNypRoPCEVzqWM7cLABTUiWOi8XAw";
   /* private final String APIKEY = "AIzaSyDlIhkNypRoPCEVzqWM7cLABTUiWOi8XAw";*/
    String zipcode;
    ArrayList<Representative> representatives = new ArrayList<>();
    List<ArrayList> repList = new ArrayList<>();
    Representative representative;
    //List<Representative> o = new ArrayList<>(2);
    Object[] o  = new Object[2];

    public CivicInfoDownloader(AsyncInterface activityContext){
        this.res = activityContext;
    }


    @Override
    protected void onPostExecute(String s){

        if(s!=null ){
            try {
                JSONObject jsonObjects = new JSONObject(s);
                String city = jsonObjects.getJSONObject("normalizedInput").getString("city");
                String state = jsonObjects.getJSONObject("normalizedInput").getString("state");
                String zip = jsonObjects.getJSONObject("normalizedInput").getString("zip");
                o[0] = city + ", " + state + " " + zip;
                JSONArray array1 = (JSONArray) jsonObjects.get("offices");
                JSONArray officialIndicesArray ;
                //int[] list;
                // List<Integer> indices = new ArrayList<Integer>();
                String officesname;

                int y;
                for (int i = 0; i < array1.length(); i++) {
                    JSONObject jsonObject = array1.getJSONObject(i);
                    officesname = jsonObject.getString("name");
                    officialIndicesArray = jsonObject.optJSONArray("officialIndices");
                    int oi[] = new int[officialIndicesArray.length()];
                    for(y=0;y<officialIndicesArray.length();y++)
                    {
                        oi[y]=officialIndicesArray.getInt(y);
                    }
                    JSONArray array3 = (JSONArray) jsonObjects.get("officials");
                    for (int j = 0; j < officialIndicesArray.length(); j++) {
                        //for (int t = 0; t < officialIndicesArray.getJSONArray(j).length(); t++) {

                       /* int index = officialIndicesArray.optJSONArray(k).optInt(j);*/
                        JSONObject officialsJsonObject = array3.getJSONObject(oi[j]);
                        String officialname=null; if( officialsJsonObject.has("name"))
                            officialname = officialsJsonObject.getString("name");
                        else
                            officialname = "No Data Provided";
                        String addressLine1 =null; if(officialsJsonObject.has("address")&&officialsJsonObject.getJSONArray("address").getJSONObject(0).has("line1"))
                            addressLine1=officialsJsonObject.getJSONArray("address").getJSONObject(0).getString("line1");
                        else
                            addressLine1= "No Data Provided";
                        String addressLine2 =null; if(officialsJsonObject.has("address")&&officialsJsonObject.getJSONArray("address").getJSONObject(0).has("line2"))
                            addressLine2=officialsJsonObject.getJSONArray("address").getJSONObject(0).getString("line2");
                        else
                            addressLine2 = "No Data Provided";
                        String addressCity = null; if(officialsJsonObject.has("address")&&officialsJsonObject.getJSONArray("address").getJSONObject(0).has("city"))
                            addressCity = officialsJsonObject.getJSONArray("address").getJSONObject(0).getString("city");
                        else
                            addressCity = "No Data Provided";
                        String addressState = null; if(officialsJsonObject.has("address")&&officialsJsonObject.getJSONArray("address").getJSONObject(0).has("state"))
                            addressState = officialsJsonObject.getJSONArray("address").getJSONObject(0).getString("state");
                        else
                            addressState = "No Data Provided";
                        String addresszip = null; if(officialsJsonObject.has("address")&&officialsJsonObject.getJSONArray("address").getJSONObject(0).has("zip"))
                            addresszip = officialsJsonObject.getJSONArray("address").getJSONObject(0).getString("zip");
                        else
                            addresszip = "No Data Provided";
                        String addressLine3 = null;
                        //if(addressCity!=null&&addressState!=null&&addresszip!=null)
                            if(addressCity!="No Data Provided"&&addressState!="No Data Provided"&&addresszip!="No Data Provided")
                            addressLine3 = addressCity + ", " + addressState + " " + addresszip;
                        else
                            addressLine3 = "No Data Provided";
                        String officialparty = null; if(officialsJsonObject.has("party"))
                            officialparty = officialsJsonObject.getString("party");
                        else
                            officialparty = "No Data Provided";
                        String officialphones = null; if(officialsJsonObject.has("phones"))
                            officialphones = officialsJsonObject.getJSONArray("phones").getString(0);
                        else
                            officialparty = "No Data Provided";
                        String officialurl = null; if(officialsJsonObject.has("urls"))
                            officialurl = officialsJsonObject.getJSONArray("urls").getString(0);
                        else
                            officialurl = "No Data Provided";
                        String officialemails=null; if( officialsJsonObject.has("emails"))
                            officialemails = officialsJsonObject.getJSONArray("emails").getString(0);
                        else
                            officialemails = "No Data Provided";
                        String officialphotourl = null; if(officialsJsonObject.has("photoUrl"))
                            officialphotourl = officialsJsonObject.getString("photoUrl");
                        else
                            officialphotourl = "No Data Provided";
                        boolean channelCheck = officialsJsonObject.has("channels");
                        JSONArray channelsArray=null;
                        if(channelCheck) {
                            channelsArray = officialsJsonObject.getJSONArray("channels");
                        }
                        String officialchannel1G = null;
                        String officialchannel1Gid = null;
                        String officialchannel1F = null;
                        String officialchannel1Fid = null;
                        String officialchannel1T = null;
                        String officialchannel1Tid = null;
                        String officialchannel1Y = null;
                        String officialchannel1Yid = null;
                        if(channelsArray!=null) {
                            for (int k = 0; k < channelsArray.length(); k++) {
                                String channelType = channelsArray.getJSONObject(k).getString("type");
                                String channelID = channelsArray.getJSONObject(k).getString("id");

                                if (channelType.equals("GooglePlus")) {
                                    officialchannel1G = channelType;
                                    officialchannel1Gid = channelID;
                                }
                                if (channelType.equals("Facebook")) {
                                    officialchannel1F = channelType;
                                    officialchannel1Fid = channelID;
                                }
                                if (channelType.equals("Twitter")) {
                                    officialchannel1T = channelType;
                                    officialchannel1Tid = channelID;
                                }
                                if (channelType.equals("YouTube")) {
                                    officialchannel1Y = channelType;
                                    officialchannel1Yid = channelID;
                                }
                            }
                        }
                        if(officialchannel1G == null)officialchannel1G = "No Data Provided";
                        if(officialchannel1Gid == null)officialchannel1Gid = "No Data Provided";
                        if(officialchannel1F == null)officialchannel1F = "No Data Provided";
                        if(officialchannel1Fid==null)officialchannel1Fid = "No Data Provided";
                        if(officialchannel1T == null)officialchannel1T = "No Data Provided";
                        if(officialchannel1Tid == null)officialchannel1Tid = "No Data Provided";
                        if(officialchannel1Y == null)officialchannel1Y = "No Data Provided";
                        if(officialchannel1Yid == null)officialchannel1Yid = "No Data Provided";
                        /*String officialchannel1G = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(0).has("type"))
                               officialchannel1G = officialsJsonObject.getJSONArray("channels").getJSONObject(0).getString("type");
                        String officialchannel1Gid = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(0).has("id"))
                               officialchannel1Gid = officialsJsonObject.getJSONArray("channels").getJSONObject(0).getString("id");
                        String officialchannel1F = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(1).has("type"))
                               officialchannel1F = officialsJsonObject.getJSONArray("channels").getJSONObject(1).getString("type");
                        String officialchannel1Fid = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(1).has("id"))
                               officialchannel1Fid = officialsJsonObject.getJSONArray("channels").getJSONObject(1).getString("id");
                        String officialchannel1T = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(2).has("type"))
                               officialchannel1T = officialsJsonObject.getJSONArray("channels").getJSONObject(2).getString("type");
                        String officialchannel1Tid = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(2).has("id"))
                               officialchannel1Tid = officialsJsonObject.getJSONArray("channels").getJSONObject(2).getString("id");
                        String officialchannel1Y = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(3).has("type"))
                               officialchannel1Y = officialsJsonObject.getJSONArray("channels").getJSONObject(3).getString("type");
                        String officialchannel1Yid = null; if(officialsJsonObject.has("channels")&&officialsJsonObject.getJSONArray("channels").getJSONObject(3).has("id"))
                               officialchannel1Yid = officialsJsonObject.getJSONArray("channels").getJSONObject(3).getString("id");*/
                        representatives.add(new Representative(officesname,officialname,addressLine1,addressLine2,addressLine3,officialparty,officialphones,
                                officialurl,officialemails,officialphotourl,officialchannel1G,officialchannel1Gid,officialchannel1F,
                                officialchannel1Fid,officialchannel1T,officialchannel1Tid,officialchannel1Y,officialchannel1Yid));

                        //representatives.add(rep);
                       o[1]= representatives;
                        //rep = null;
                    }

                }


            }
            catch(Exception e){
                e.printStackTrace();
            }
            /*catch (OutOfMemoryError e){
                e.printStackTrace();
            }*/

        }
        res.setOfficialList(o);
    }

    @Override
    protected String doInBackground(String... params) {
        zipcode = params[0];
        Uri.Builder buildURL = Uri.parse(civicInfourl).buildUpon();
        buildURL.appendQueryParameter("address",params[0]);
        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {

            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(ir);

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }

       String s = sb.toString();


        return s;

    }
}
