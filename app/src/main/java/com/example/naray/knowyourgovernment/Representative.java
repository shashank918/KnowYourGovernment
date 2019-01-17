package com.example.naray.knowyourgovernment;

import java.io.Serializable;

/**
 * Created by naray on 4/3/2017.
 */

public class Representative implements Serializable {

    private String rep_offices_name;
    private String rep_official_name;
    private String rep_address_line1;
    private String rep_address_line2;
    private String rep_address_line3;
    private String rep_party;
    private String rep_phones;
    private String rep_urls;
    private String rep_emails;
    private String rep_photo_url;
    private String rep_googleplus_type;
    private String rep_googleplus_id;
    private String rep_facebook_type;
    private String rep_facebook_id;
    private String rep_twitter_type;
    private String rep_twitter_id;
    private String rep_youtube_type;
    private String rep_youtube_id;

    public Representative(){
        this.rep_official_name = "Representative Name";
        this.rep_offices_name = "Representative Designation";
        this.rep_party = "Representative Party";
    }

    public Representative(String officesname,String officialname,String addressline1,String addressline2,String addressline3,
                          String officialparty,String officialphones,String officialurl,String officialemails,String officialphotourl,
                          String officialchannel1G,String officialchannel1Gid,String officialchannel1F,String officialchannel1Fid,
                          String officialchannel1T,String officialchannel1Tid,String officialchannel1Y,String officialchannel1Yid){
        this.rep_offices_name = officesname;
        this.rep_official_name = officialname;
        this.rep_address_line1 = addressline1;
        this.rep_address_line2 = addressline2;
        this.rep_address_line3 = addressline3;
        this.rep_party = officialparty;
        this.rep_phones = officialphones;
        this.rep_urls = officialurl;
        this.rep_emails = officialemails;
        this.rep_photo_url = officialphotourl;
        this.rep_googleplus_type = officialchannel1G;
        this.rep_googleplus_id = officialchannel1Gid;
        this.rep_facebook_type = officialchannel1F;
        this.rep_facebook_id = officialchannel1Fid;
        this.rep_twitter_type = officialchannel1T;
        this.rep_twitter_id = officialchannel1Tid;
        this.rep_youtube_type = officialchannel1Y;
        this.rep_youtube_id = officialchannel1Yid;
    }

    public String getrepofficesName() {
        return rep_offices_name;
    }

    public String getrepofficialName() {
        return rep_official_name;
    }

    public String getrepaddressLine1() { return rep_address_line1; }

    public String getrepaddressLine2() { return rep_address_line2; }

    public String getrepaddressLine3() { return rep_address_line3; }

    public String getrepParty() {
        return rep_party;
    }

    public String getrepPhones(){ return rep_phones;}

    public String getrepUrls(){ return rep_urls; }

    public String getrepEmails(){ return rep_emails; }

    public String getrepphotoUrl(){ return rep_photo_url; }

    public String getgoogleplusType(){ return rep_googleplus_type; }

    public String getgoogleplusID(){ return rep_googleplus_id; }

    public String getrepfacebookType(){ return rep_facebook_type; }

    public String getrepfacebookID(){ return rep_facebook_id;}

    public String getreptwitterType(){ return rep_twitter_type; }

    public String getreptwitterID(){ return rep_twitter_id; }

    public String getrepyoutubeType(){ return rep_youtube_type; }

    public String getrepyoutubeID(){ return rep_youtube_id; }

    @Override
    public String toString(){
          return rep_offices_name + rep_official_name + rep_address_line1 + rep_address_line2 + rep_address_line3 + rep_party +
                 rep_phones + rep_urls + rep_emails + rep_photo_url + rep_googleplus_type + rep_googleplus_id + rep_facebook_type +
                 rep_facebook_id + rep_twitter_type + rep_twitter_id + rep_youtube_type + rep_youtube_id;
    }

}
