package ir.apptune.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Pouya on 19/11/2016.
 */

public class PersianTextView extends TextView {

    public PersianTextView(Context context) {
        super(context);
    }
    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface BNazanin = Typeface.createFromAsset(context.getAssets(), "BNazanin.ttf");
        this.setTypeface(BNazanin);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = PersianNumberFormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
