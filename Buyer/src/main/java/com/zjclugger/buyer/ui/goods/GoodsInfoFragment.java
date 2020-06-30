package com.zjclugger.buyer.ui.goods;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.persistence.entity.Shopcart;
import com.zjclugger.buyer.ui.vm.BuyerViewModel;
import com.zjclugger.buyer.webapi.response.ProductResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.buyer.ui.banner.BannerImageAdapter;
import com.wuhenzhizao.sku.bean.Sku;
import com.youth.banner.Banner;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * item页ViewPager里的商品Fragment
 */
public class GoodsInfoFragment extends BaseFragment {
    @BindView(R.id.goods_banner)
    Banner mGoodsBannerView;
    @BindView(R.id.elvv_comment_view)
    ExtendLabelValueView mCommentView;
    @BindView(R.id.elvv_choose_sku)
    ExtendLabelValueView mChooseSkuView;
    @BindView(R.id.fab_to_top)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.open_goods_detail)
    TextView mOpenDetailView;
    List<JListItem<String>> mBannerList = new ArrayList<>();
    @BindView(R.id.goods_info_scroll_view)
    ScrollView mGoodsScrollView;
    @BindView(R.id.iv_add_cart_anim)
    ImageView mAddCartAnimView;
    @BindView(R.id.iv_shopping_cart)
    ImageView mShopCartView;
    @BindView(R.id.add_to_shop_cart)
    TextView mAddToShopCartView;
    @BindView(R.id.buy_now)
    TextView mBuyNowView;

    BuyerViewModel mViewModel;
    // disposable 是订阅事件，可以用来取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
    CompositeDisposable mDisposable = new CompositeDisposable();

    //layout
    @BindView(R.id.shop_cart_goods_quantity)
    TextView mShopCartGoodsQuantityView;

    private GoodsSkuDialog mGoodSkuDialog;
    private int mShopCartGoodsCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(BuyerViewModel.class);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override
    public void initViews() {
        //init data
        initData();
        mGoodsBannerView.setAdapter(new BannerImageAdapter(mBannerList, true));
        mGoodsBannerView.setBannerGalleryEffect(20, 10);
        //添加透明效果(画廊配合透明效果更棒)
        mGoodsBannerView.addPageTransformer(new AlphaPageTransformer());

        //设置文字中间一条横线
        // tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsScrollView.smoothScrollTo(0, 0);
            }
        });

        initSkuDialog();
        mChooseSkuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodSkuDialog.show();
            }
        });

        Shopcart shopcart = new Shopcart();

        mAddToShopCartView.setOnClickListener(v -> {
            shopcart.setCount(68);
            shopcart.setGoodsId("G001234");
            shopcart.setGoodsName("G2最新产品");
            shopcart.setTransactionPrice("9999.99");
            mViewModel.addToShopCart(shopcart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            showMessage("添加商品到本地数据库");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            showMessage("添加商品到本地数据库发生了错误:" + throwable.getMessage());
                        }
                    });

        });

        mBuyNowView.setOnClickListener(v -> {
            shopcart.setCount(28);
            shopcart.setGoodsId("G001235");
            shopcart.setGoodsName("G5最新产品");
            shopcart.setTransactionPrice("19999.99");
            mViewModel.saveShopCart(shopcart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Long aLong) {
                            showMessage("立即购买--已记录到本地数据库");
                        }

                        @Override
                        public void onError(Throwable e) {
                            showMessage("立即购买--发生了错误：" + e.getMessage());
                        }
                    });

        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mDisposable) {
            mDisposable.clear();
        }
    }

    private void initSkuDialog() {
        if (null == mGoodSkuDialog) {
            mGoodSkuDialog = new GoodsSkuDialog(mContext);
            mGoodSkuDialog.setGravity(Gravity.BOTTOM)
                    .setCanceledOnTouchOutside(false)
                    .setMargin(1, 1, 1, -5);
            ProductResult productResult =
                    CommonUtils.json2Object(ProductResult.PRODUCT_JSON_STRING, ProductResult.class);
            mGoodSkuDialog.setData(productResult, new GoodsSkuDialog.Callback() {
                @Override
                public void onAdded(Sku sku, int quantity) {
                    mShopCartGoodsCount += quantity;

                    mShopCartGoodsQuantityView.setVisibility(View.VISIBLE);

                    // 获取SKU面板Logo拷贝
                    ImageView logoImageView = new ImageView(mContext);
                    mAddCartAnimView.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(mAddCartAnimView.getDrawingCache());
                    logoImageView.setImageBitmap(bitmap);
                    mAddCartAnimView.setDrawingCacheEnabled(false);

                    int[] startLocation = new int[2];
                    mAddCartAnimView.getLocationOnScreen(startLocation);
                    // 执行动画
                    startAddToCartAnimation(logoImageView, startLocation);
                }
            });
        }
    }


    public void setOpenDetailViewListener(View.OnClickListener listener) {
        if (null != mOpenDetailView) {
            mOpenDetailView.setOnClickListener(listener);
        }
    }

    public void setOpenCommentViewListener(View.OnClickListener listener) {
        if (null != mCommentView) {
            mCommentView.setOnClickListener(listener);
        }
    }

    private void initData() {
        /**
         * 仿淘宝商品详情第一个是视频
         */
        /*    List<DataBean> list = new ArrayList<>();
            list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087
            .mp4", "第一个放视频", 2));
            list.add(new DataBean(R.mipmap.img_banner_1, "听风.赏雨", 1));
            list.add(new DataBean(R.mipmap.img_banner_2, "迪丽热巴.迪力木拉提", 1));
            list.add(new DataBean(R.mipmap.img_banner_3, "爱美.人间有之", 1));
            return list;*/

        mBannerList.add(new JListItem(2201, "第一个", "https://img.zcool" +
                ".cn/community/011ad05e27a173a801216518a5c505.jpg"));
        mBannerList.add(new JListItem(2202, "第一个", "https://img.zcool" +
                ".cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        mBannerList.add(new JListItem(2203, "第一个", "https://img.zcool" +
                ".cn/community/013c7d5e27a174a80121651816e521.jpg"));
        mBannerList.add(new JListItem(2204, "第一个", "https://img.zcool" +
                ".cn/community/01b8ac5e27a173a80120a895be4d85.jpg"));
        mBannerList.add(new JListItem(2205, "第一个", "https://img.zcool" +
                ".cn/community/01a85d5e27a174a80120a895111b2c.jpg"));
    }

    /**
     * ********************************添加到购物车动画***********************************
     */
    private ViewGroup mAnimationMaskLayout;      // 动画浮层

    private ViewGroup buildAddToCartAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToCartAnimLayout(View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private void startAddToCartAnimation(final View v, int[] startLocation) {
        mAnimationMaskLayout = buildAddToCartAnimLayout();
        mAnimationMaskLayout.addView(v);
        final View view = addViewToCartAnimLayout(v, startLocation);
        int[] bottomCartLocation = new int[2];                    // 这是用来存储动画结束位置的X、Y坐标
        mShopCartView.getLocationInWindow(bottomCartLocation);

        AddToCartAnimation animation = new AddToCartAnimation(startLocation, bottomCartLocation);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                mAnimationMaskLayout.removeView(v);
                mShopCartGoodsQuantityView.setText(String.valueOf(mShopCartGoodsCount));
            }
        });
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_goods_info, null);
        initView(rootView);
        initListener();
        initData();
        return rootView;
    }

    private void initListener() {
        fab_up_slide.setOnClickListener(this);
        ll_current_goods.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_pull_up.setOnClickListener(this);
        ll_goods_detail.setOnClickListener(this);
        ll_goods_config.setOnClickListener(this);
        sv_switch.setOnSlideDetailsListener(this);
    }

    private void initView(View rootView) {
        fab_up_slide = (FloatingActionButton) rootView.findViewById(R.id.fab_up_slide);
        sv_switch = (SlideDetailsLayout) rootView.findViewById(R.id.sv_switch);
        sv_goods_info = (ScrollView) rootView.findViewById(R.id.sv_goods_info);
        v_tab_cursor = rootView.findViewById(R.id.v_tab_cursor);
        vp_item_goods_img = (ConvenientBanner) rootView.findViewById(R.id.vp_item_goods_img);
        vp_recommend = (ConvenientBanner) rootView.findViewById(R.id.vp_recommend);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        ll_current_goods = (LinearLayout) rootView.findViewById(R.id.ll_current_goods);
        ll_activity = (LinearLayout) rootView.findViewById(R.id.ll_activity);
        ll_comment = (LinearLayout) rootView.findViewById(R.id.ll_comment);
        ll_recommend = (LinearLayout) rootView.findViewById(R.id.ll_recommend);
        ll_pull_up = (LinearLayout) rootView.findViewById(R.id.ll_pull_up);
        ll_goods_detail = (LinearLayout) rootView.findViewById(R.id.ll_goods_detail);
        ll_goods_config = (LinearLayout) rootView.findViewById(R.id.ll_goods_config);
        tv_goods_detail = (TextView) rootView.findViewById(R.id.tv_goods_detail);
        tv_goods_config = (TextView) rootView.findViewById(R.id.tv_goods_config);
        tv_goods_title = (TextView) rootView.findViewById(R.id.tv_goods_title);
        tv_new_price = (TextView) rootView.findViewById(R.id.tv_new_price);
        tv_old_price = (TextView) rootView.findViewById(R.id.tv_old_price);
        tv_current_goods = (TextView) rootView.findViewById(R.id.tv_current_goods);
        tv_comment_count = (TextView) rootView.findViewById(R.id.tv_comment_count);
        tv_good_comment = (TextView) rootView.findViewById(R.id.tv_good_comment);
        setDetailData();
        setLoopView();
        setRecommendGoods();

        //设置文字中间一条横线
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();

        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        vp_item_goods_img.setPageIndicator(new int[]{R.mipmap.index_white, R.mipmap.index_red});
        vp_item_goods_img.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign
        .CENTER_HORIZONTAL);
        vp_recommend.setPageIndicator(new int[]{R.drawable.shape_item_index_white,
                R.drawable.shape_item_index_red});
        vp_recommend.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
    }

    *//**
     * 加载完商品详情执行
     *//*
    public void setDetailData() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsInfoWebFragment = new GoodsInfoWebFragment();
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);

        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment)
        .commitAllowingStateLoss();
    }

    */

    /**
     * 设置推荐商品
     *//*
    public void setRecommendGoods() {
        List<RecommendGoodsBean> data = new ArrayList<>();
        data.add(new RecommendGoodsBean("Letv/乐视 LETV体感-超级枪王 乐视TV超级电视产品玩具 体感游戏枪 电玩道具 黑色",
                "http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg",
                new BigDecimal(599), "799"));
        data.add(new RecommendGoodsBean("IPEGA/艾派格 幽灵之子 无线蓝牙游戏枪 游戏体感枪 苹果安卓智能游戏手柄 标配",
                "http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg",
                new BigDecimal(299), "399"));
        data.add(new RecommendGoodsBean("Letv/乐视 LETV体感-超级枪王 乐视TV超级电视产品玩具 体感游戏枪 电玩道具 黑色",
                "http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg",
                new BigDecimal(599), "799"));
        data.add(new RecommendGoodsBean("IPEGA/艾派格 幽灵之子 无线蓝牙游戏枪 游戏体感枪 苹果安卓智能游戏手柄 标配",
                "http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg",
                new BigDecimal(299), "399"));
        List<List<RecommendGoodsBean>> handledData = handleRecommendGoods(data);
        //设置如果只有一组数据时不能滑动
        vp_recommend.setManualPageable(handledData.size() == 1 ? false : true);
        vp_recommend.setCanLoop(handledData.size() == 1 ? false : true);
        vp_recommend.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new ItemRecommendAdapter();
            }
        }, handledData);
    }*/

}
