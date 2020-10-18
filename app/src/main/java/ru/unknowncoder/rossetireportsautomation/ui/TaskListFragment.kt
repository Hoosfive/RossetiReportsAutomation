package ru.unknowncoder.rossetireportsautomation.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_task_list.*
import ru.unknowncoder.rossetireportsautomation.R
import ru.unknowncoder.rossetireportsautomation.ui.adapter.TaskListAdapter


class TaskListFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return TaskListFragment()
        }
    }

    private val adapter = TaskListAdapter()
    private lateinit var fragmentView: View


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
            R.layout.fragment_task_list, container,
            false
        )
        initRecyclerView()
        swipeInit()
        getTasks()
        return fragmentView
    }

    private fun getTasks() {
        setStub(false)
        setLoading(true)
        /*NetworkService.getMemes({
            showMemes(it)
            setLoading(false)
            setStub(false)
        }, {
            showError()
            setLoading(false)
            setStub(true)
        })*/
        val db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showTasks(task.result!!.documents)
                } else {
                    showError()
                }
            }

    }

    private fun initRecyclerView() {
        val recyclerView = fragmentView.findViewById<RecyclerView>(R.id.memes_rv)
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


    private fun showTasks(tasks: List<DocumentSnapshot>) {
        adapter.setData(tasks)
    }

    private fun showError() {
        val snack = Snackbar.make(
            popular_meme_layout,
            R.string.taskListSnackBarErrorText,
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
                getTasks()
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