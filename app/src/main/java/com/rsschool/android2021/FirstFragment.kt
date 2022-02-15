package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.IllegalArgumentException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValueEditText: EditText? = null
    private var maxValueEditText: EditText? = null
    private var listener: FirstFragmentActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FirstFragmentActionListener
        Log.i("TAG", "FirstFragment onAttach " + this.id + " " + this.tag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValueEditText = view.findViewById(R.id.min_value)
        maxValueEditText = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = resources.getString(R.string.previous_result, result)

        generateButton?.setOnClickListener {
            try {
                val minValue: Int = minValueEditText?.text.toString().toInt()
                val maxValue: Int = maxValueEditText?.text.toString().toInt()
                if (minValue > maxValue) {
                    throw IllegalArgumentException("min > max")
                }
                listener?.goToSecondFragmentByGenerateButton(minValue, maxValue)
            } catch (e: Exception) {
                Toast.makeText(context, R.string.something_going_wrong, Toast.LENGTH_SHORT).show()
                minValueEditText?.text?.clear()
                maxValueEditText?.text?.clear()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface FirstFragmentActionListener {
        fun goToSecondFragmentByGenerateButton(min: Int, max: Int)
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("TAG", "FirstFragment onDestroy" + this.id + " " + this.tag)
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("TAG", "FirstFragment onDetach" + this.id + " " + this.tag)
    }
}