package ir.project.sinadalvand.handleprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener ,SeekBar.OnSeekBarChangeListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
        SizeseekBar.setOnSeekBarChangeListener(this)
    }


    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        handleProgressbar.setHandleSize(p1.toFloat())
        handleSize.text = "${p1}dp"
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            startButton->{
                handleProgressbar.start()
            }

            stopButton ->{
                handleProgressbar.stop()
            }
        }
    }
}
