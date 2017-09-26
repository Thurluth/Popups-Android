package thurluth.popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Nathan on 08/09/2017.
 **/

public class MyEditText extends android.support.v7.widget.AppCompatEditText
{

    ViewGroup parentLayout;
    int viewPartRef;
    LinearLayout focusNothing;

    private boolean toFocusNothing = true;

    private String mPrefix;
    private Rect mPrefixRect = new Rect();

    private void createFocusNothing(Context context)
    {
        focusNothing = new LinearLayout(context);
        focusNothing.setFocusable(true);
        focusNothing.setFocusableInTouchMode(true);
    }

    public LinearLayout getFocusNothing()
    {
        return focusNothing;
    }

    public void setPrefix(final String prefix)
    {
        mPrefix = prefix;
    }

    public void setToFocusNothing(boolean focus)
    {
        this.toFocusNothing = focus;
    }

    public void setCursorColor(@ColorInt int color)
    {
        try
        {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(this);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(this);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored)
        {
        }
    }

    public void colorHandles(int color)
    {
        try
        {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            if (!editorField.isAccessible())
            {
                editorField.setAccessible(true);
            }

            Object editor = editorField.get(this);
            Class<?> editorClass = editor.getClass();

            String[] handleNames = {"mSelectHandleLeft", "mSelectHandleRight", "mSelectHandleCenter"};
            String[] resNames = {"mTextSelectHandleLeftRes", "mTextSelectHandleRightRes", "mTextSelectHandleRes"};

            for (int i = 0; i < handleNames.length; i++)
            {
                Field handleField = editorClass.getDeclaredField(handleNames[i]);
                if (!handleField.isAccessible())
                {
                    handleField.setAccessible(true);
                }

                Drawable handleDrawable = (Drawable) handleField.get(editor);

                if (handleDrawable == null)
                {
                    Field resField = TextView.class.getDeclaredField(resNames[i]);
                    if (!resField.isAccessible())
                    {
                        resField.setAccessible(true);
                    }
                    int resId = resField.getInt(this);
                    handleDrawable = this.getResources().getDrawable(resId, null);
                }

                if (handleDrawable != null)
                {
                    Drawable drawable = handleDrawable.mutate();
                    drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    handleField.set(editor, drawable);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public MyEditText(final Context context)
    {
        super(context);
        mPrefix = "";
        createFocusNothing(context);
        this.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if (keyEvent != null && i == EditorInfo.IME_ACTION_DONE)
                {
                    focusNothing.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    public MyEditText(final Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        createFocusNothing(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MyEditText, 0, 0);
        mPrefix = a.getString(R.styleable.MyEditText_prefix);
        viewPartRef = a.getResourceId(R.styleable.MyEditText_parentLayout, -1);
        if (mPrefix == null)
            mPrefix = "";
        this.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if (keyEvent != null && i == EditorInfo.IME_ACTION_DONE)
                {
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
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
        {
            focusNothing.requestFocus();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        getPaint().getTextBounds(mPrefix, 0, mPrefix.length(), mPrefixRect);
        if (!mPrefix.matches(""))
            mPrefixRect.right += getPaint().measureText("  "); // add some offset
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawText(mPrefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
    }

    @Override
    public int getCompoundPaddingLeft()
    {
        return super.getCompoundPaddingLeft() + mPrefixRect.width();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if (viewPartRef != -1)
        {
            parentLayout = (ViewGroup) ((View) this.getParent()).findViewById(viewPartRef);
            if (parentLayout != null)
                parentLayout.addView(focusNothing);
            if (toFocusNothing)
                focusNothing.requestFocus();
        }
    }
}
