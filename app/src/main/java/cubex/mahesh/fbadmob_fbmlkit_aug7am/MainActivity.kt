package cubex.mahesh.fbadmob_fbmlkit_aug7am

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        loadAdd.setOnClickListener {
            val adRequest1 = AdRequest.Builder().build()
            var iadd = InterstitialAd(this@MainActivity)
            iadd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
            iadd.loadAd(adRequest1)
            iadd.adListener = object: AdListener( ){
                override fun onAdLoaded() {
                    iadd.show()
                }
            }

        }

        camera.setOnClickListener {
            var i = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(i,123)
        }


    } // onCreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bmp = data!!.extras.get("data") as Bitmap
        var vision_image = FirebaseVisionImage.fromBitmap(bmp)
        val textRecognizer = FirebaseVision.getInstance()
                .onDeviceTextRecognizer
        textRecognizer.processImage(vision_image).
                addOnSuccessListener {
                  var list =  it.textBlocks
                    var msg = " "
                    for(word in list){
                        msg = msg+ word.text.toString()
                    }
                    Toast.makeText(this@MainActivity,
                            msg,Toast.LENGTH_LONG).show()
                }.
                addOnFailureListener {
                    Toast.makeText(this@MainActivity,
                            "Fail to Recognize Text",
                            Toast.LENGTH_LONG).show()
                }

    }
}
