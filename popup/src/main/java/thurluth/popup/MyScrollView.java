package thurluth.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Nathan on 27/09/2017.
 **/

public class MyScrollView extends ScrollView
{

    private boolean scrollable = true;

    public void setScrollable(boolean scrollable)
    {
        this.scrollable = scrollable;
    }

    public MyScrollView(Context context)
    {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (scrollable)
                    return super.onTouchEvent(ev);
                return scrollable; // scrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return scrollable && super.onInterceptTouchEvent(ev);
    }
}
