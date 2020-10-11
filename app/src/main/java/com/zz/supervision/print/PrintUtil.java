package com.zz.supervision.print;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zz.lib.core.http.utils.ToastUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import androidx.annotation.RequiresApi;

public class PrintUtil {
    /**
     * 打印pdf文件方法
     *
     * @param activity
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void printpdf(Activity activity,String path) throws IOException {
        PrintManager printManager = (PrintManager) activity.getSystemService(Context.PRINT_SERVICE);
        MyPrintPdfAdapter myPrintAdapter = new MyPrintPdfAdapter(readStream(1,path));
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(PrintAttributes.COLOR_MODE_COLOR);
        printManager.print("test", myPrintAdapter, builder.build());
    }

    /**
     * 打印html文件方法
     *
     * @param activity
     * @throws IOException
     */
    public static void printWebView(Activity activity) throws IOException {
        WebView webView = new WebView(activity);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ToastUtils.showToast("完成");
            }
        });
        String htmlDocument = readStream(2,"");
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void createWebPrintJob(WebView webView, Activity activity) {
        PrintManager printManager = (PrintManager) activity.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = "webDocument";
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }

    /**
     * 读取本地文件
     * @param type
     * @return
     * @throws IOException
     */
    public static String readStream(int type,String path) throws IOException {
        String filePath = "/sdcard/1.html";
        if (type == 1) {
            filePath = path;
        }
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while (-1 != (len = fileInputStream.read(bytes))) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        byteArrayOutputStream.close();
        fileInputStream.close();
        byte[] resBytes = byteArrayOutputStream.toByteArray();
        if (type == 1) {
            return Base64.encodeToString(resBytes, 1);
        } else {
            return new String(resBytes, "UTF-8");
        }
    }

}