package com.hc_android.hc_css.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.utils.DownloadUtil;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.android.EasyPermissionUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileBrowsActivity extends BaseActivity implements QbSdk.PreInitCallback , ValueCallback<String> {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.btn_choose)
    ImageView btnChoose;
    @BindView(R.id.file_brows_include)
    ConstraintLayout fileBrowsInclude;
    @BindView(R.id.fileName)
    TextView fileName;
    @BindView(R.id.fileSize)
    TextView fileSize;
    @BindView(R.id.openFile)
    TextView openFile;
    @BindView(R.id.file_download)
    ProgressBar fileDownload;

    private String savePath;
    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        FileEntity fileEntity = (FileEntity) intent.getSerializableExtra("_fileEntity");
        fileName.setText(fileEntity.getName());
        fileSize.setText(FileUtils.bytes2kb(fileEntity.getSize()));

        backImg.setOnClickListener(view -> finish());
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] perms = {
                        // 把你想要申请的权限放进这里就行，注意用逗号隔开
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                };
                boolean flag = EasyPermissionUtils.checkPermission(FileBrowsActivity.this, perms);
                if (!flag) {
                    EasyPermissionUtils.requireSomePermission(FileBrowsActivity.this,"获取手机读写权限",1,perms);
                } else {
                    if (savePath == null) {
                        openFile.setText("");
                        fileDownload.setVisibility(View.VISIBLE);
                        DownloadUtil.get().download(FileBrowsActivity.this, Address.FILE_URL + fileEntity.getFileUrl(), "hecong/chatfile", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(String savePaths) {
                                savePath = savePaths;
                                runOnUiThread(() -> {
                                    fileDownload.setVisibility(View.GONE);
                                    openFile.setText(getResources().getString(R.string.open_down));
                                });
                            }

                            @Override
                            public void onDownloading(int progress) {

                            }

                            @Override
                            public void onDownloadFailed() {

                            }
                        });

                    } else {
                        openFileReader(FileBrowsActivity.this, savePath + "/" + fileEntity.getFileUrl());
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.background)
                .statusBarDarkFont(true)
                .titleBar(R.id.file_brows_include)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_file_brows;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }
    public static void show(Context context, FileEntity fileEntity) {
        Intent intent = new Intent(context, FileBrowsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("_fileEntity", fileEntity);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void openFileReader(Context context, String pathName)
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("local", "true");
        JSONObject Object = new JSONObject();
        try
        {
            Object.put("pkgName",context.getApplicationContext().getPackageName());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        params.put("menuData",Object.toString());
        QbSdk.getMiniQBVersion(context);
        int ret = QbSdk.openFileReader(context, pathName, params, this);

    }

    @Override
    public void onCoreInitFinished() {

//        ToastUtils.showShort("onCoreInitFinished");
    }

    @Override
    public void onViewInitFinished(boolean b) {
//        ToastUtils.showShort("onCoreInitFinished:"+b);
    }

    @Override
    public void onReceiveValue(String s) {
//        ToastUtils.showShort("onReceiveValue:"+s);
    }
}
