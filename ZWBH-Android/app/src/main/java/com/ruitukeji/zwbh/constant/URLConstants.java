package com.ruitukeji.zwbh.constant;

/**
 * 用于存放url常量的类
 * Created by ruitu ck on 2016/9/14.
 */

public class URLConstants {

    /**
     * 服务器地址URL
     */
    public static String SERVERURL = "http://atwwg.api.antiwearvalve.com/";

    /**
     * 测试服务器地址URL
     */
    public static String SERVERURL1 = "http://wuzaishipper.wuzaitianxia56.com/";
//    public static String SERVERURL1 = "http://wztx.drv.api.zenmechi.cc/";


    /**
     * 得到国内全部城市
     */
    public static String SERVERURL2 = "http://user.api.shahaizi.shop/";
    public static String APIURL1 = SERVERURL2 + "index.php?";


    /**
     * 请求地址URL
     */
    public static String APIURL = SERVERURL1;

    /**
     * 得到全部城市
     */
    //  public static String GETALLCITYBYCOUNTRY = APIURL1 + "m=Api&c=Region&a=getAllCityByCountryId";
    public static String ALLCITY = APIURL + "Address/getAllCity";

    /**
     * 得到热门城市
     */
    public static String ALLHOTCITY = APIURL + "Address/getAllHotCity";

    /**
     * 应用配置参数
     */
    public static String APPCONFIG = APIURL + "appConfig";

    /**
     * 启动页和广告页
     */
    public static String START = APIURL + "lastApk";

    /**
     * 获取最新apk下载地址
     */
    public static String LASTAPK = APIURL + "lastApk";

    /**
     * 首页
     */
    public static String HOME = APIURL + "index/home";

    /**
     * 公告详情
     */
    public static String ANNOUNCEMENT = APIURL + "index/getAdverDetail";

    /**
     * 获取文章内容
     */
    public static String GETARTICLE = APIURL + "index/getArticle";

    /**
     * 上传头像
     */
    public static String UPLOADAVATAR = APIURL + "user/uploadAvatar";

    /**
     * 上传图片
     */
    public static String UPLOADQFCTIMG = APIURL + "file/uploadImg";

    /**
     * 置换Token  get请求
     */
    public static String REFRESHTOKEN = APIURL + "user/refreshToken";

    /**
     * 登录
     */
    public static String USERLOGIN = APIURL + "User/login";

    /**
     * 第三方登录
     */
    public static String THIRDLOGIN = APIURL + "User/thirdLogin";

    /**
     * 第三方登录绑定手机号
     */
    public static String THIRDLOGINADD = APIURL + "User/thirdLoginAdd";


    /**
     * 发送验证码
     * reg=注册 restpwd=找回密码 login=登陆 bind=绑定手机号.
     */
    public static String SENDCAPTCHA = "http://wztx.shp.api.zenmechi.cc/index/sendCaptcha";

    /**
     * 用户注册
     */
    public static String USERREG = APIURL + "User/reg";

    /**
     * 货主个人认证
     */
    public static String PERSONAUTH = APIURL + "User/personAuth";

    /**
     * 企业个人认证
     */
    public static String BUSINESSAUTH = APIURL + "User/businessAuth";

    /**
     * 重置密码
     */
    public static String USERRESTPWD = APIURL + "User/forget";

    /**
     * 主界面周边搜素
     */
    public static String MAINHOME = "http://yuntuapi.amap.com/datasearch/around";
    public static String MAINNEARBYSEARCH = "http://yuntuapi.amap.com/datasearch/around";

    /**
     * 距离测量
     */
    public static String DISTANCE = "http://restapi.amap.com/v3/distance";

    /**
     * 物流定位轨迹搜素
     */
    public static String LOGISTICSPOSITIONING = "http://yuntuapi.amap.com/datamanage/data/list";

    /**
     * 我的消息-列表/详情
     */
    public static String MESSAGE = APIURL + "message";

    /**
     * 我的消息-详情
     */
    public static String MESSAGEDETAIL = APIURL + "message/detail";

    /**
     * 删除消息
     */
    public static String DELMESSAGE = APIURL + "message/delMessage";

    /**
     * 标记已读消息
     */
    public static String READMESSAGE = APIURL + "message/UpdateRead";

    /**
     * 未读消息数量
     */
    public static String GETUNREAD = APIURL + "message/getUnRead";

    /**
     * 获取车辆车长信息以及车型
     */
    public static String GETALLCARSTYLE = APIURL + "car/getAllCarStyle";

    /**
     * 提交货源
     */
    public static String ORDERADD = APIURL + "goods/addGoods";

    /**
     * 派单给司机
     */
    public static String SENDORDER = APIURL + "quote/sendOrder";

    /**
     * 获取用户信息
     */
    public static String USERINFO = APIURL + "User/info";

    /**
     * 查询始发地目的地
     */
    public static String INFOADDRESS = APIURL + "Address/InfoAddress";

    /**
     * 删除始发地目的地
     */
    public static String DELADDRESS = APIURL + "Address/DelAddress";

    /**
     * 是否默认地址
     */
    public static String UPDATEDEFAULT = APIURL + "Address/UpdateDefault";

    /**
     * 地址
     */
    public static String ADDRESS = APIURL + "Address/spAddressManageInsert";

    /**
     * 获取单条信息
     */
    public static String GETONEINFOADDRESS = APIURL + "Address/GetOneInfo";

    /**
     * 修改始发地 目的地信息
     */
    public static String UPDATEADDRESS = APIURL + "Address/UpdateAddress";

    /**
     * 获取常用司机信息
     */
    public static String GETDRIVERINFO = APIURL + "car/GetDriverInfo";

