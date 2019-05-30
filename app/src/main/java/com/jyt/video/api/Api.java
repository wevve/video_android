package com.jyt.video.api;


import com.jyt.video.api.entity.FilterVideoListResult;
import com.jyt.video.api.entity.PersonHomeResult;
import com.jyt.video.api.entity.VideoTypeItem;
import com.jyt.video.common.entity.BaseJson;
import com.jyt.video.deal.entity.Record;
import com.jyt.video.home.entity.HomeResult;
import com.jyt.video.home.entity.HomeTabResult;
import com.jyt.video.login.entity.LoginResult;
import com.jyt.video.setting.entity.AlipayAccount;
import com.jyt.video.setting.entity.BankCardAccount;
import com.jyt.video.video.entity.CollectionVideo;
import com.jyt.video.video.entity.CommentVO;
import com.jyt.video.video.entity.VideoDetail;
import com.jyt.video.wallet.entity.WalletIndexInfo;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Api {



    //注册
    @POST("/appapi/register.html")
    @FormUrlEncoded
    public Observable<BaseJson> register(@Field("account")String account,@Field("pwd")String password);


    //登录
    @POST("/appapi/login.html")
    @FormUrlEncoded
    public Observable<BaseJson<LoginResult>> login(@Field("account")String account, @Field("pwd")String password);

    //首页顶部分栏
    @GET("/appapi/homeTab")
    Observable<BaseJson<HomeTabResult>> getHomeTab();

    //首页数据
    @GET("/appapi/homeMain")
    Observable<BaseJson<HomeResult>> getHomeData();

    //个人中心
    @GET("appapi/personal/userId/{userId}.html")
    Observable<BaseJson<PersonHomeResult>> getUserInfo(@Path("userId")String userId);

    //获取视频分类
     @GET("appapi/videoClass")
    Observable<BaseJson<List<VideoTypeItem>>> getVideoType();

    //根据分类获取视频列表
    @GET("appapi/videoList")
    Observable<BaseJson<FilterVideoListResult>> getVideoListByFilter();

    //视频详情
    @GET("appapi/detail/videoId/{videoId}/userId/{userId}")
    Observable<BaseJson<VideoDetail>> getVideoDetail(@Path("videoId")long videoId,@Path("userId")long userId);

    //视频点赞
    @POST("appapi/like")
    @FormUrlEncoded
    Observable<BaseJson<Object>> doLike(@Field("videoId")long videoId,@Field("userId")long userId);


    //添加评论
    @POST("appapi/comment")
    @FormUrlEncoded
    Observable<BaseJson<Object>> addComment(@Field("videoId")long videoId,@Field("userId")long userId,@Field("content")String comment);


    //获取评论
    @GET("appapi/commentList")
    Observable<BaseJson<CommentVO>> getCommentList(@Query("videoId") Long videoId,@Query("lastId")Long lastId);

    //购买视频
    @POST("/appapi/buy")
    @FormUrlEncoded
    Observable<BaseJson<Object>> buyVideo(@Field("videoId")long videoId,@Field("userId")long userId);


    //获取收藏列表
    @GET("appapi/collectionList/userId/{userId}.html")
    Observable<BaseJson<List<CollectionVideo>>> getCollectionVideoList(@Path("userId") Long userId);

    //收藏视频
    @GET("appapi/addCollection/userId/{userId}/videoId/{videoId}")
    Observable<BaseJson<Object>> doCollection(@Path("userId")Long userId,@Path("videoId")Long videoId);

    //删除收藏
    @POST("appapi/deleteCollection")
    @FormUrlEncoded
    Observable<BaseJson<Object>> delCollection(@Field("userId")Long userId,@Field("videoId")Long videoId);

    //交易明细
    @GET("appapi/record/userId/{userId}/IDtype/{typeId}/lastId/{lastId}.html")
    Observable<BaseJson<List<Record>>> getRecordList(@Path("userId")Long userId,@Path("typeId")int typeId,@Path("lastId")String lastId);

    //钱包首页
    @GET("appapi/balance/userId/{userId}.html")
    Observable<BaseJson<WalletIndexInfo>> getWalletInfo(@Path("userId")Long userId);

    //银行卡列表
    @GET("appapi/bankList/userId/{userId}/type/2.html")
    Observable<BaseJson<List<BankCardAccount>>> getBankCardList(@Path("userId")Long userId);

    //支付宝列表
    @GET("appapi/bankList/userId/{userId}/type/1.html")
    Observable<BaseJson<List<AlipayAccount>>> getALiPayAccountList(@Path("userId")Long userId);

    //保存银行卡账号
    @POST("appapi/addbank.html")
    @FormUrlEncoded
    Observable<BaseJson<Object>> saveBankCard(@FieldMap Map<String,String> map);

    //保存支付宝账号
    @POST("appapi/addbank.html")
    @FormUrlEncoded
    Observable<BaseJson<Object>> saveAlipayAccount(@FieldMap Map<String,String> map);

    //删除银行卡
    @POST("appapi/delbank.html")
    @FormUrlEncoded
    Observable<BaseJson<Object>> delBankCard(@Field("userId")Long userId,@Field("cardId")Long cardId,@Field("type")int type );

    //删除支付宝
    @POST("appapi/delbank.html")
    @FormUrlEncoded
    Observable<BaseJson<Object>> delALiAccount(@Field("userId")Long userId,@Field("alipayId")Long id,@Field("type")int type );

    //提现
    @POST("appapi/getmoney")
    @FormUrlEncoded
    Observable<BaseJson<Object>> withdraw(@Field("userId")Long userId,@Field("cardId")Long accountId,@Field("money")String money);

    @POST("appapi/upload.html")
    @Multipart
    Observable<BaseJson<String>> uploadFile(@PartMap Map<String,RequestBody>  requestBodyMap, @Part MultipartBody.Part file);

    @POST("appapi/updateUserInfo")
    @FormUrlEncoded
    Observable<BaseJson<Object>> modifyUserInfo(@Field("content")String content,@Field("userId")Long userId,@Field("type")int type);

}
