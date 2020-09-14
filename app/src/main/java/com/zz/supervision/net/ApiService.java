package com.zz.supervision.net;




import com.zz.supervision.bean.CameraBean;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.bean.MapListBean;
import com.zz.supervision.bean.OperLog;
import com.zz.supervision.bean.RealTimeCtrlGroup;
import com.zz.supervision.bean.RealTimeCtrlTerminal;
import com.zz.supervision.bean.RegionExpandItem;
import com.zz.supervision.bean.UserBasicBean;
import com.zz.supervision.bean.UserInfo;
import com.zz.supervision.bean.Version;
import com.zz.supervision.bean.IpAdress;
import com.zz.supervision.bean.YsConfig;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by admin on 2018/4/23.
 */

public interface ApiService {

    @GET("/app/light/authCode/getAddress/{authCode}")
    Observable<JsonT<IpAdress>> getAddress(@Path("authCode") String authCode);

    @POST( "/app/light/login")
    Observable<JsonT<UserInfo>> login(@QueryMap Map<String, Object> params);

    @POST("/app/light/gtClientId")
    Observable<JsonT> putClientId(@QueryMap Map<String, Object> params);


    @GET("/app/light")
    Observable<JsonT<UserBasicBean>> getUserDetail();

    @POST("/app/light/logout")
    Observable<JsonT> logout();

    @POST( "/app/light/resetPwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @GET("/app/version/latest")
    Observable<JsonT<Version>> getVersion();
   @GET("/app/version/versionCode/{versionCode}")
    Observable<JsonT<Version>> getVersionInfo(@Path("versionCode") String terminalId);

    @Multipart
    @POST(URLs.VERSION + "/upload/image")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

}