    /**
     * 加入 移除 黑名单
     */
    public static String DRIVERBACK = APIURL + "car/JoinDriverBack";

    /**
     * 删除司机
     */
    public static String DELCOLLECTDRIVER = APIURL + "Car/DelCollectDriver";

    /**
     * 获取个人订单信息 发票
     */
    public static String INVOICEGETGOODSINFO = APIURL + "Invoice/GetGoodsInfo";

    /**
     * 申请个人和公司发票
     */
    public static String INVOICEAPPLYINVOICE = APIURL + "Invoice/ApplyInvoice";

    /**
     * 开票记录
     */
    public static String INVOICERECORD = APIURL + "Invoice/InvoiceRecord";

    /**
     * 开票详情
     */
    public static String INVOICEREINFO = APIURL + "Invoice/InvoiceInfo";

    /**
     * 所含订单
     */
    public static String CONTAINSORDER = APIURL + "Invoice/ContainsOrder";

    /**
     * 获取异常信息
     */
    public static String GETABNORMAL = APIURL + "Abnormal/GetAbnormal";

    /**
     * 获取单条异常信息
     */
    public static String GETONEABNORMAL = APIURL + "Abnormal/GetOneAbnormal";

    /**
     * 显示货源列表
     */
    public static String GOODSLIST = APIURL + "goods/goodsList";

    /**
     * 货源详情功能
     */
    public static String GOODDETAIL = APIURL + "Goods/detail";

    /**
     * 取消货源功能
     */
    public static String CANCELGOODS = APIURL + "goods/cancelGoods";

    /**
     * 更新用户信息
     */
    public static String UPDATEINFO = APIURL + "user/updateInfo";

    /**
     * 获取个人认证信息
     */
    public static String GETPERSONAUTHINFO = APIURL + "user/getPersonAuthInfo";

    /**
     * 获取企业公司认证信息
     */
    public static String GETCOMPANYAUTHINFO = APIURL + "user/getCompanyAuthInfo";


    /**
     * 投诉中心
     */
    public static String COMPLAINTCENTER = APIURL + "Help/ComplaintInfo";


    /**
     * 帮助中心
     */
    public static String HELPCENTER = APIURL + "Help/HelpMain";

    /**
     * 帮助中心详情
     */
    public static String HELPCENTERDETAIL = APIURL + "Help/getOneHelp";

    /**
     * 用户反馈
     */
    public static String USERFEEDBACK = APIURL + "Help/UserFadeBack";

    /**
     * 显示订单列表
     */
    public static String SHOWORDERLIST = APIURL + "order/showOrderList";

    /**
     * 显示司机报价列表
     */
    public static String SHOWDRIVERQUOTELIST = APIURL + "quote/showDriverQuoteList";

    /**
     * 提交司机报价
     */
    public static String SENDDRIVERPRICE = APIURL + "quote/confirmQuotePrice";

    /**
     * 显示订单详情
     */
    public static String SHOWORDERINFO = APIURL + "order/showOrderInfo";

    /**
     * 查看凭证
     */
    public static String SHOWCERPIC = APIURL + "order/showCerPic";

    /**
     * 通过余额支付
     */
    public static String SCOREPAY = APIURL + "pay/scorePay";

    /**
     * 微信支付
     */
    public static String WXPAY = APIURL + "pay/wxpay";

    /**
     * 支付宝支付
     */
    public static String ALIPAY = APIURL + "pay/alipay";

    /**
     * 上传支付凭证
     */
    public static String UPLOADCERPIC = APIURL + "order/uploadCerPic";

    /**
     * 发送评论内容
     */
    public static String SENDCOMMENTINFO = APIURL + "comment/sendCommentInfo";

    /**
     * 获取评论内容
     */
    public static String COMMENTINFO = APIURL + "comment/commentInfo";

    /**
     * 显示我的推荐列表
     */
    public static String SHOWMYRECOMMLIST = APIURL + "recommend/showMyRecommList";

    /**
     * 改变广告状态
     */
    public static String CHANGEAD = APIURL + "User/changeAd";

    /**
     * 获取广告状态
     */
    public static String ISAD = APIURL + "User/isAd";

    /**
     * 我的钱包
     */
    public static String PAY = APIURL + "pay";

    /**
     * 提现记录
     */
    public static String SHOWCASHRECORD = APIURL + "pay/showCashRecord";

    /**
     * 获取个人银行卡信息
     */
    public static String MYBANKCARD = APIURL + "Pay/MyBankCard";

    /**
     * 添加银行卡
     */
    public static String ADDBANKCARD = APIURL + "Pay/AddBankCard";

    /**
     * 获得银行
     */
    public static String GETBANK = APIURL + "Pay/GetBank";


    /**
     * 充值 支付宝支付
     */
    public static String RECHARGEBYALIPAY = APIURL + "pay/rechargeByAlipay";

    /**
     * 充值 微信支付
     */
    public static String RECHARGEBYWEXIN = APIURL + "pay/rechargeByWexin";

    /**
     * 充值记录
     */
    public static String RECHARGERECORD = APIURL + "pay/rechargeRecord";

    /**
     * 查看账单
     */
    public static String SHOWPAYRECORD = APIURL + "pay/showPayRecord";

    /**
     * 缴纳保证金  支付宝
     */
    public static String ALIPAYCASH = APIURL + "pay/alipayCash";

    /**
     * 缴纳保证金  微信
     */
    public static String PAYBOND1 = APIURL + "pay/wxpayCash";

    /**
     * 修改密码
     */
    public static String UPDATEPWD = APIURL + "User/updatePwd";
}
