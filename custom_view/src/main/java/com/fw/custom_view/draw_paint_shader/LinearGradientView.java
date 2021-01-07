package com.fw.custom_view.draw_paint_shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.fw.custom_view.R;

/**
 * author : Fuwa
 * e-mail : fufan.zhou@androidmov.com
 * date   : 2021/1/7 16:25
 * desc   :
 * version: 1.0
 */
public class LinearGradientView extends View {
    private Paint mPaint;
    private Bitmap mBitMap = null;
    private int mWidth;
    private int mHeight;
    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitMap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test)).getBitmap();
        mPaint = new Paint();
        //渐变
        LinearGradient linearGradient = new LinearGradient(0,0, 200, 200,mColors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        mWidth = mBitMap.getWidth();
        mHeight = mBitMap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);


        canvas.drawRect(0,0,200,200, mPaint);
    }
}
