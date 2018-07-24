package example.com.okh_rxj_ret_all;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by asus-pc on 2017/8/3.
 */

//Call请求发送到Web服务器返回响应调用
interface ApiService {
    @GET("/autoLogin")
    Call<LoginBean> getlogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("/autoLogin")
    Observable<LoginBean> getloginWithObservable(
            @Query("username") String username,
            @Query("password") String password
    );
}
