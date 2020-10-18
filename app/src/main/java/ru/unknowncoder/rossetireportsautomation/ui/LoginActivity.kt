package ru.unknowncoder.rossetireportsautomation.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.domain.LoginDataBody
import ru.unknowncoder.rossetireportsautomation.domain.UserBody
import ru.unknowncoder.rossetireportsautomation.model.Preferences


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TEXT_PASS = InputType.TYPE_TEXT_VARIATION_PASSWORD + 1
        private const val TEXT_PASS_VISIBLE = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD + 1
        private const val MIN_PASS_LENGTH = 8
    }

    private lateinit var auth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passLine.inputType =
            TEXT_PASS // костыль для нормального отображения скрытого пароля при вводе
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        firebaseUser = auth.currentUser
    }

    private fun initListeners() {
        passLayout.endIconImageButton.setOnClickListener {
            togglePasswordVisibility()
        }
        loginBtn.setOnClickListener {
            if (validateFields()) {
                authUser()
            }
            /*private var firebaseUser: FirebaseUser? = null
            private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

            fun auth(email: String, password: String): FirebaseUser? {
                firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUser = task.result?.user
                        }
                    }*/
        }
        passLine.setOnFocusChangeListener { _, _ ->
            setPassHelperTextVisibility()
        }
        passLayout.setSimpleTextChangeWatcher { _, _ ->
            setPassHelperTextVisibility()
        }
    }

    private fun authUser() {
        setLoading(true)
        auth
            .signInWithEmailAndPassword(
                loginLine.text.toString(),
                passLine.text.toString()
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUser = task.result?.user
                    saveData(LoginDataBody(loginLine.text.toString(), firebaseUser?.uid.toString()))
                } else if (task.isCanceled) {
                    showError()
                    setLoading(true)
                }
            }
    }

    private fun togglePasswordVisibility() {
        val selection = passLine.selectionEnd
        if (passLine.inputType == TEXT_PASS) {
            passLine.inputType = TEXT_PASS_VISIBLE
            passLine.setSelection(selection)
            passLayout.endIconImageButton.setImageResource(R.drawable.ic_eye_off)
        } else {
            passLine.inputType = TEXT_PASS
            passLine.setSelection(selection)
            passLayout.endIconImageButton.setImageResource(R.drawable.ic_eye)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        loginBtn.isEnabled = !isLoading
        if (isLoading) {
            loginBtn.text = ""
            progressBar.visibility = View.VISIBLE
        } else {
            loginBtn.text = resources.getString(R.string.loginBtn)
            progressBar.visibility = View.GONE
        }
    }

    private fun validateFields(): Boolean {
        var validated = true
        if (loginLine.length() == 0) {
            loginLayout.setError(resources.getString(R.string.emptyFieldsError), false)
            validated = false
        }
        if (passLine.length() == 0) {
            passLayout.setError(resources.getString(R.string.emptyFieldsError), false)
            validated = false
        }
        if (passLine.length() < MIN_PASS_LENGTH)
            validated = false
        return validated
    }

    private fun setPassHelperTextVisibility() {
        if (passLine.length() < MIN_PASS_LENGTH)
            passLayout.helperText = resources.getString(
                R.string.passHelperText,
                MIN_PASS_LENGTH
            )
        else passLayout.helperText = " "
    }

    private fun saveData(loginDataBody: LoginDataBody) {
        Preferences.editUserLoginDataPref(
            baseContext,
            loginDataBody
        )
        // Create a reference to the cities collection
        val db = FirebaseFirestore.getInstance()
        db.collection("electritians").whereEqualTo("user_token", loginDataBody.uid)
            .get()
            .addOnSuccessListener { documents ->
                Preferences.editUserInfoPrefs(
                    baseContext, UserBody(
                        documents.first().getString("name").toString(),
                        documents.first().getString("surname").toString(),
                        documents.first().getString("paronymic").toString(),
                        documents.first().getString("point").toString()
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
            .addOnFailureListener {
                showError()
            }
// Create a query against the collection.

        setLoading(false)
    }

    private fun showError() {
        val snack = Snackbar.make(
            loginBtnLayout,
            R.string.loginSnackBarErrorText,
            Snackbar.LENGTH_LONG
        )
        snack.view.setBackgroundColor(resources.getColor(R.color.colorError))
        snack.show()
        setLoading(false)
    }
}
