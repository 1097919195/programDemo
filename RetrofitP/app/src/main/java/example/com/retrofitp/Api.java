package example.com.retrofitp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by asus-pc on 2017/7/12.
 */

interface ZjlBean {
    @POST("/autoLogin")
    Call<LoginBean> getlogin(@Query("username")String username, @Query("password")String password);
}
