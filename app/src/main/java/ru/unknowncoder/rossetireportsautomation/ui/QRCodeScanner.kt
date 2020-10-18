package ru.unknowncoder.rossetireportsautomation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import ru.unknowncoder.rossetireportsautomation.R

@SuppressLint("Registered")
class QRCodeScanner : AppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object {
        const val CAMERA_PERMISSION_REQUEST_ID: Int = 777
        const val CAMERA_PERM = Manifest.permission.CAMERA


    }

    private lateinit var qrCodeScanner: ZXingScannerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrscanner_layout)
        checkPermission()

    }

    private fun checkPermission() {
        val grant = ContextCompat.checkSelfPermission(this, CAMERA_PERM)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(CAMERA_PERM),
                CAMERA_PERMISSION_REQUEST_ID
            )
        } else {
            initCam()
        }
    }

    private fun initCam() {
        qrCodeScanner = findViewById(R.id.qrCodeScanner)
        setScannerProperties()
    }

    private fun setScannerProperties() {
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)

    }

    override fun onResume() {
        super.onResume()
        val grant = ContextCompat.checkSelfPermission(this, CAMERA_PERM)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            return
        }
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }

    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            Toast.makeText(this, p0.text, Toast.LENGTH_LONG).show()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_ID -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }
}