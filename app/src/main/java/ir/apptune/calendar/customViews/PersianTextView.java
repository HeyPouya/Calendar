package ir.apptune.calendar.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import ir.apptune.calendar.PersianNumberFormatHelper;

/**
 * A custom TextView which shows texts in Bnazanin font.
 */

public class PersianTextView extends android.support.v7.widget.AppCompatTextView {

    public PersianTextView(Context context) {
        super(context);
    }

    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "BNazanin.ttf"));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = PersianNumberFormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
