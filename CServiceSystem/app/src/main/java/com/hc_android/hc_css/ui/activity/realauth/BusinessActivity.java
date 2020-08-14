package com.hc_android.hc_css.ui.activity.realauth;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.contract.BusinessActivityContract;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.presenter.BusinessActivityPresenter;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.image.GlideEngine;
import com.hc_android.hc_css.utils.android.image.UploadFileUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;
import com.hc_android.hc_css.wight.ChoiceDialog;
import com.hc_android.hc_css.wight.CustomDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BusinessActivity extends BaseActivity<BusinessActivityPresenter, BusinessEntity.DataBean> implements BusinessActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.upload_img)
    ImageView uploadImg;
    @BindView(R.id.name_company)
    TextView nameCompany;
    @BindView(R.id.name_company_et)
    AddAndSubEditText nameCompanyEt;
    @BindView(R.id.xy_num)
    TextView xyNum;
    @BindView(R.id.xy_num_et)
    AddAndSubEditText xyNumEt;
    @BindView(R.id.legal_name)
    TextView legalName;
    @BindView(R.id.legal_name_ed)
    AddAndSubEditText legalNameEd;
    @BindView(R.id.next_b)
    TextView nextB;
    @BindView(R.id.upload_btn)
    TextView uploadBtn;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;


    private String path, type;
    private BusinessEntity.DataBean.ResultBean resultBean;

    @Override
    public BusinessActivityPresenter getPresenter() {
        return new BusinessActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("REAL_TYPE");
        if (title != null) titleRealAuthen.setText(title);
    }

    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_business;
    }

    @Override
    public void showDataSuccess(BusinessEntity.DataBean datas) {
        showContentView();
        if (datas.get_suc()==0){
            new ChoiceDialog(this, "请上传清晰、合法的营业执照", 1);
            setTextClear();
            return;
        }
        if (datas.getResult() != null) {
            resultBean = datas.getResult();
            legalNameEd.setText(resultBean.getOwner());
            nameCompanyEt.setText(resultBean.getName());
            xyNumEt.setText(resultBean.getCredit());
        }
    }

    private void setTextClear() {
        legalNameEd.setText("");
        nameCompanyEt.setText("");
        xyNumEt.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backImg, R.id.next_b, R.id.upload_img, R.id.upload_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.upload_btn:
            case R.id.upload_img:

                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .isWeChatStyle(true)
                        .isCamera(false)
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(false)// 是否可预览视频
                        .isGif(false)
                        .selectionMode(PictureConfig.SINGLE)//单选模式
                        .isOriginalImageControl(false)
                        .minimumCompressSize(1024)
                        .compress(true)//是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.next_b:
                boolean isDialog = false;
                String msg = null;
                if (resultBean != null && resultBean.isIfCreditValid()) {
                    if (TextUtils.isEmpty(nameCompanyEt.getText()) || TextUtils.isEmpty(legalNameEd.getText()) || TextUtils.isEmpty(xyNumEt.getText())){
                        msg = "请输入正确的营业执照信息！";
                        isDialog = true;
                    }else if (!resultBean.getName().equals(nameCompanyEt.getText().toString().trim())) {
                        msg = "您输入的公司名称和您提交的营业执照公司名称不一致！";
                        isDialog = true;
                    }else if (!resultBean.getOwner().equals(legalNameEd.getText().toString().trim())) {
                        msg = "您输入的法人名称和您提交的营业执照法人名称不一致！";
                        isDialog = true;
                    }else if (!resultBean.getCredit().equals(xyNumEt.getText().toString().trim())) {
                        msg = "您输入的社会信用代码和您提交的营业执照社会信用代码不一致！";
                        isDialog = true;
                    }
                } else {
                    isDialog = true;
                    msg = "您提交的营业执照不合法，请点击图片重新上传！";
                }

                if (isDialog) {
                    ChoiceDialog choiceDialog = new ChoiceDialog(this, msg, 1);
                } else {
                    if (titleRealAuthen.getText().toString().equals("企业对公打款认证")) {
                        Intent intent = new Intent(BusinessActivity.this, RemittanceActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        intent.putExtra("REAL_TYPE", titleRealAuthen.getText().toString());
                        intent.putExtra("_NAME", resultBean.getName());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(BusinessActivity.this, IDCardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        intent.putExtra("REAL_TYPE", titleRealAuthen.getText().toString());
                        intent.putExtra("_OWNER", resultBean.getOwner());
                        startActivity(intent);
                        finish();
                    }
                }
                break;
        }
    }

    /**
     * 相片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "是否压缩:" + media.isCompressed());
                        Log.i(TAG, "压缩:" + media.getCompressPath());
                        Log.i(TAG, "原图:" + media.getPath());
                        Log.i(TAG, "是否裁剪:" + media.isCut());
                        Log.i(TAG, "裁剪:" + media.getCutPath());
                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                        Log.i(TAG, "getMimeType():" + media.getMimeType());
                        Log.i(TAG, "getChooseModel():" + media.getChooseModel());
                        Log.i(TAG, "getFileName():" + media.getFileName());
                        Log.i(TAG, "media.getDuration():" + media.getDuration());
                        Log.i(TAG, "media.getSize():" + media.getSize());
                        Log.i(TAG, "getHeight():" + media.getHeight());
                        Log.i(TAG, "getWidth():" + media.getWidth());
                    }

                    if (!NullUtils.isEmptyList(selectList)) {
                        LocalMedia localMedia = selectList.get(0);
                        path = localMedia.getPath();
                        if (localMedia.isOriginal()) {
                            path = localMedia.getOriginalPath();
                            Log.i(TAG, "原图:path" + localMedia.getPath());
                        }
                        if (localMedia.getAndroidQToPath() != null) {

                            path = localMedia.getAndroidQToPath();
                        }
                        if (localMedia.isCompressed()) {
                            path = localMedia.getCompressPath();
                            Log.i(TAG, "压缩:path" + path);
                        }
                        if (path != null) {
                            type = localMedia.getMimeType();
                            uploadBtn.setVisibility(View.GONE);
                            uploadImg.setVisibility(View.VISIBLE);
                            Glide.with(this).load(path).into(uploadImg);
                            showLoadingView("全局");
                            mPresenter.pGetToken("img", type);
                        }
                        break;
                    }
            }
        }
    }

    @Override
    public void showToken(TokenEntity.DataBean dataBean) {
        UploadFileUtils.getInstance().upload(dataBean.getToken(), path, new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {

                if (responseInfo.isOK()) {
                    try {
                        String content = null;
                        String fileKey = jsonObject.getString("key");
                        mPresenter.pVerityBusiness(Address.IMG_URL + fileKey);
                    } catch (Exception e) {
                        ToastUtils.showShort("网络错误");
                        showContentView();
                        setTextClear();
                    }
                } else {
                    ToastUtils.showShort("网络错误");
                    showContentView();
                    setTextClear();
                }
            }
        });
    }

}
