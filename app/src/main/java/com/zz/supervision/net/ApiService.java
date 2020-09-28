package com.zz.supervision.net;




import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.CameraBean;
import com.zz.supervision.bean.CompanyType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.bean.MapListBean;
import com.zz.supervision.bean.OperLog;
import com.zz.supervision.bean.RealTimeCtrlGroup;
import com.zz.supervision.bean.RealTimeCtrlTerminal;
import com.zz.supervision.bean.RegionExpandItem;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.bean.UserBasicBean;
import com.zz.supervision.bean.UserInfo;
import com.zz.supervision.bean.Version;
import com.zz.supervision.bean.IpAdress;
import com.zz.supervision.bean.YsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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


    @POST( "/app/v1/login")
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
    @POST( "/app/v1/supervise/enclosure/upload")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

    @GET("/app/v1/supervise/companyInfo/list")
    Observable<JsonT<List<CompanyBean>>> getCompanyInfoList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/universal/getRecordList")
    Observable<JsonT<List<SuperviseBean.ResposeBean>>> getRecordList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/companyInfo/removeCompanyInfo/{id}")
    Observable<JsonT> removeCompanyInfo(@Path("id")String id);

    @GET("/app/v1/supervise/companyInfo/{id}")
    Observable<JsonT<CompanyBean>> getCompanyInfo(@Path("id") String id);


    @GET("/app/v1/supervise/companyInfo/selectCompanyGroupCount")
    Observable<JsonT<List<CompanyType>>> selectCompanyGroupCount(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/companyInfo/editCompanyInfo")
    Observable<JsonT<String>> editCompanyInfo(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/companyInfo/addCompanyInfo")
    Observable<JsonT<String>> postCompanyInfo(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/companyInfo/uploadImgs/{id}")
    @FormUrlEncoded
    Observable<JsonT> uploadCompanyImgs(@Path("id")String id,@Field("enclosureIds") String handleFile);

    @POST("/app/v1/supervise/enclosure/upload")
    @FormUrlEncoded
    Observable<JsonT<List<Integer>>> uploadImgs( @Field("filebase64s") String handleFile);


    @GET("/app/v1/supervise/companyInfo/getLawEnforcerList")
    Observable<JsonT<List<LawEnforcerBean>>> getLawEnforcerList();

    @GET("/app/v1/supervise/dict/getDicts")
    Observable<JsonT<List<BusinessType>>> getDicts(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/spxsInspectionRecord/getItems")
    Observable<JsonT<List<SuperviseBean>>> getSuperviseList();

    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/spxsInspectionRecord/confirm/{id}")
    Observable<JsonT<SuperviseBean.ResposeConfirmBean>> submitSuperviseConfirm(@Path("id")String id, @Body ArrayList<SuperviseBean.PostBean> requestBody);


    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/spxsInspectionRecord/submitScoreItems/{id}")
    Observable<JsonT<SuperviseBean.ResposeBean>> submitSupervise(@Path("id")String id,@Body ArrayList<SuperviseBean.PostBean> requestBody);

    @POST("/app/v1/supervise/universal/createRecord")
    Observable<JsonT<Integer>> createRecord(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/enclosure/base64/{type}/{modelId}")
    Observable<JsonT<List<ImageBack>>> getImageBase64(@Path("type") String type, @Path("modelId") String modelId);

    @POST("/app/v1/supervise/spxsInspectionRecord/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("id")String id,@QueryMap Map<String, Object> params);


}

