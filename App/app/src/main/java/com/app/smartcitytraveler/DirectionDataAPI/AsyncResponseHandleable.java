package com.app.smartcitytraveler.DirectionDataAPI;

import java.util.ArrayList;
import java.util.HashMap;

public interface AsyncResponseHandleable {
    void processFinish(HashMap<String,String> directionDataHash, ArrayList<String> pollyPaths);
    void processFinish2(HashMap<String,String> directionDataHash, ArrayList<String> pollyPaths);
    void processFinish3(HashMap<String,String> directionDataHash, ArrayList<String> pollyPaths);
}
