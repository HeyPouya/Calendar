package ir.apptune.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A custom TextView which shows texts in Bnazanin font.
 */

public class PersianTextView extends TextView {

    public PersianTextView(Context context) {
        super(context);
    }

    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface Bnazanin = Typeface.createFromAsset(context.getAssets(), "BNazanin.ttf");
        this.setTypeface(Bnazanin);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = PersianNumberFormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
