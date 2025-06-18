package com.example.zonghe;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.List;


public class WeatherFragment extends Fragment {
    EditText etcity;
    Button btnquery;
    ImageView imageView1,imageView2,imageView3;
    TextView tvweather,tvtemp,textView,tvwea1,tvwea2,tvwea3,tvtemp1,tvtemp2,tvtemp3,tvdate1,tvdate2,tvdate3;
    String cityid,city,strweather,strtemp;
    LinearLayout re;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_weather, container, false);
        View view = View.inflate(getActivity(),R.layout.activity_weather,null);

        textView = (TextView) view.findViewById(R.id.textView);
        etcity = (EditText) view.findViewById(R.id.etcity);
        btnquery = (Button) view.findViewById(R.id.btnquery);
        tvweather = (TextView) view.findViewById(R.id.tvweather);
        tvwea1 = (TextView)view.findViewById(R.id.tvwea1);
        tvwea2 = (TextView)view.findViewById(R.id.tvwea2);
        tvwea3 = (TextView)view.findViewById(R.id.tvwea3);
        tvtemp1 = (TextView)view.findViewById(R.id.tvtemp1);
        tvtemp2 = (TextView)view.findViewById(R.id.tvtemp2);
        tvtemp3 = (TextView)view.findViewById(R.id.tvtemp3);
        tvtemp = (TextView) view.findViewById(R.id.tvtemp);
        imageView1 = (ImageView)view.findViewById(R.id.imageView1);
        imageView2 = (ImageView)view.findViewById(R.id.imageView2);
        imageView3 = (ImageView)view.findViewById(R.id.imageView3);
        tvdate1 = (TextView)view.findViewById(R.id.tvdate1);
        tvdate2 = (TextView)view.findViewById(R.id.tvdate2);
        tvdate3 = (TextView)view.findViewById(R.id.tvdate3);
        re = (LinearLayout) view.findViewById(R.id.research) ;
        HeConfig.init("HE2204191003291095","727278c17901404cb75dd15500758778");
        HeConfig.switchToDevService();

        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("123");
                city = etcity.getText().toString().trim();
                Context context =getActivity();
                System.out.println(context);
                QWeather.getGeoCityLookup(context, city, new QWeather.OnResultGeoListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("获取失败");

                    }

                    @Override
                    public void onSuccess(GeoBean geoBean) {
                        cityid = geoBean.getLocationBean().get(0).getId();
                        System.out.println(cityid);
                        System.out.println("111");

                    }
                });
                QWeather.getWeatherNow(context, cityid, new QWeather.OnResultWeatherNowListener() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                        strweather = now.getText();
                        strtemp = now.getTemp();
                        tvweather.setText(strweather);
                        tvtemp.setText(strtemp+"℃");
                        System.out.println("成功");
//                        if(now.getIcon().equals("100"))
//                            imageView.setImageResource(R.drawable.sun);
//                        else if(now.getIcon().equals("101"))
//                            imageView.setImageResource(R.drawable.cloudy);




                    }
                });
                QWeather.getWeather3D(context, cityid, new QWeather.OnResultWeatherDailyListener() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(WeatherDailyBean weatherDailyBean) {
                        List<WeatherDailyBean.DailyBean> threeday = weatherDailyBean.getDaily();
                        if(threeday.get(0).getIconDay().equals("100")){

                        }
                        //日期
                        tvdate1.setText(threeday.get(0).getFxDate().toString().substring(5));
                        tvdate2.setText(threeday.get(1).getFxDate().toString().substring(5));
                        tvdate3.setText(threeday.get(2).getFxDate().toString().substring(5));

                        //当天温度区间
                        textView.setText("最低温度"+threeday.get(0).getTempMin().toString()+"℃,最高温度"+threeday.get(0).getTempMax().toString()+"℃");
                        //三天天气
                        tvwea1.setText(threeday.get(0).getTextDay().toString());
                        tvwea2.setText(threeday.get(1).getTextDay().toString());
                        tvwea3.setText(threeday.get(2).getTextDay().toString());
                        //三天温度区间
                        tvtemp1.setText(threeday.get(0).getTempMin().toString()+"℃~"+threeday.get(0).getTempMax().toString()+"℃");
                        tvtemp2.setText(threeday.get(1).getTempMin().toString()+"℃~"+threeday.get(1).getTempMax().toString()+"℃");
                        tvtemp3.setText(threeday.get(2).getTempMin().toString()+"℃~"+threeday.get(2).getTempMax().toString()+"℃");
                        //天气图标
                        imageView1.setImageResource(setimg(threeday, 0));
                        imageView2.setImageResource(setimg(threeday, 1));
                        imageView3.setImageResource(setimg(threeday, 2));

                        setback(threeday);

                        //re.setBackgroundResource(R.drawable.wea);


                    }
                });
//                setimg(imageView1,0);
//                setimg(imageView2,1);
//                setimg(imageView3,2);

            }
        });
        return view;
    }

    //设置天气图标
    public int setimg(List<WeatherDailyBean.DailyBean> threeday, int i) {
        if (threeday.get(i).getIconDay().equals("100")) {
            re.setBackgroundResource(R.drawable.sunnyback);
            return R.drawable.sun;

        } else if (threeday.get(i).getIconDay().equals("101")) {
            re.setBackgroundResource(R.drawable.cloudyback);
            return R.drawable.cloudy;
        }
        //return R.drawable.sun;
        else if(threeday.get(i).getIconDay().equals("307")){
            re.setBackgroundResource(R.drawable.rainback);
            return R.drawable.heavy_rain;
        }
        else if(threeday.get(i).getIconDay().equals("305")){
            re.setBackgroundResource(R.drawable.rainback);
            return R.drawable.light_rain;
        }
        else if(threeday.get(i).getIconDay().equals("306")){
            re.setBackgroundResource(R.drawable.rainback);
            return R.drawable.moderate_rain;
        }
        else if(threeday.get(i).getIconDay().equals("104")){
            re.setBackgroundResource(R.drawable.overcastback);
            return R.drawable.overcast;
        }
        else if(threeday.get(i).getIconDay().equals("302")){
            re.setBackgroundResource(R.drawable.rainback);
            return R.drawable.thundershower;
        }
        return R.drawable.sun;
    }
    //设置变化背景
    public void setback(List<WeatherDailyBean.DailyBean> threeday){
        if (threeday.get(0).getIconDay().equals("100")) {
            re.setBackgroundResource(R.drawable.sunnyback1);


        } else if (threeday.get(0).getIconDay().equals("101")) {
            re.setBackgroundResource(R.drawable.cloudyback);

        }
        //return R.drawable.sun;
        else if(threeday.get(0).getIconDay().equals("307")){
            re.setBackgroundResource(R.drawable.rainback);

        }
        else if(threeday.get(0).getIconDay().equals("305")){
            re.setBackgroundResource(R.drawable.rainback);

        }
        else if(threeday.get(0).getIconDay().equals("306")){
            re.setBackgroundResource(R.drawable.rainback);

        }
        else if(threeday.get(0).getIconDay().equals("104")){
            re.setBackgroundResource(R.drawable.overcastback);

        }
        else if(threeday.get(0).getIconDay().equals("302")) {
            re.setBackgroundResource(R.drawable.rainback);
        }
    }

}