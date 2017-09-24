package com.example.eagleweb.kotlinonedemo

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ksyun.media.shortvideo.kit.KSYRecordKit
import com.ksyun.media.streamer.capture.CameraCapture
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt
import com.ksyun.media.streamer.kit.StreamerConstants
import java.io.File

class RecordActivity : Activity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start -> {
                toast("开始录制")
                kit.startRecord(fileName)
            }
            R.id.btn_stop -> {
                toast("停止录制")
                kit.stopRecord()
            }
        }

    }

    private var filePath: String = Environment.getExternalStorageDirectory().absolutePath + File.separator + "kotlinDemo"
    private var fileName: String = Environment.getExternalStorageDirectory().absolutePath + File.separator + "kotlinDemo" + File.separator + System.currentTimeMillis() + ".mp4"

    private lateinit var kit: KSYRecordKit
//        get() {
//            val kit = KSYRecordKit(this)
//            return kit
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val btnStart = findViewById(R.id.btn_start) as Button
        val btn_stop = findViewById(R.id.btn_stop) as Button
        val gl_surface = findViewById(R.id.gl_surface) as GLSurfaceView
        btnStart.text = "开始录制"
        btn_stop.text = "停止录制"
        btnStart.setOnClickListener(this)
        btn_stop.setOnClickListener(this)

        kit = KSYRecordKit(this)
        kit.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE)
        kit.setDisplayPreview(gl_surface)
        kit.cameraFacing = CameraCapture.FACING_FRONT
        kit.imgTexFilterMgt.setFilter(kit.glRender, ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO)

        kit.startCameraPreview()
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
//        val fileName = fileName
//        kit.startRecord(fileName)

    }

    private fun showToast(): (View) -> Unit = {

        Toast.makeText(this, "开始录制", Toast.LENGTH_SHORT).show()
    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

//    private fun toS(s: String): String {
//        return ""
//    }
//
//
//    fun toS(s: String) {}

    override fun onDestroy() {
        super.onDestroy()
        kit.stopCameraPreview()
//            kit!!.release()

    }
}