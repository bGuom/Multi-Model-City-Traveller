package com.app.smartcitytraveler.DirectionDataAPI;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetAsyncDirectionsData2 extends AsyncTask<Object,String,String> {


    String URL;
    String GoogleDirectionData;
    AsyncResponseHandleable Parent = null;

    //public GetAsyncDirectionsData(AsyncResponseHandleable parent){
    //    Parent = parent;
    //}

    @Override
    protected String doInBackground(Object... objects) {

        URL = (String)objects[0];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            GoogleDirectionData = downloadUrl.readUrl(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return GoogleDirectionData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        HashMap<String,String> TempHash;
        ArrayList<String> TempPath;
        JsonDataParser jsonDataParser = new JsonDataParser();
        TempHash = jsonDataParser.getDirectionData(s);
        TempPath = jsonDataParser.getPathsArray(s);
        Parent.processFinish2(TempHash,TempPath);

    }





}
