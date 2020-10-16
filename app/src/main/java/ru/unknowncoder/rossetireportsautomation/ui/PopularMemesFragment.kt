package ru.unknowncoder.rossetireportsautomation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_popular_memes.*
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.model.NetworkService
import ru.unknowncoder.rossetireportsautomation.model.response.MemesResponseBody
import ru.unknowncoder.rossetireportsautomation.ui.adapter.MemeListAdapter

class PopularMemesFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return PopularMemesFragment()
        }
    }

    private val adapter = MemeListAdapter()
    lateinit var fragmentView : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(
            R.layout.fragment_popular_memes, container,
            false
        )
        initRecyclerView()
        swipeInit()
        getMemes()
        return fragmentView
    }

    private fun getMemes() {
        setLoading(true)
        NetworkService.getMemes({
            showMemes(it)
            setLoading(false)
            setStub(false)
        }, {
            showError()
            setLoading(false)
            setStub(true)
        })
    }

    private fun initRecyclerView() {
        val recyclerView = fragmentView.findViewById<RecyclerView>(R.id.memes_rv)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }


    private fun showMemes(memes: List<MemesResponseBody>) {
        adapter.setData(memes)
    }

    private fun showError() {
        val snack = Snackbar.make(
            popular_meme_layout,
            R.string.popularMemesSnackBarErrorText,
            Snackbar.LENGTH_LONG
        )
        snack.view.setBackgroundColor(resources.getColor(R.color.colorError))
        snack.show()
    }

    private fun setLoading(state: Boolean) {
        val progressBar = fragmentView.findViewById<ProgressBar>(R.id.progressBar)

        if (state)
            progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    private fun setStub(state: Boolean) {
        val textStub = fragmentView.findViewById<TextView>(R.id.textStub)
        if (state)
            textStub.visibility = View.VISIBLE
        else textStub.visibility = View.GONE
    }

    private fun swipeInit() {
        val swipeRefresh = fragmentView.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefresh.setOnRefreshListener {
            val runnable = Runnable {
                getMemes()
                swipeRefresh.isRefreshing = false
            }

            // Execute the task after specified time
            Handler().postDelayed(
                runnable, 1000.toLong()
            )
        }

        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

}