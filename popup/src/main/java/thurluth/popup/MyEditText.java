package thurluth.popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import static thurluth.popup.R.drawable.edittext;

/**
 * Created by Nathan on 02/10/2017.
 **/

public class MyEditText extends android.support.v7.widget.AppCompatEditText
{
    TextPaint paint;
    TagDrawable left;
    LinearLayout focusNothing;
    private boolean toFocusNothing = true;
    String prefix;
    ViewGroup parentLayout;
    int viewPartRef;
    int color;
    MyEditText editText = this;

    Rect line0bounds = new Rect();
    int mLine0Baseline;

    public MyEditText(final Context context)
    {
        super(context);

        createFocusNothing(context);
        prefix = "";
        this.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if (i == EditorInfo.IME_ACTION_DONE)
                {
                    focusNothing.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                }
                return false;
            }
        });
        setBackgroundResource(edittext);
        setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                LayerDrawable layers = (LayerDrawable) ContextCompat.getDrawable(context, edittext);
                GradientDrawable shape = (GradientDrawable) (layers.findDrawableByLayerId(R.id.edittext_line));
                if (b)
                {
                    shape.setStroke(4, Color.parseColor("#60C5FF"));
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
                else
                {
                    shape.setStroke(4, Color.GRAY);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                setBackground(layers);
            }
        });

        left = new TagDrawable();

        paint = getPaint();

        if (prefix.length() != 0)
            left.setText(prefix + " ");
        else
            left.setText(prefix);
        setCompoundDrawablesRelative(left, null, null, null);
        setPadding(10, 10, 10, 10);
        this.color = getCurrentTextColor();
    }

    public MyEditText(final Context context, AttributeSet attrs)
    {
        super(context, attrs);

        int cursorColor;
        int handlesColor;

        createFocusNothing(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyEditText, 0, 0);
        prefix = a.getString(R.styleable.MyEditText_prefix);
        viewPartRef = a.getResourceId(R.styleable.MyEditText_parentLayout, -1);
        cursorColor = a.getColor(R.styleable.MyEditText_cursorColor, -1);
        handlesColor = a.getColor(R.styleable.MyEditText_handlesColor, -1);
        if (prefix == null)
            prefix = "";
        if (cursorColor != -1)
            setCursorColor(cursorColor);
        if (handlesColor != -1)
            setHandlesColors(handlesColor);
        this.setOnEditorActionListener(new OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if (i == EditorInfo.IME_ACTION_DONE)
                {
                    focusNothing.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                }
                return false;
            }
        });
        setBackgroundResource(edittext);
        setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                LayerDrawable layers = (LayerDrawable) ContextCompat.getDrawable(context, edittext);
                GradientDrawable shape = (GradientDrawable) (layers.findDrawableByLayerId(R.id.edittext_line));
                if (b)
                {
                    shape.setStroke(4, Color.parseColor("#60C5FF"));
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
                else
                {
                    shape.setStroke(4, Color.GRAY);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                setBackground(layers);
            }
        });
        a.recycle();
        setPadding(getPaddingLeft() / 2 + 10, getPaddingTop() / 2, getPaddingRight() / 2, getPaddingBottom() / 2);

        left = new TagDrawable();

        paint = getPaint();

        if (prefix.length() != 0)
            left.setText(prefix + " ");
        else
            left.setText(prefix);
        setCompoundDrawablesRelative(left, null, null, null);
        this.color = getCurrentTextColor();
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
        }
        catch (Exception ignored)
        {
        }
    }

    public void setHandlesColors(int color)
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setToFocusNothing(boolean focus)
    {
        this.toFocusNothing = focus;
    }

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

    @Override
    public void setTextColor(int color)
    {
        super.setTextColor(color);
        this.color = color;
    }

    @Override
    public void setTypeface(Typeface typeface)
    {
        super.setTypeface(typeface);
        if (paint != null)
        {
            paint.setTypeface(typeface);
        }

        postInvalidate();
    }

    public void setPrefix(String prefix)
    {
        if (prefix.length() != 0)
            left.setText(prefix + " ");
        else
            left.setText(prefix);
        setCompoundDrawablesRelative(left, null, null, null);
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        mLine0Baseline = getLineBounds(0, line0bounds);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if (viewPartRef != -1)
        {
            parentLayout = (ViewGroup) ((View) this.getParent()).findViewById(viewPartRef);
            if (parentLayout != null)
            {
                parentLayout.addView(focusNothing);
                parentLayout.setFocusableInTouchMode(true);
                parentLayout.setFocusable(true);
            }
            if (toFocusNothing)
                focusNothing.requestFocus();
        }
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

    private class TagDrawable extends Drawable
    {

        public void setText(String s)
        {
            prefix = s;

            setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());

            invalidateSelf();
        }

        @Override
        public void draw(@NonNull Canvas canvas)
        {
            if (editText.isEnabled())
                paint.setColor(color);
            else
                paint.setColor(Color.GRAY);
            canvas.drawText(prefix, 0, paint.getTextSize() - 5, paint);
        }

        @Override
        public void setAlpha(int i)
        {
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter)
        {
        }

        @Override
        public int getOpacity()
        {
            return PixelFormat.OPAQUE;
        }

        @Override
        public int getIntrinsicHeight()
        {
            return (int) paint.getTextSize();
        }

        @Override
        public int getIntrinsicWidth()
        {
            return (int) paint.measureText(prefix);
        }
    }

}
