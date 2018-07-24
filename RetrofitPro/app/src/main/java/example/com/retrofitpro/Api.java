package example.com.retrofitpro;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface IWeather {
    @GET("/v3/weather/now.json")
    Call<WeatherBean> getWeather(
            @Query("key")String key,
            @Query("location")String location
    );

    @GET("/v3/weather/now.json")
    Observable<WeatherBean> getWeatherWithObservable(
            @Query("key")String key,
            @Query("location")String location
    );
}