package com.zz.supervision.print;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyPrintPdfAdapter extends PrintDocumentAdapter {
//    private String mFilePath;

    private String fileBaseString;

//    public MyPrintPdfAdapter(String file) {
//        this.mFilePath = file;
//    }

    public MyPrintPdfAdapter(String fileBase64Str){
        this.fileBaseString = fileBase64Str;
    }

    // 当用户更改打印设置导致打印结果改变时调用，如更改纸张尺寸，纸张方向等；
    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal,
                         LayoutResultCallback callback, Bundle extras) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }
        PrintDocumentInfo info = new PrintDocumentInfo.Builder(getJobName())
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build();
        callback.onLayoutFinished(info, true);
    }

    // 当将要打印的结果写入到文件中时调用，该方法在每次onLayout（）调用后会调用一次或多次；
    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal,
                        WriteResultCallback callback) {
        InputStream input = null;
        OutputStream output = null;

        try {
            byte[] fileByte = Base64.decode(fileBaseString, Base64.DEFAULT);
            input = new ByteArrayInputStream(fileByte);
            output = new FileOutputStream(destination.getFileDescriptor());

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

        } catch (FileNotFoundException e) {
            // Catch exception
            e.printStackTrace();
        } catch (Exception e) {
            // Catch exception
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return
     */
    private String getJobName() {
        try {
//            String[] filePaths = mFilePath.split(File.separator);
//            return filePaths[filePaths.length - 1];
            return "receiptPrint";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(System.currentTimeMillis());
    }
}