package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var listener: SecondFragmentActionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as SecondFragmentActionListener
        Log.i("TAG", "SecondFragment onAttach" + this.id + " " + this.tag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            listener?.goToFirstFragmentByBackButton(result?.text.toString().toInt())
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            listener?.goToFirstFragmentByBackButton(result?.text.toString().toInt())
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min ..max).random()
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

    interface SecondFragmentActionListener {
        fun goToFirstFragmentByBackButton(previousNumber: Int)
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("TAG", "SecondFragment onDestroy" + this.id + " " + this.tag)
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("TAG", "SecondFragment onDetach" + this.id + " " + this.tag)
    }
}