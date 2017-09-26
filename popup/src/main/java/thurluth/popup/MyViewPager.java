package thurluth.popup;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Nathan on 14/09/2017.
 **/

public class MyViewPager extends ViewPager
{
    private boolean pageable;

    public MyViewPager(Context context)
    {
        super(context);
        pageable = true;
    }

    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        pageable = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (this.pageable)
        {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return this.pageable && super.onInterceptTouchEvent(event);

    }

    public void setPageable(boolean pageable)
    {
        this.pageable = pageable;
    }

}
