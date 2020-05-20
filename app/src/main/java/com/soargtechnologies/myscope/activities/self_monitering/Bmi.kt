package com.soargtechnologies.myscope.activities.self_monitering

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.security.ProviderInstaller
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.services.Self_monitoring_service
import com.soargtechnologies.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*
import javax.net.ssl.SSLContext

class Bmi_self : BaseActivity() {

    var boolean: Boolean? = true
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null
    internal lateinit var myCalendar: Calendar
    var currentAnimator: Animator? = null

    var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        activitiesToolbar()
        header!!.text = "BMI"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)

      //  showLongToast(mobile_no.toString())

        myCalendar = Calendar.getInstance()
        val date= DatePickerDialog.OnDateSetListener{ view, year, monthofyear, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthofyear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            date_of_bmi.setText(date1)
        }
        date_of_bmi.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePicker, date ,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn_bmi.setOnClickListener {
            assignValuestoVariable()

        }

        bmi1.setOnClickListener {

            bmicalculator(weight_bmi, height_bmi , bmi1)
        }


        val thumb1View: View = findViewById(R.id.bmi_image)
        thumb1View.setOnClickListener({
            zoomImageFromThumb(thumb1View, R.drawable.bmi_chart)
            date1.visibility = View.GONE
            weight1.visibility = View.GONE
            height1.visibility = View.GONE
            bmi11.visibility = View.GONE
            note1.visibility = View.GONE
            btn_bmi.visibility = View.GONE

        })
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.

        currentAnimator?.cancel()


        val expandedImageView: ImageView = findViewById(R.id.expanded_image)
        expandedImageView.setImageResource(imageResId)


        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()


        thumbView.getGlobalVisibleRect(startBoundsInt)
        findViewById<View>(R.id.container1)
                .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)


        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {

            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {

            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }


        thumbView.alpha = 0f

        expandedImageView.visibility = View.VISIBLE


        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f


        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }


        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()


            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        height1.visibility = View.VISIBLE
                        weight1.visibility = View.VISIBLE
                        bmi11.visibility = View.VISIBLE
                        date1.visibility = View.VISIBLE
                        note1.visibility = View.VISIBLE
                        btn_bmi.visibility = View.VISIBLE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE

                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

    private fun assignValuestoVariable() {

        val date_of_Bmi = date_of_bmi.text.toString()
        val Weight = weight_bmi.text.toString()
        val Height = height_bmi.text.toString()
        val Bmi = bmi1.text.toString()

        //  validateInput(date_of_bmi, date_of_Bmi)
        validateInput( weight_bmi, Weight)
        validateInput(height_bmi , Height)

        if ((Bmi != "") && (Weight != "") && (Height != "") && (date_of_Bmi != "") ) {
  //          showLongToast("save the details")
            sucess()

        } else {

            showLongSnackBar("Please fill the required fields")

        }
    }

    private fun sucess() {



        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(getApplicationContext());
            var sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        }

        catch (e: Exception) {
            e.printStackTrace();
        }



        val newBmi = Self_dataClass()
        newBmi.date_of_bmi = date_of_bmi!!.text.toString().trim()
        newBmi.weight = weight_bmi!!.text.toString().trim()
        newBmi.height = height_bmi!!.text.toString().trim()
        newBmi.bmi = bmi1!!.text.toString().trim()
        newBmi.bmi_notes = notes_bmi!!.text.toString().trim()
        newBmi.bmi_save_on=datesetvalue()
        newBmi.mobile_no = mobile_no!!



        val glucoseService = ServiceBuilder.buildService(Self_monitoring_service::class.java)

        val requestCall = glucoseService.addBmi(newBmi)

        requestCall.enqueue(object : Callback<Self_dataClass> {

            override fun onResponse(call: Call<Self_dataClass>, resp: Response<Self_dataClass>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it
     //               Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
   //                 Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Self_dataClass>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
    //            Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
