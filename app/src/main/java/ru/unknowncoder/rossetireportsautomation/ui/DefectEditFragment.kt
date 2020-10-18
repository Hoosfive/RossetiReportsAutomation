package ru.unknowncoder.rossetireportsautomation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_defect_list.view.*
import ru.unknowncoder.rossetireportsautomation.R


class DefectEditFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return DefectEditFragment()
        }
    }

    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        fragmentView = inflater.inflate(R.layout.fragment_defect_list, container, false)

        fragmentView.qrScanBtn.setOnClickListener {
            startActivity(Intent(context, QRCodeScanner::class.java))

        }
        return fragmentView

    }
}