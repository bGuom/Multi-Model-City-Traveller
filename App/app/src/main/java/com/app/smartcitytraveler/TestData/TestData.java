package com.app.smartcitytraveler.TestData;

import com.app.smartcitytraveler.MediumData.BusData;
import com.app.smartcitytraveler.MediumData.Controllers.BusDataController;
import com.app.smartcitytraveler.MediumData.TrainData;
import com.app.smartcitytraveler.MediumData.Controllers.TrainDataController;
import com.app.smartcitytraveler.Stations.BusHalt;
import com.app.smartcitytraveler.Stations.TaxiPark;
import com.app.smartcitytraveler.Stations.TrainStation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class TestData {

    public float getWalkSpeed(){
        return Float.parseFloat("1.4");
    }

    public float[] getTaxiFare(){
        float[] arr = new float[2];
        arr[0]=60;
        arr[1]=35;
        return arr;
    }

    public Float getMaxWalkDistance(){
        return Float.parseFloat("500");
    }

    public ArrayList<Integer> getBusFareArray(){
        ArrayList<Integer> busFareArray = new ArrayList<Integer>();
        busFareArray.add(0);
        busFareArray.add(12);
        busFareArray.add(14);
        busFareArray.add(19);
        busFareArray.add(23);
        busFareArray.add(28);
        busFareArray.add(32);
        busFareArray.add(37);
        busFareArray.add(39);
        busFareArray.add(42);
        busFareArray.add(44);
        busFareArray.add(47);
        busFareArray.add(50);
        busFareArray.add(52);
        busFareArray.add(55);
        busFareArray.add(58);
        busFareArray.add(60);
        busFareArray.add(63);
        busFareArray.add(65);
        busFareArray.add(68);
        busFareArray.add(71);


        return busFareArray;
    }

    //==========================================================
    public TrainDataController getTestTrainData(){

        return new TrainDataController(getTestStationArray(),getTestUpTrainDataArray(),getTestDownTrainDataArray());
    }

    private ArrayList<TrainStation> getTestStationArray(){

        ArrayList<TrainStation> TestTrainStations = new ArrayList<TrainStation>();

        TestTrainStations.add(new TrainStation(1,"Alawwa Railway Station",new LatLng(7.293448,80.238618)));
        TestTrainStations.add(new TrainStation(2,"Walakumbura Railway Station",new LatLng(7.314354,80.269224)));
        TestTrainStations.add(new TrainStation(3,"Polgahawela Railway Station",new LatLng(7.33106,80.300949)));
        TestTrainStations.add(new TrainStation(4,"Girabe Railway Station",new LatLng(7.364413,80.302435)));
        TestTrainStations.add(new TrainStation(5,"Thalawttegedara Railway Station",new LatLng(7.387958,80.301427)));
        TestTrainStations.add(new TrainStation(6,"Pothuhara Railway Station",new LatLng(7.419863,80.327429)));
        TestTrainStations.add(new TrainStation(7,"Nyliya Railway Station",new LatLng(7.449979,80.35851)));
        TestTrainStations.add(new TrainStation(8,"Kurunegala Railway Station",new LatLng(7.476793,80.374073)));

        return TestTrainStations;
    }

    private ArrayList<TrainData> getTestUpTrainDataArray(){

        ArrayList<TrainData> TestTrainData = new ArrayList<TrainData>();

        TestTrainData.add(new TrainData(1,"1109","1,2,3","5:21,5:26,5:31","0"));
        TestTrainData.add(new TrainData(2,"1125","1,2,3","6:31,6:36,6:41","0"));
        TestTrainData.add(new TrainData(3,"1127","1,2,3","7:59,8:04,8:09","0"));
        TestTrainData.add(new TrainData(4,"1135","1,2,3","8:58,9:03,9:08","0"));
        TestTrainData.add(new TrainData(5,"1136","1,2,3","9:57,10:02,10:07","0"));
        TestTrainData.add(new TrainData(6,"1141","1,2,3","10:57,11:00,11:05","0"));
        TestTrainData.add(new TrainData(7,"1143","1,2,3","12:07,12:12,12:17","0"));
        TestTrainData.add(new TrainData(8,"1144","1,2,3","13:32,13:37,13:41","0"));
        TestTrainData.add(new TrainData(9,"1147","1,2,3","14:46,14:51,14:56","0"));
        TestTrainData.add(new TrainData(10,"1151","1,2,3","15:25,15:30,15:35","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(11,"1151A","1,2,3","15:25,15:30,15:35","0"));
        TestTrainData.add(new TrainData(12,"1164A","1,2","14:50,14:59","7"));
        TestTrainData.add(new TrainData(13,"1152","1,2,3","15:57,16:02,16:08","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(14,"1162","1,2,3","16:51,16:56,17:01","0"));
        TestTrainData.add(new TrainData(15,"1170","1,2,3","17:38,17:43,17:48","0"));
        TestTrainData.add(new TrainData(16,"1172","1,2,3","18:40,18:47,18:53","0"));
        TestTrainData.add(new TrainData(17,"1172A","1,2,3","19:11,19:17,19:23","7"));
        TestTrainData.add(new TrainData(18,"1164","1,3","18:04,18:12","0"));
        TestTrainData.add(new TrainData(19,"1168","1,2,3","18:49,18:54,18:59","1,2"));
        TestTrainData.add(new TrainData(20,"1168A","1,2,3","18:56,19:01,19:06","5"));
        TestTrainData.add(new TrainData(21,"1168B","1,2,3","18:47,18:52,18:57","6"));
        TestTrainData.add(new TrainData(22,"1173","1,2,3","19:09,19:14,19:19","0"));
        TestTrainData.add(new TrainData(23,"1175A","1,2,3","19:44,19:49,19:54","6,7"));
        TestTrainData.add(new TrainData(24,"1176","1,2,3","19:26,19:31,19:36","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(25,"1183","1,2,3","20:19,20:24,20:29","7"));
        TestTrainData.add(new TrainData(26,"1183A","1,2,3","20:29,20:34,20:35","0"));
        TestTrainData.add(new TrainData(27,"1191A","1,2,3","21:58,22:03,22:08","0"));
        TestTrainData.add(new TrainData(28,"1132","1,2,3","22:36,22:41,22:46","0"));
        TestTrainData.add(new TrainData(29,"1194","1,2,3","0:51,0:56,1:01","0"));
        TestTrainData.add(new TrainData(30,"4021","3,6,8","6:59,7:16,7:25","0"));
        TestTrainData.add(new TrainData(31,"4077","3,6,8","7:57,8:16,8:27","0"));
        TestTrainData.add(new TrainData(32,"4017","3,6,8","13:01,13:19,13:29","0"));
        TestTrainData.add(new TrainData(33,"4093","3,6,8","10:59,11:26,11:45","0"));
        TestTrainData.add(new TrainData(34,"4085","1,3,4,5,6,7,8","15:01,15:12,15:24,15:28,15:39,15:47,15:55","0"));
        TestTrainData.add(new TrainData(35,"4003","3,6,8","17:05,17:23,17:36","0"));
        TestTrainData.add(new TrainData(36,"4468","1,3,4,5,6,7,8","18:03,18:10,18:18,18:21,18:30,18:41,18:47","1,2,3,4,5"));
        TestTrainData.add(new TrainData(37,"4480","3,4,5,6,7","18:10,18:17,18:20,18:29,18:40,18:47","6,7"));
        TestTrainData.add(new TrainData(38,"4468","1,3,4,5,6,7,8","18:27,18:36,18:56,18:59,19:08,19:15,19:22","7"));
        TestTrainData.add(new TrainData(39,"4469","1,2,3,4,5,6,7,8","18:34,18:39,18:44,18:55,18:59,19:09,19:15,19:22","1,2,3,4,5"));
        TestTrainData.add(new TrainData(40,"4470","1,2,3","19:01,19:27,19:23","0"));
        TestTrainData.add(new TrainData(41,"4497","3,6,8","21:30,21:39,22:54","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(42,"4089","3,6,8","21:50,21:59,23:14","0"));

        return  TestTrainData;
    }

    private ArrayList<TrainData> getTestDownTrainDataArray(){

        ArrayList<TrainData> TestTrainData = new ArrayList<TrainData>();

        TestTrainData.add(new TrainData(43,"4086","8,7,6,5,3,2,1","7:49,8:04,8:12,8:28,8:32,8:41,8:54","0"));
        TestTrainData.add(new TrainData(44,"4856","8,7,6,5,4,3","4:16,4:26,4:33,4:43,4:46,4:55","7"));
        TestTrainData.add(new TrainData(45,"4857","8,6,5,3,1","5:36,5:50,6:00,6:12,6:20","1,2,3,4,5"));
        TestTrainData.add(new TrainData(46,"4859","8,7,6,5,4,3","6:20,6:26,6:33,6:46,6:49,6:57","0"));
        TestTrainData.add(new TrainData(47,"4018","8,3","11:10,,11:46","0"));
        TestTrainData.add(new TrainData(48,"4078","8,6,3","16:27,16:40,16:58","0"));
        TestTrainData.add(new TrainData(49,"4094","8,6,3","14:29,14:43,14:50","7"));
        TestTrainData.add(new TrainData(50,"4090","8,3","1:45,,2:19","0"));
        TestTrainData.add(new TrainData(51,"4858","8,6,5,4,3","15:23,15:36,15:52,15:55,16:05","0"));
        TestTrainData.add(new TrainData(52,"1507","3,2,1","5:30,3:35,3:39","0"));
        TestTrainData.add(new TrainData(53,"1512","3,2,1","3:53,3:59,4:04","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(54,"1512A","3,2,1","3:53,3:59,4:04","7"));
        TestTrainData.add(new TrainData(55,"1518","3,2,1","4:23,4:29,4:34","1,2,3,4,5"));
        TestTrainData.add(new TrainData(56,"1518A","3,2,1","4:25,4:29,4:34","7"));
        TestTrainData.add(new TrainData(57,"1527","3,2,1","5:08,5:14,5:19","6"));
        TestTrainData.add(new TrainData(58,"1527 B","3,2,1","5:08,5:14,5:19","7"));
        TestTrainData.add(new TrainData(59,"1528","3,1","5:25,5:32","1,2,3,4,5"));
        TestTrainData.add(new TrainData(60,"1531","3,2","6:15,6:23","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(61,"1535A","3,2,1","5:30,6:24,6:29","1,2,3,4,5"));
        TestTrainData.add(new TrainData(62,"1526A","3,2,1","6:20,6:24,6:29","6,7"));
        TestTrainData.add(new TrainData(63,"1526","3,2,1","6:20,6:24,6:26","1,2,3,4,5"));
        TestTrainData.add(new TrainData(64,"1537","3,2,1","5:48,5:54,5:59","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(65,"1537A","3,2,1","5:48,5:53,5:58","0"));
        TestTrainData.add(new TrainData(66,"1542","3,2,1","6:25,6:29,6:34","1,2,3,4,5,6"));
        TestTrainData.add(new TrainData(67,"1542A","3,2,1","6:29,6:29,6:34","7"));
        TestTrainData.add(new TrainData(68,"1536","3,2,1","7:43,7:48,7:53","0"));
        TestTrainData.add(new TrainData(69,"1549","3,2,1","8:50,8:54,8:59","0"));
        TestTrainData.add(new TrainData(70,"1550","3,2,1","10:18,10:24,10:29","0"));
        TestTrainData.add(new TrainData(71,"1551","3,2,1","11:18,11:24,11:29","0"));
        TestTrainData.add(new TrainData(72,"1552","3,2,1","13:03,13:09,13:14","0"));
        TestTrainData.add(new TrainData(73,"1565","3,2,1","13:38,13:49,13:54","0"));
        TestTrainData.add(new TrainData(74,"1566","3,2,1","14:33,14:42,14:47","0"));
        TestTrainData.add(new TrainData(75,"1568","3,2,1","15:10,15:14,15:19","0"));
        TestTrainData.add(new TrainData(76,"1581","3,2,1","16:27,16:32,16:37","0"));
        TestTrainData.add(new TrainData(77,"1576","3,2,1","18:51,19:35,19:41","0"));
        TestTrainData.add(new TrainData(78,"1570","3,2,1","18:10,18:14,18:19","1,2,3,4,5"));
        TestTrainData.add(new TrainData(79,"1578","3,2,1","17:20,17:29,17:34","0"));



        return  TestTrainData;
    }
    //============================================================



    //=======================================================
    public ArrayList<TaxiPark> getTestTaxiParksArray(){

        ArrayList<TaxiPark> TestTaxiParks = new ArrayList<TaxiPark>();

        TestTaxiParks.add(new TaxiPark(1,new LatLng(7.293646,80.239699)));
        TestTaxiParks.add(new TaxiPark(2,new LatLng(7.331176,80.295486)));
        TestTaxiParks.add(new TaxiPark(3,new LatLng(7.335096,80.299911)));
        TestTaxiParks.add(new TaxiPark(4,new LatLng(7.330767,80.302178)));
        TestTaxiParks.add(new TaxiPark(5,new LatLng(7.337845,80.302597)));
        TestTaxiParks.add(new TaxiPark(6,new LatLng(7.343063,80.303438)));
        TestTaxiParks.add(new TaxiPark(7,new LatLng(7.347741,80.304574)));
        TestTaxiParks.add(new TaxiPark(8,new LatLng(7.354363,80.306332)));
        TestTaxiParks.add(new TaxiPark(9,new LatLng(7.395505,80.321786)));
        TestTaxiParks.add(new TaxiPark(10,new LatLng(7.403113,80.324427)));
        TestTaxiParks.add(new TaxiPark(11,new LatLng(7.419933,80.328038)));
        TestTaxiParks.add(new TaxiPark(12,new LatLng(7.427299,80.331605)));
        TestTaxiParks.add(new TaxiPark(13,new LatLng(7.437511,80.338604)));
        TestTaxiParks.add(new TaxiPark(14,new LatLng(7.445981,80.342711)));
        TestTaxiParks.add(new TaxiPark(15,new LatLng(7.458345,80.345965)));
        TestTaxiParks.add(new TaxiPark(16,new LatLng(7.473292,80.351888)));
        TestTaxiParks.add(new TaxiPark(17,new LatLng(7.476923,80.356058)));
        TestTaxiParks.add(new TaxiPark(18,new LatLng(7.478697,80.357604)));
        TestTaxiParks.add(new TaxiPark(19,new LatLng(7.479184,80.358061)));
        TestTaxiParks.add(new TaxiPark(20,new LatLng(7.484019,80.362766)));
        TestTaxiParks.add(new TaxiPark(21,new LatLng(7.485262,80.363848)));
        TestTaxiParks.add(new TaxiPark(22,new LatLng(7.478149,80.374133)));
        TestTaxiParks.add(new TaxiPark(23,new LatLng(7.476593,80.374096)));


        return TestTaxiParks;
    }
    //=======================================================



    //==========================================================
    public ArrayList<BusHalt> getTestBusHaltArray(){

        ArrayList<BusHalt> TempBusHalts = new ArrayList<BusHalt>();

        TempBusHalts.add(new BusHalt(1,1,"Alawwa",new LatLng(7.294175,80.240905),"6","2055"));
        TempBusHalts.add(new BusHalt(2,2,"Kapuwarala",new LatLng(7.305522,80.255257),"6","1598"));
        TempBusHalts.add(new BusHalt(3,3,"walakumbura",new LatLng(7.313018,80.267165),"6","1388"));
        TempBusHalts.add(new BusHalt(4,4,"Morawalapitiya",new LatLng(7.318554,80.278312),"6","3312"));
        TempBusHalts.add(new BusHalt(5,5,"polgahawela",new LatLng(7.336452,80.300766),"6","9548"));
        TempBusHalts.add(new BusHalt(6,10,"pothuhara",new LatLng(7.416432,80.327386),"6","7077"));
        TempBusHalts.add(new BusHalt(7,13,"Wahara Junction",new LatLng(7.47326,80.352127),"6","1058"));
        TempBusHalts.add(new BusHalt(8,14,"Hospital",new LatLng(7.479951,80.35873),"6","1121"));
        TempBusHalts.add(new BusHalt(9,15,"kurunegala",new LatLng(7.486822,80.364891),"6","0"));

        TempBusHalts.add(new BusHalt(1,1,"Alawwa",new LatLng(7.294175,80.240905),"513/1","4055"));
        TempBusHalts.add(new BusHalt(10,2,"Pambadeniya",new LatLng(7.32471,80.230456),"513/1","2640"));
        TempBusHalts.add(new BusHalt(11,3,"Mharachchimulla",new LatLng(7.341889,80.219951),"513/1","10178"));
        TempBusHalts.add(new BusHalt(12,4,"Nakalagamuwa",new LatLng(7.400822,80.20924),"513/1","3881"));
        TempBusHalts.add(new BusHalt(13,5,"Dampalassa",new LatLng(7.412567,80.210442),"513/1","4311"));
        TempBusHalts.add(new BusHalt(14,6,"Narammala",new LatLng(7.432653,80.215318),"513/1","0"));

        TempBusHalts.add(new BusHalt(5,1,"polgahawela",new LatLng(7.336452,80.300766),"506/1","2881"));
        TempBusHalts.add(new BusHalt(15,2,"Bandawa",new LatLng(7.3607,80.307087),"506/1","2526"));
        TempBusHalts.add(new BusHalt(16,3,"Kahawaththa",new LatLng(7.371687,80.291386),"506/1","43"));
        TempBusHalts.add(new BusHalt(17,4,"Udapola",new LatLng(7.371535,80.291751),"506/1","3929"));
        TempBusHalts.add(new BusHalt(18,5,"Wadakada",new LatLng(7.397607,80.26873),"506/1","774"));
        TempBusHalts.add(new BusHalt(19,6,"Abagammana",new LatLng(7.403182,80.265039),"506/1","1274"));
        TempBusHalts.add(new BusHalt(20,7,"Bemmullegedara",new LatLng(7.411291,80.256739),"506/1","7396"));
        TempBusHalts.add(new BusHalt(14,8,"Narammala",new LatLng(7.432653,80.215318),"506/1","0"));

        TempBusHalts.add(new BusHalt(9,1,"Kurunegala",new LatLng(7.486822,80.364891),"5","1579"));
        TempBusHalts.add(new BusHalt(21,2,"Puwakgas Junction",new LatLng(7.484459,80.354064),"5","2126"));
        TempBusHalts.add(new BusHalt(22,3,"Malkaduwawa",new LatLng(7.4744,80.342821),"5","5261"));
        TempBusHalts.add(new BusHalt(23,4,"Piduruwella",new LatLng(7.468124,80.302274),"5","1021"));
        TempBusHalts.add(new BusHalt(24,5,"Pansal junstion",new LatLng(7.46292,80.297191),"5","308"));
        TempBusHalts.add(new BusHalt(25,6,"Uhumiya",new LatLng(7.462508,80.294442),"5","1535"));
        TempBusHalts.add(new BusHalt(26,7,"Weerambugedara",new LatLng(7.45513,80.284047),"5","3027"));
        TempBusHalts.add(new BusHalt(27,8,"Kalugamuwa Junction",new LatLng(7.449071,80.257963),"5","2680"));
        TempBusHalts.add(new BusHalt(20,9,"Bemmullegedara",new LatLng(7.439256,80.237566),"5","1539"));
        TempBusHalts.add(new BusHalt(28,10,"Uyanwaththa",new LatLng(7.438325,80.224915),"5","2069"));
        TempBusHalts.add(new BusHalt(14,11,"Narammala",new LatLng(7.432653,80.215318),"5","0"));


        return TempBusHalts;
    }

    private ArrayList<BusData> getTestBusData(){return null;}

    public BusDataController getBusDataController(){

        return (new BusDataController(getTestBusHaltArray(),getTestBusData(),getBusRoutes()));
    }

    public ArrayList<String> getBusRoutes(){
        ArrayList<String> TempArr = new ArrayList<String>();
        TempArr.add("6");
        TempArr.add("513/1");
        TempArr.add("506/1");
        TempArr.add("5");

        return TempArr;
    }

    public ArrayList<BusData> getUpBusData(){

        ArrayList<BusData> TempArray = new ArrayList<BusData>();

        TempArray.add(new BusData(1,"63/4039",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"05:30","05:50"));
        TempArray.add(new BusData(1,"63/4039",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"08:05","08:35"));
        TempArray.add(new BusData(1,"63/4039",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"12:05","12:35"));
        TempArray.add(new BusData(1,"63/4039",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"15:05","15:35"));
        TempArray.add(new BusData(1,"63/4039",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"18:05","18:35"));
        TempArray.add(new BusData(2,"NC-0695",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"06:25","07:05"));
        TempArray.add(new BusData(2,"NC-0695",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"09:35","10:05"));
        TempArray.add(new BusData(2,"NC-0695",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"13:50","14:05"));
        TempArray.add(new BusData(2,"NC-0695",	"Polgahawela"	,"Narammala","506/1",	"CTB",		"16:25","17:25"));


        TempArray.add(new BusData(3,"NB-8718",	"Colombo",	"Kurunegala","6","CTB",	"05:05","05:25"));
        TempArray.add(new BusData(3,"NB-8718",	"Colombo",	"Kurunegala","6","CTB",	"09:20","9:40"));
        TempArray.add(new BusData(3,"NB-8718",	"Colombo",	"Kurunegala","6","CTB",	"16:30","16:50"));
        TempArray.add(new BusData(5,"NB-8359",	"Warakapola",	"Kurunegala","6","CTB",	"06:10","06:20"));
        TempArray.add(new BusData(5,"NB-8359",	"Colombo",	"Kurunegala","6","CTB",	"11:40","12:00"));
        TempArray.add(new BusData(6,"NB-9748",	"Colombo",	"Kurunegala","6","CTB",	"11:20","11:40"));

        TempArray.add(new BusData(7, "GK-3625","Alawwa","Narammala","513/1","CTB","13:45", "14:00"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","04:25","04:30"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","06:15","06:30"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","08:15","08:30"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","10:15","10:30"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","13:05","13:15"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","15:00","15:30"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","17:35","18:00"));
        TempArray.add(new BusData(8, "NB-8684","Alawwa","Narammala","513/1","CTB","20:15","21:00"));

        TempArray.add(new BusData(9, "NB-8650","Colombo","Kurunegala","5","CTB","11:00","11:20"));
        TempArray.add(new BusData(10, "NB-9752","Kurunagala","Narammala","5","CTB","15:30","16:00"));



        return TempArray;
    }

    public ArrayList<BusData> getDownBusData(){

        ArrayList<BusData> TempArray = new ArrayList<BusData>();

        TempArray.add(new BusData(1,"63/4039",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"06:50","07:05"));
        TempArray.add(new BusData(1,"63/4039",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"09:35","10:05"));
        TempArray.add(new BusData(1,"63/4039",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"13:35","14:05"));
        TempArray.add(new BusData(1,"63/4039",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"16:35","17:05"));
        TempArray.add(new BusData(2,"NC-0695",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"08:05","08:35"));
        TempArray.add(new BusData(2,"NC-0695",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"11:05","11:35"));
        TempArray.add(new BusData(2,"NC-0695",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"15:05","15:25"));
        TempArray.add(new BusData(2,"NC-0695",	"Narammala"	,"Polgahawela","506/1",	"CTB",		"18:05","18:35"));

        TempArray.add(new BusData(3,"NB-8718",	"Kurunegala",	"Colombo","6","CTB",	"06:00","06:20"));
        TempArray.add(new BusData(3,"NB-8718",	"Kurunegala",	"Colombo","6","CTB",	"13:00","13:20"));
        TempArray.add(new BusData(3,"NB-8718",	"Kurunegala",	"Colombo","6","CTB",	"20:30","20:45"));
        TempArray.add(new BusData(4,"NB-8650",	"Kurunegala",	"Alawwa","6","",	"14:45","15:00"));
        TempArray.add(new BusData(5,"NB-8359",	"Kurunegala",	"Colombo","6","CTB",	"08:10","08:20"));
        TempArray.add(new BusData(5,"NB-8359",	"Kurunegala",	"Colombo","6","CTB",	"15:30","16:15"));
        TempArray.add(new BusData(6,"NB-9748",	"Kurunegala",	"Colombo","6","CTB",	"07:00","07:30"));
        TempArray.add(new BusData(6,"NB-9748",	"Kurunegala",	"Colombo","6","CTB",	"15:25","15:45"));

        TempArray.add(new BusData(7, "GK-3625","Narammala","Alawwa","513/1","CTB","1:35",""));
        TempArray.add(new BusData(7, "GK-3625","Narammala","Alawwa","513/1","CTB","15:30","16:00"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","05:20","05:25"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","07:00","07:15"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","09:15","09:30"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","11:15","11:30"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","14:00","14:15"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","16:20","16:45"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","18:45","19:30"));
        TempArray.add(new BusData(8, "NB-8684","Narammala","Alawwa","513/1","CTB","21:45","22:00"));

        TempArray.add(new BusData(9, "NB-8650","Kurunegala","Colombo","5","CTB","07:20","07:40"));
        TempArray.add(new BusData(10, "NB-9752","Narammala","Kurunagala","5","CTB","06:30","07:10"));


        return TempArray;
    }



    //==========================================================


}
