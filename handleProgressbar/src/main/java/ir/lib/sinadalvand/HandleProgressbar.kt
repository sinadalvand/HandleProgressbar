package ir.lib.sinadalvand

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import ir.lib.sinadalvand.Interpolator.AwesomeInterpolator
import ir.lib.sinadalvand.awesomeprocessloader.R
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.TypedValue


class HandleProgressbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Animator.AnimatorListener,
    ValueAnimator.AnimatorUpdateListener {


    private var startDim = 0f
    private var endDim = 0f
    /**
     * default color for processor view
     */
    private val DEFAULT_COLOR = Color.BLACK

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var handleSize = -1

    private var color:Int = 0

    /**
     * Start offset for start of progressbar
     */
    private var start = 0.45f

    /**
     * end offset for start of progressbar
     */
    private var end = 0.55f

    private val rect = RectF()

    private var isFirstInit = true


    private val animatorSet = AnimatorSet()
    private val starter = ValueAnimator.ofFloat()
    private val ender = ValueAnimator.ofFloat()
    private var stop = false
    private var stopOnce = true

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.HandleProgressbar
        )
        stop = a.getInt(R.styleable.HandleProgressbar_progressbarState, 0) != 0
        handleSize = a.getDimensionPixelSize(R.styleable.HandleProgressbar_handleSize, 150)
        color = a.getColor(R.styleable.HandleProgressbar_progressbarColor, DEFAULT_COLOR)
        a.recycle()


        starter.apply {
            addUpdateListener(this@HandleProgressbar)
            interpolator =
                AwesomeInterpolator(AwesomeInterpolator.Ease.QUAD_IN)
        }

        ender.apply {
            addUpdateListener(this@HandleProgressbar)
            interpolator =
                AwesomeInterpolator(AwesomeInterpolator.Ease.LINEAR)
        }


        animatorSet.apply {
            addListener(this@HandleProgressbar)
            duration = 1000
            playTogether(starter, ender)
            cancel()
        }



        init()


    }

    private fun init(){
        paint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        start = ((measuredWidth / 2) - (handleSize / 2)) / measuredWidth.toFloat()
        end = ((measuredWidth / 2) + (handleSize / 2)) / measuredWidth.toFloat()

        startDim = start
        endDim = end



        starter.setFloatValues(startDim, 1f)
        ender.setFloatValues(endDim, 1f)


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)


        if (isFirstInit) {
            if (!stop) {
                animatorSet.duration = 2000
                starter.setFloatValues(0f, 1f)
                ender.setFloatValues(0f, 1f)
                animatorSet.start()
            }
            isFirstInit = false
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val h = height
        val w = width
        rect.right = w * end
        rect.left = w * start
        rect.top = 0f
        rect.bottom = h.toFloat()
        canvas?.drawRoundRect(rect, 10f, 10f, paint)

    }


    override fun onAnimationUpdate(animation: ValueAnimator?) {
        val value = animation?.animatedValue as Float
        if (animation == starter) {
            start = value
            invalidate()
        } else {
            end = value
            invalidate()
        }
    }

    override fun onAnimationEnd(animation: Animator?) {
        if (!stop) {
            starter.setFloatValues(0f, 1f)
            ender.setFloatValues(0f, 1f)
            animatorSet.duration = 2000
            animatorSet.start()
        } else {
            if (stopOnce) {
                theEnd()
                stopOnce = false
            }
        }
    }

    override fun onAnimationStart(animation: Animator?) {
    }

    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationCancel(animation: Animator?) {
    }

    private fun theEnd() {
        val startPer = if (start == 1f) 0f else start
        val endPer = if (end == 1f) 0f else end
        val time = 1000 * (startDim - startPer) / startDim
        starter.setFloatValues(startPer, startDim)
        ender.setFloatValues(endPer, endDim)
        animatorSet.duration = time.toLong()
        animatorSet.start()
    }


    fun start() {

        if (!stop) return

        stopOnce = true
        stop = false
        starter.setFloatValues(startDim, 1f)
        ender.setFloatValues(endDim, 1f)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    fun stop() {
        if (stop) return

        stop = true
        if (start <= startDim && end <= endDim) {
            stopOnce = true
            val starts = start
            val ends = end
            animatorSet.cancel()
            start = starts
            end = ends
            animatorSet.start()
        }

    }

    fun setHandleSize(dp:Float){
        handleSize = dp2px(dp)
        requestLayout()
    }

    fun setHandleColor(color:Int){
        this.color = color
        init()
    }


    override fun onSaveInstanceState(): Parcelable? {
        val parcel = super.onSaveInstanceState()
        val saver = SavedState(parcel!!)
        saver.isFirstTime = isFirstInit
        saver.stoped = stop
        return saver
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val saver = (state as SavedState)
        super.onRestoreInstanceState(state)
        isFirstInit = saver.isFirstTime
        stop = saver.stoped
    }

    private class SavedState : BaseSavedState {

        internal var stoped: Boolean = false
        internal var isFirstTime = false

        internal constructor(superState: Parcelable) : super(superState) {}

        private constructor(ins: Parcel) : super(ins) {
            //here we should get data from in
            stoped = ins.readInt() == 1
            isFirstTime = ins.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            // here we should write data
            super.writeToParcel(out, flags)
            out.writeInt(if (stoped) 1 else 0)
            out.writeInt(if (isFirstTime) 1 else 0)
        }

        companion object {

            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(ins: Parcel): SavedState {
                    return SavedState(ins)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }


    }

    private fun dp2px(dp: Float) = applyDimension(COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()


}