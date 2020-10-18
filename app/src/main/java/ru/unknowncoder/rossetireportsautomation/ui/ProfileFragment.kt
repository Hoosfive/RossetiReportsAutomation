package ru.unknowncoder.rossetireportsautomation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.domain.UserBody
import ru.unknowncoder.rossetireportsautomation.model.Preferences

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setData()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun setData() {
        lateinit var data : UserBody
        context?.let { data = Preferences.getUserInfoPrefs(it) }
        userNameTV.text = data.patronymic + " " + data.firstName + " " +data.lastName
        userPointTV.text = data.point
    }
}