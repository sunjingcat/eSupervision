package com.zz.supervision.net;


import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.CityBean;
import com.zz.supervision.bean.CompanyType;
import com.zz.supervision.bean.DeviceCheck;
import com.zz.supervision.bean.DeviceType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.bean.MonitorBean;
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.bean.PipePartBean;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SceneRecord;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.bean.SuperviseInfoBean;
import com.zz.supervision.bean.UserBasicBean;
import com.zz.supervision.bean.UserInfo;
import com.zz.supervision.bean.Version;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * Created by admin on 2018/4/23.
 */

public interface ApiService {


    @POST("/app/v1/login")
    Observable<JsonT<UserInfo>> login(@QueryMap Map<String, Object> params);

    @POST("/app/light/gtClientId")
    Observable<JsonT> putClientId(@QueryMap Map<String, Object> params);


    @GET("/app/v1")
    Observable<JsonT<UserBasicBean>> getUserDetail();

    @POST("/app/v1/logout")
    Observable<JsonT> logout();

    @POST("/app/v1/resetPwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/coldstorage/resetPwd/{id}")
    Observable<JsonT> resetPwd(@Path("id") String id, @QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/version/latest")
    Observable<JsonT<Version>> getVersion();

    @GET("/app/v1/supervise/version/versionCode/{versionCode}")
    Observable<JsonT<Version>> getVersionInfo(@Path("versionCode") String terminalId);

    @Multipart
    @POST("/app/v1/supervise/enclosure/upload")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

    @GET("/app/v1/supervise/companyInfo/list")
    Observable<JsonT<List<CompanyBean>>> getCompanyInfoList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbDeviceAccessory/list/{deviceId}")
    Observable<JsonT<List<AccessoryBean>>> getAccessoryInfoList(@Path("deviceId") String deviceId,@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbDeviceInfo/list")
    Observable<JsonT<List<EquipmentBean>>> getEquipmentInfoList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/zdgypProductInfo/list")
    Observable<JsonT<List<ProductBean>>> getZdgypProductInfo(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/sceneRecord")
    Observable<JsonT<List<SceneRecord>>> getSceneRecord(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbInspectionRecord/deviceListByCompanyAndType")
    Observable<JsonT<List<EquipmentBean>>> deviceListByCompanyAndType(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/universal/getRecordList")
    Observable<JsonT<List<RecordBean>>> getRecordList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/llglInspectionRecord/list")
    Observable<JsonT<List<RecordBean>>> getColdRecordList(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/{url}/list")
    Observable<JsonT<List<RecordBean>>> getTzsbInspectionRecordList(@Path("url") String url,@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/{url}/list")
    Observable<JsonT<List<RecordBean>>> getYaoRecordList(@Path("url") String url, @QueryMap Map<String, Object> params);

    @DELETE("/app/v1/supervise/{url}/{id}")
    Observable<JsonT> removeCompanyInfo(@Path(value = "url", encoded = true) String url,@Path("id") String id);

    @DELETE("/app/v1/supervise/tzsbDeviceInfo/{id}")
    Observable<JsonT> removeDeviceInfo(@Path("id") String id);

    @DELETE("/app/v1/supervise/tzsbDeviceInfo/tzsbPressurepipePart/{id}")
    Observable<JsonT> removePressurepipePartInfo(@Path("id") String id);


    @DELETE("/app/v1/supervise/{url}/{id}")
    Observable<JsonT> removeSuperviseInfo(@Path("url") String url, @Path("id") String id);

    @GET("/app/v1/supervise/{url}/{id}")
    Observable<JsonT<CompanyBean>> getCompanyInfo(@Path("url") String url, @Path("id") String id);

    @GET("/app/v1/supervise/zdgypProductInfo/{id}")
    Observable<JsonT<ProductBean>> getZdgypProductInfo( @Path("id") String id);

    @GET("/app/v1/supervise/tzsbDeviceInfo/{id}")
    Observable<JsonT<EquipmentBean>> getEquipmentInfo(@Path("id") String id);

    @GET("/app/v1/supervise/tzsbDeviceAccessory/{id}")
    Observable<JsonT<AccessoryBean>> getAccessoryInfo(@Path("id") String id);

    @GET("/app/v1/supervise/tzsbDeviceInfo/tzsbPressurepipePart/{id}")
    Observable<JsonT<PipePartBean>> getPressurepipePartInfo(@Path("id") String id);

    @GET("/app/v1/supervise/tzsbDeviceCheck/{url}/{id}")
    Observable<JsonT<DeviceCheck>> getCheckInfo(@Path("url") String url,@Path("id") String id);

    @GET("/app/v1/supervise/companyInfo/selectCompanyGroupCount")
    Observable<JsonT<List<CompanyType>>> selectCompanyGroupCount(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/companyInfo/editCompanyInfo")
    Observable<JsonT<String>> editCompanyInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/supervise/{url}")
    Observable<JsonT<String>> editYaoCompanyInfo(@Path("url") String url, @QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/companyInfo/addCompanyInfo")
    Observable<JsonT<String>> postCompanyInfo(@QueryMap Map<String, Object> params);

   @POST("/app/v1/supervise/zdgypProductInfo")
    Observable<JsonT<String>> postZdgypProductInfo(@QueryMap Map<String, Object> params);


    @PUT("/app/v1/supervise/tzsbDeviceInfo")
    Observable<JsonT<String>> editTzsbDeviceInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/supervise/zdgypProductInfo")
    Observable<JsonT<String>> editZdgypProductInfo(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/supervise/tzsbDeviceAccessory")
    Observable<JsonT<String>> editAccessoryInfo(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/tzsbDeviceInfo")
    Observable<JsonT<String>> postTzsbDeviceInfo(@QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/tzsbDeviceAccessory")
    Observable<JsonT<String>> postAccessory(@QueryMap Map<String, Object> params);


    @POST("/app/v1/supervise/{url}")
    Observable<JsonT<String>> postYaoCompanyInfo(@Path("url") String url, @QueryMap Map<String, Object> params);

    @POST("/app/v1/supervise/{url}/uploadImgs/{id}")
    @FormUrlEncoded
    Observable<JsonT> uploadCompanyImgs(@Path("url") String url, @Path("id") String id, @Field("enclosureIds") String handleFile);

    @POST("/app/v1/supervise/enclosure/uploadSingle")
    @FormUrlEncoded
    Observable<JsonT<String>> uploadImg(@Field("base64") String handleFile);


    @GET("/app/v1/supervise/companyInfo/getLawEnforcerList")
    Observable<JsonT<List<LawEnforcerBean>>> getLawEnforcerList();

    @GET("/app/v1/supervise/dict/getDicts")
    Observable<JsonT<List<BusinessType>>> getDicts(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbDeviceCheck/getCheckCategoryDictByDeviceType/{deviceType}")
    Observable<JsonT<List<BusinessType>>> getCheckCategory(@Path("deviceType") String deviceType);

    @GET("/app/v1/supervise/tzsbDeviceCheck/beforeAddDeviceCheck/{deviceId}")
    Observable<JsonT<List<BeforeAddDeviceCheck>>> beforeAddDeviceCheck(@Path("deviceId") String deviceId);

    @GET("/app/v1/supervise/tzsbDeviceCheck/beforeAddPartCheck")
    Observable<JsonT<List<BeforeAddDeviceCheck>>> beforeAddPartCheck(@Path("url") String url);

    @GET("/app/v1/supervise/tzsbOrganization/{type}")
    Observable<JsonT<List<OrganizationBean>>> tzsbRegistOrganizationList(@Path("type") String type, @QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbDeviceInfo/tzsbPressurepipePartList/{deviceId}")
    Observable<JsonT<List<PipePartBean>>> tzsbPressurepipePartList(@Path("deviceId") String deviceId);

    @GET("/app/v1/supervise/tzsbDeviceInfo/selectTzsbCompanyTypeGroupCount/{companyId}")
    Observable<JsonT<List<DeviceType>>> selectTzsbCompanyTypeGroupCount(@Path("companyId") String companyId);

    @GET("/app/v1/supervise/tzsbDeviceInfo/selectTzsbDeviceType")
    Observable<JsonT<List<DictBean>>> selectTzsbDeviceType();

    @GET("/app/v1/supervise/tzsbCompanyInfo/citys")
    Observable<JsonT<List<CityBean>>> getCitys();

    @GET("/app/v1/supervise/tzsbOrganization/selectorganizationalUnit")
    Observable<JsonT<List<DictBean>>> selectorganizationalUnit();

    @GET("/app/v1/supervise/universal/getInspectionTypeByCompanyType")
    Observable<JsonT<List<BusinessType>>> getInspectionTypeByCompanyType(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/{url}/getItems/{id}")
    Observable<JsonT<List<SuperviseBean>>> getSuperviseList(@Path("id") String id, @Path("url") String url);


    @GET("/app/v1/supervise/{url}/getItems")
    Observable<JsonT<RiskSuperviseBean>> getRiskSuperviseList(@Path("url") String url);

    @GET("/app/v1/supervise/{url}/recordWithItems/{id}")
    Observable<JsonT<SuperviseInfoBean>> getSuperviseInfo(@Path("url") String url, @Path("id") String id);


    @GET("/app/v1/supervise/{url}/recordWithItems/{id}")
    Observable<JsonT<RiskSuperviseBean>> getRiskSuperviseInfo(@Path("url") String url, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/{url}/confirm/{id}")
    Observable<JsonT<SuperviseBean.ResposeConfirmBean>> submitSuperviseConfirm(@Path("url") String url, @Path("id") String id, @Body ArrayList<SuperviseBean.PostBean> requestBody);

    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/{url}/confirm/{id}")
    Observable<JsonT<SuperviseBean.ResposeConfirmBean>> submitSpxsRiskRecordConfirm(@Path("url") String url, @Path("id") String id, @Body RiskSuperviseBean.PostBean requestBody);

    @POST("/app/v1/supervise/tzsbDeviceInfo/tzsbPressurepipePart")
    Observable<JsonT> addPressurepipePart(@QueryMap Map<String, Object> params);

    @PUT("/app/v1/supervise/tzsbDeviceInfo/tzsbPressurepipePart")
    Observable<JsonT> editPressurepipePart(@QueryMap Map<String, Object> params);


    @Headers("Content-Type: application/json")
    @PUT("/app/v1/supervise/tzsbDeviceCheck")
    Observable<JsonT> putTzsbDeviceCheck(@Body DeviceCheck requestBody);


    @Headers("Content-Type: application/json")
    @PUT("/app/v1/supervise/tzsbDeviceCheck/addPressurepipePartCheck")
    Observable<JsonT> addPressurepipePartCheck(@Body DeviceCheck requestBody);


    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/{url}/submitScoreItems/{id}")
    Observable<JsonT<SuperviseBean.ResposeBean>> submitSupervise(@Path("url") String url, @Path("id") String id, @Body ArrayList<SuperviseBean.PostBean> requestBody);


    @Headers("Content-Type: application/json")
    @POST("/app/v1/supervise/{url}/submitScoreItems/{id}")
    Observable<JsonT<Integer>> submitSpxsRiskRecord(@Path("url") String url, @Path("id") String id, @Body RiskSuperviseBean.PostBean requestBody);

    @POST("/app/v1/supervise/universal/createRecord")
    Observable<JsonT<Integer>> createRecord(@QueryMap Map<String, Object> params);

  @POST("/app/v1/supervise/sceneRecord")
    Observable<JsonT<Integer>> createSceneRecord(@QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/enclosure/base64/{type}/{modelId}")
    Observable<JsonT<List<ImageBack>>> getImageBase64(@Path("type") String type, @Path("modelId") String modelId);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/sceneRecord")
    Observable<JsonT> submitSceneRecordSign( @FieldMap Map<String, Object> versionBody);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSign(@Path("url") String url, @Path("id") String id, @FieldMap Map<String, Object> versionBody);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT<SuperviseBean.ResposeBean>> submitSign(@Path("url") String url, @Path("id") String id, @Field("companySign") String companySign, @Field("officerSign") String officerSign, @Field("recordSign") String recordSign, @Field("reformTime") String reformTime, @Field("resultReduction") String resultReduction, @Field("inspectionOpinion") String inspectionOpinion, @Field("violation") String violation);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT<SuperviseBean.ResposeBean>> submitSign1(@Path("url") String url, @Path("id") String id,@FieldMap Map<String, Object> versionBody);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitSignRisk(@Path("url") String url, @Path("id") String id, @Field("fillerSign") String fillerSign, @Field("ownerSign") String ownerSign, @Field("reviewerSign") String reviewerSign);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/{url}/submitSign/{id}")
    Observable<JsonT> submitZdgypSign(@Path("url") String url, @Path("id") String id, @Field("companySign") String companySign, @Field("officerSign") String officerSign, @Field("isRandomCheck") String isRandomCheck,@Field("randomCheckType") String randomCheckType, @Field("inspectionMethod") String inspectionMethod,@Field("inspectionResult") String inspectionResult,@Field("reductionOpinion ") String reductionOpinion );

    @GET("/app/v1/supervise/{url}/{id}")
    Observable<JsonT<SuperviseBean.ResposeBean>> getSuperviseDetail(@Path("url") String url, @Path("id") String id);

    @GET("/app/v1/supervise/sceneRecord/{id}")
    Observable<JsonT<SceneRecord>> getSceneRecordDetail( @Path("id") String id);

    @GET("/app/v1/supervise/pdfPrint/getPdfDownPath/{id}")
    Observable<JsonT<String>> getDocInfo(@Path("id") String id, @QueryMap Map<String, Object> params);

    @GET("/app/v1/supervise/tzsbInspectionRecord/order/{recordId}")
    Observable<JsonT<MonitorBean>> getDeviceByCheck(@Path("recordId") String recordId);

    @FormUrlEncoded
    @PUT("/app/v1/supervise/tzsbInspectionRecord/editOrder/{id}")
    Observable<JsonT> postDeviceByCheck(@Path("id") String id, @Field("illegalActivity") String illegalActivity, @Field("lawContent") String lawContent, @Field("accordContent") String accordContent, @Field("reformMeasure") String reformMeasure);

    @PUT("/app/v1/supervise/tzsbInspectionRecord/complete/{recordId}")//sceneRecord,tzsbInspectionRecord
    Observable<JsonT<SuperviseBean.ResposeBean>> completeTzsbInspectionRecord(@Path("recordId") String recordId);
    @PUT("/app/v1/supervise/sceneRecord/complete/{recordId}")//sceneRecord,tzsbInspectionRecord
    Observable<JsonT<Integer>> completeSceneRecord(@Path("recordId") String recordId);
}

