package com.fw.custom_view.draw_paint_shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.fw.custom_view.R;

import java.util.Objects;

/**
 * author : Fuwa
 * e-mail : fufan.zhou@androidmov.com
 * date   : 2021/1/7 15:18
 * desc   :
 * version: 1.0
 */
public class MyGradientView extends View {
    Bitmap bitmap;
    Paint paint;
    Rect rect;
    int bitWidth;
    int bitHeight;
    ShapeDrawable shapeDrawable;
    public MyGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = ((BitmapDrawable) (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.test)))).getBitmap();
        bitWidth = bitmap.getWidth();
        bitHeight = bitmap.getHeight();
        /**
         * 1 TileMode.CLAMP  最后一像素平铺
         * 2 Shader 着色器
         * 3 canvas.drawRect：绘制矩形
         * 4 canvas.drawCircle 绘制圆形
         *
         */
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint = new Paint();
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        rect = new Rect(0,0 ,1000, 1000);
        //另一种绘制
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
        shapeDrawable.setBounds(0, 0, bitWidth, bitWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        canvas.drawRect(rect, paint);
        canvas.drawCircle(bitWidth >> 1, bitHeight >> 1, bitHeight >> 1, paint);
        //另一种绘制
        shapeDrawable.draw(canvas);
    }
}
