package thurluth.popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nathan on 08/09/2017.
 **/

public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    ViewGroup parentLayout;
    int viewPartRef;
    LinearLayout focusNothing;

    private String mPrefix;
    private Rect mPrefixRect = new Rect();

    private void createFocusNothing(Context context) {
        focusNothing = new LinearLayout(context);
        focusNothing.setFocusable(true);
        focusNothing.setFocusableInTouchMode(true);
    }

    public LinearLayout getFocusNothing() {
        return focusNothing;
    }

    public void setPrefix(final String prefix) {
        mPrefix = prefix;
    }

    public MyEditText(final Context context) {
        super(context);
        mPrefix = "";
        this.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (i == EditorInfo.IME_ACTION_DONE)) {
                    focusNothing.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return false;
            }
        });
        createFocusNothing(context);
    }

    public MyEditText(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        createFocusNothing(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MyEditText, 0, 0);
        mPrefix = a.getString(R.styleable.MyEditText_prefix);
        viewPartRef = a.getResourceId(R.styleable.MyEditText_parentLayout, -1);
        if (mPrefix == null)
            mPrefix = "";
        this.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (i == EditorInfo.IME_ACTION_DONE)) {
                    focusNothing.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return false;
            }
        });
        a.recycle();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            focusNothing.requestFocus();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
        mPrefixRect.right += getPaint().measureText("  "); // add some offset
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (viewPartRef != -1) {
            parentLayout = (ViewGroup) ((View) this.getParent()).findViewById(viewPartRef);
            if (parentLayout != null)
                parentLayout.addView(focusNothing);
            focusNothing.requestFocus();
        }
    }
}
