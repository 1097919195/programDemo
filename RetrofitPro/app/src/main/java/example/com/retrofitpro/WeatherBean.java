package example.com.retrofitpro;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/12.
 */
public class WeatherBean {
    public List<Result> results;

    public class Result{
        public Location location;
        public Now now;
        public String last_update;
    }

    public class Location{
        public String id;
        public String name;
        public String country;
        public String path;
        public String timezone;
        public String timezone_offset;
    }

    public class Now{
        public String text;
        public int code;
        public float temperature;
    }
}

