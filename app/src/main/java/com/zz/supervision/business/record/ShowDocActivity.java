package com.zz.supervision.business.record;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sun.jna.platform.win32.WinNT;
import com.tencent.smtt.sdk.TbsReaderView;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.MonitorActivity;
import com.zz.supervision.business.inspenction.SuperviseResultActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.print.PrintUtil;
import com.zz.supervision.print.model.DeviceDTO;
import com.zz.supervision.utils.FileUtils;
import com.zz.supervision.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class ShowDocActivity extends MyBaseActivity implements TbsReaderView.ReaderCallback {


    private TbsReaderView mTbsReaderView;
    private Button mDownloadBtn;
    private TextView btnPrinter;
    private Toolbar toolbar;

    private DownloadManager mDownloadManager;
    private long mRequestId;
    private DownloadObserver mDownloadObserver;
    private String mFileUrl = "";
    private String mFileName;
    private ProgressBar pg;
    private TextView tv;
    private LinearLayout ll;
    private TextView doc_title;
    private TextView tv_print;
    private DeviceDTO deviceDTO;
    int isRead = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_show_doc;

    }


    private void displayFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", FileUtils.getLocalFile(context, mFileName).getPath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(FileUtils.parseFormat(mFileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
            btnPrinter.setVisibility(View.VISIBLE);
        }

    }

    private void startDownload() {
        mDownloadObserver = new DownloadObserver(new Handler());
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, mDownloadObserver);

        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mFileUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        try {
            mRequestId = mDownloadManager.enqueue(request);
        } catch (Exception e) {
            LogUtils.v(e.toString());

        }
    }

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mRequestId);
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载的字节数
                int currentBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //总需下载的字节数
                int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //状态所在的列索引
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                tv.setText(mFileName + "正在下载：" + currentBytes + "/" + totalBytes);
                int pro = (currentBytes / totalBytes) * 100;
                pg.setProgress(pro);
                if (DownloadManager.STATUS_SUCCESSFUL == status && ll.getVisibility() == View.VISIBLE) {
                    ll.setVisibility(View.GONE);
                    displayFile();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
        if (mDownloadObserver != null) {
            getContentResolver().unregisterContentObserver(mDownloadObserver);
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void initView() {

        PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
                getDocInfo();
            }

            @Override
            public void onDenied() {

            }
        });

        mFileUrl = getIntent().getStringExtra("url");
        isRead = getIntent().getIntExtra("read", 0);

        mTbsReaderView = new TbsReaderView(this, this);
        mDownloadBtn = (Button) findViewById(R.id.btn_download);
        btnPrinter = (TextView) findViewById(R.id.toolbar_subtitle);
        doc_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        pg = (ProgressBar) findViewById(R.id.pg);
        tv = (TextView) findViewById(R.id.tv);
        tv_print = (TextView) findViewById(R.id.tv_print);
        ll = (LinearLayout) findViewById(R.id.ll);
        String title = getIntent().getStringExtra("title");
        doc_title.setText(TextUtils.isEmpty(title) ? "文件" : title);
        RelativeLayout rootRl = (RelativeLayout) findViewById(R.id.rl_root);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (isRead == 1) {
            btnPrinter.setText("确定");
        } else {
            btnPrinter.setText("打印");
        }

        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRead == 1) {
                    startDownload();
                } else {
                    if (FileUtils.isLocalExist(context, mFileName)) {
                        ll.setVisibility(View.GONE);
                        displayFile();
                    } else {
                        startDownload();

                    }
                }
            }
        });
        tv_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setClass(ShowDocActivity.this, SelectPrintActivity.class);
                    startActivityForResult(intent, 1001);


            }
        });
        btnPrinter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (isRead==1){
                    completeData();
                }else {
                    String path = FileUtils.getLocalFile(context, mFileName).getPath();
                    if (TextUtils.isEmpty(path)) return;
                    try {
                        PrintUtil.printpdf(ShowDocActivity.this, FileUtils.getLocalFile(context, mFileName).getPath());
                    } catch (IOException e) {

                    }
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }


    private class DownloadObserver extends ContentObserver {

        private DownloadObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            queryDownloadStatus();
        }
    }

    void getDocInfo() {
        Map<String, Object> map = new HashMap<>();

        String id = getIntent().getStringExtra("id");
        int tinspectType = getIntent().getIntExtra("tinspectType", 0);
        if (tinspectType == 6 || tinspectType == 7) {
            tinspectType = 7;
        } else if (tinspectType == 8 || tinspectType == 9 || tinspectType == 10) {
            tinspectType = 6;
        } else if (tinspectType >= 11 && tinspectType <= 18) {
            tinspectType = 8;
        }  else if (tinspectType == 19 ) {
            tinspectType = 9;
        } else if (tinspectType == 100) {
            tinspectType = 8;
        }
        int tinspectSheetType = getIntent().getIntExtra("tinspectSheetType", 0);
        map.put("tinspectSheetType", tinspectSheetType);
        map.put("tinspectType", tinspectType);
        RxNetUtils.request(getApi(ApiService.class).getDocInfo(id, map), new RequestObserver<JsonT<String>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onSuccess(JsonT<String> jsonT) {
                mFileUrl = jsonT.getData();
                mFileName = FileUtils.parseName(mFileUrl);
                tv.setText(mFileName);
                if (isRead == 1) {
                    ll.setVisibility(View.VISIBLE);
                } else {
                    if (FileUtils.isLocalExist(context, mFileName)) {
                        ll.setVisibility(View.GONE);
                        displayFile();
                    } else {
                        ll.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            protected void onFail2(JsonT<String> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && data != null) {
            DeviceDTO deviceDTO = (DeviceDTO) data.getSerializableExtra("deviceDTO");
            this.deviceDTO = deviceDTO;
            tv_print.setText(deviceDTO.getPlayerName() + "-" + deviceDTO.getDeviceName() + "");
        }
    }
    void completeData() {
        String id = getIntent().getStringExtra("id");
        int type = getIntent().getIntExtra("type",0);
        RxNetUtils.request(getApi(ApiService.class).completeTzsbInspectionRecord(id), new RequestObserver<JsonT<SuperviseBean.ResposeBean>>(this) {
            @Override
            protected void onSuccess(JsonT<SuperviseBean.ResposeBean> jsonT) {
                if (jsonT.isSuccess()) {
                    startActivity(new Intent(ShowDocActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", jsonT.getData()).putExtra("type", type));
                    finish();
                }
            }

            @Override
            protected void onFail2(JsonT<SuperviseBean.ResposeBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
}