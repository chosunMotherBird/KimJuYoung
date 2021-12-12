package com.example.project.mainMap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import com.example.project.Charger.ChargerDTO;
import com.example.project.R;
import com.example.project.logIn.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private Button login_btn;
    private JSONArray jsonArray;
    private ArrayList<ChargerDTO> chargerList;
    private ArrayList<MapPOIItem> markerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        jsonArray=new JSONArray();

        /**
         * 서버에서 받아올 charger entitiy 들을 담아낼 ArrayList
         */
        chargerList=new ArrayList<>();

        /**
         * maker의 정보들을 담아낼 List
         */
        markerList=new ArrayList<>();

        getAppKeyHash();

        /**
         *  server url을 안드로이드에뮬레이터 에서는 10.0.0.2 가 로컬호스트임, 나중에 서버올리면 바꿀예정
         *  아래는 비동기처리를 위한 AsyncTask를 상속받은 NetworkTask entity 생성 및
         *  execute를 사용해 AsyncTask 를 실행시켜 url 에서 chargers 의 정보를 받아옴.
         *
         *  카카오맵은 에뮬레이터로 안되는 관계로 cmd에서 ipconfig로 현재 ipv4 확인
         *  스마트폰에서 localhost에 접속하려면 ipv4 를 확인해서 들어가야함.
         *  정상적으로 작동
         */
        String url="http://192.168.0.2:8088/chargers/all";
        NetworkTask NetworkTask=new NetworkTask(url,null);
        NetworkTask.execute();




        /**
         * login 버튼을 누르면 인텐트 전환 할건데
         * 나중에 보기 좋게 코드 바꿀예정
         */
        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
    }


    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    public void initMapView()
    {
        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
    }

    /**
     *
     * @param jsonArray
     * onPostExecute 에서 doInbackgroung 로부터 받은 json 을
     * chargerList에 넣는 메소드
     */
    public void makeChargerList(JSONArray jsonArray){
        try
        {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Long id = Long.parseLong(jsonObject.getString("id"));
                String chargerName = jsonObject.getString("chargerName");
                String chargerLocation = jsonObject.getString("chargerLocation");
                String city = jsonObject.getString("city");
                String closedDates = jsonObject.getString("closedDates");
                String fastChargeType = jsonObject.getString("fastChargeType");
                Integer slowNum = Integer.parseInt(jsonObject.getString("slowNum"));
                Integer fastNum = Integer.parseInt(jsonObject.getString("fastNum"));
                String parkingFee = jsonObject.getString("parkingFee");
                Double lat = Double.parseDouble(jsonObject.getString("lat"));
                Double lon = Double.parseDouble(jsonObject.getString("lon"));
                String address = jsonObject.getString("address");
                chargerList.add(new ChargerDTO(id, chargerName, chargerLocation, city, closedDates, fastChargeType, slowNum, fastNum, parkingFee, lat, lon, address));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setMarkerOnMap(){
        for(ChargerDTO chargerDTO : chargerList)
        {

            /**
             * 마커위치 조심해야함
             * MapPoint.mapPointWithCONGCoord() 와 mapPointWithGeocoord()를 바꿔서 계속 마커가 안떴음.
             * lat, long 을 쓰려면 후자를 
             */
            MapPOIItem marker = new MapPOIItem();
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(chargerDTO.getLat(), chargerDTO.getLon()));
            marker.setItemName(chargerDTO.getChargerName());
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            markerList.add(marker);
        }
        mapView.addPOIItems(markerList.toArray(new MapPOIItem[markerList.size()]));
    }


    public class NetworkTask extends AsyncTask<Void,Void,String>
    {
        /**
         * 비동기 처리를 위한 NetworkTask 클래스
         * 보면 AsyncTask<Params,Progress,Result>3개의 인자가 들어가는 걸 볼 수 있는데,
         * Params는 doInBackground 의 파라미터 타입이 되며, execute 메소드 인자값이 됨.
         * Progress는 doInBackground 작업 시 진행 단위 타입, onProgressUpdate의 파라미터 타입
         * Result는 doInBackgourn 리턴 값으로 onPostExecute의 파라미터 값
         *
         * 그 외 자세한 설명은 https://itmining.tistory.com/7
         */
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values)
        {
            this.url=url; this.values=values;
        }

        @Override
        protected void onPreExecute() {
            /**
             * 카카오 맵 불러오기
             * 실행전 준비단계
             */
            super.onPreExecute();
            initMapView();

        }

        /**
         *
         * @param params
         * @return 서버에서 받아온 result 값, 여기서는 charger
         * doInBackground 에서는 서버에 있는 정보를 받아옴.
         */
        @Override

        protected String doInBackground(Void... params)
        {
            /**
             * url에 접속해서 charger 위치를 가져오는 과정
             * String result는 모든 Json들임
             */
            String result;
            RequestHttpConnection requestHttpConnection=new RequestHttpConnection();
            result = requestHttpConnection.request(url,values);
            return result;
        }

        /**
         *
         * @param s
         * s는 doInBackground 에서 리턴한 result 값임. 여기선 Json String이라 봐도 무방
         * JsonArray는 서버에서 받아온 JsonArray는 s 속에 json들을 jsonArray 화 시켜주는 것 같음
         * 길이만큼 불러오기 성공, list에도 정상적으로 charger의 정보가 들어가는것을 debug로 확인.
         * 또한 비동기처리로 여기서 marker를 띄워야 할 것 같음.
         */
        @Override
        protected void onPostExecute(String s)
        {
            /**
             * doInbackground에서 return 한 String을 받아서 chargerlist를 만들고
             * marker를 띄움
             */
            super.onPostExecute(s);
            try
            {
                jsonArray= new JSONArray(s);
                /**
                 * jsonArray에 있는 정보로 chargerList 생성
                 */
                makeChargerList(jsonArray);
                /**
                 * 지도에 charger들 마커띄움.
                 * 지금 마커가 16개 밖에 안보이는데 똑같은 위치에 2개가 있음.
                 */
                setMarkerOnMap();

                /**
                 * 광주위도경도로 처음위치 설정
                 * 광주광역시청 위도경도
                 */
                MapPOIItem marker = new MapPOIItem();
                MapPoint mapPoint= MapPoint.mapPointWithGeoCoord(35.1595454,126.8526012);
                marker.setMapPoint(mapPoint);
                mapView.setMapCenterPoint(mapPoint, true);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
