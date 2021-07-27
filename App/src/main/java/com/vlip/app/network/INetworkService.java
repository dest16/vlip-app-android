package com.vlip.app.network;

import com.vlip.app.bean.ResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 网络服务接口
 *
 * @author zm
 */
public interface INetworkService {

    @POST("admin/login")
    Observable<ResultBean> login(@Body Map<String, Object> params);

    @POST("admin/register")
    Observable<ResultBean> register(@Body Map<String, Object> params);

    @GET("city/list")
    Observable<ResultBean> queryCities(@QueryMap Map<String, Object> params);

    @POST("orders/add")
    Observable<ResultBean> publishOrder(@Body Map<String, Object> params);

    @GET("orders/list")
    Observable<ResultBean> queryOrdersByStatus(@QueryMap Map<String, Object> params);

    @GET("orders/carrier/list")
    Observable<ResultBean> queryAcceptList(@QueryMap Map<String, Object> params);

    @POST("orders/accept")
    Observable<ResultBean> acceptOrder(@QueryMap Map<String, Object> params);

    @POST("orders/cancel")
    Observable<ResultBean> cancelOrder(@QueryMap Map<String, Object> params);

    @POST("admin/updatePassword")
    Observable<ResultBean> updatePassword(@QueryMap Map<String, Object> params);
   /**********************************************************************************/
    @GET("queryTreeCategory")
    Observable<ResultBean> queryTreeCategory(@QueryMap Map<String, Object> params);

    @GET("queryGoodsByCategory")
    Observable<ResultBean> queryGoodsByCategory(@QueryMap Map<String, Object> params);

    @GET("queryGoodsByLike")
    Observable<ResultBean> queryGoodsByLike(@QueryMap Map<String, Object> params);

    @POST("getProductBySpec")
    Observable<ResultBean> getProductBySpec(@Body Map<String, Object> params);

    @POST("saveCart")
    Observable<ResultBean> saveCart(@Header("cartKey") String cartKey, @Body Map<String, Object> params);

    @POST("queryAddressList")
    Observable<ResultBean> queryAddressList();

    @POST("queryCurrentLevelAreaById")
    Observable<ResultBean> queryCurrentLevelAreaById(@Body Map<String, Object> params);

    @POST("queryAreaByLevel")
    Observable<ResultBean> queryAreaByLevel(@Body Map<String, Object> params);

    @POST("queryAreaByParentId")
    Observable<ResultBean> queryAreaByParentId(@Body Map<String, Object> params);

    @POST("saveAddress")
    Observable<ResultBean> saveAddress(@Body Map<String, Object> params);

    @POST("updateAddress")
    Observable<ResultBean> updateAddress(@Body Map<String, Object> params);

    @POST("getDefaultAddress")
    Observable<ResultBean> getDefaultAddress();

    @POST("submitOrder")
    Observable<ResultBean> submitOrder(@Body Map<String, Object> params);

    @POST("alipay")
    Observable<ResultBean> alipay(@Body Map<String, Object> params);

    @POST("queryOrderByStatus")
    Observable<ResultBean> queryOrderByStatus(@Body Map<String, Object> params);

}
