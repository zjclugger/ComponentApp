package com.zjclugger.component.qr;

import android.content.Context;
import android.graphics.Bitmap;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zjclugger.router.ARouterConstants;
import com.zjclugger.router.service.IQRCodeService;
import com.zjclugger.qrcode.encode.QRCodeGenerator;

/**
 * 二维码生成服务
 */
@Route(path = ARouterConstants.Service.COM_QR_CODE)
public class QRCodeService implements IQRCodeService {
    Context mContext;
    Builder builder;

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public Builder builderParameter() {
        builder = new Builder();
        return builder;
    }

    @Override
    public Bitmap createQRCodeImage(String QRString) {
        if (builder == null) {
            builder = new Builder();
        }
        return QRCodeGenerator.create(QRString, builder.getWidth(), builder.getHeight(),
                builder.getCharacterSet(), builder.getErrorCorrectionLevel(),
                String.valueOf(builder.getMargin()), builder.getLineColor(),
                builder.getBackgroundColor(), builder.getLogoBitmap(), builder.getLogoPercent(),
                null);
    }
}