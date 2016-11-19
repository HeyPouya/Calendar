package ir.apptune.calendar;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.icu.text.NumberFormat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Pouya on 19/11/2016.
 */

public class PersianTextView extends TextView {

    public PersianTextView(Context context) {
        super(context);
    }
    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface nabi = Typeface.createFromAsset(context.getAssets(), "BNazanin.ttf");
        this.setTypeface(nabi);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = PersianNumberFormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
