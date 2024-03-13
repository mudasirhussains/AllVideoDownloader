package com.example.allvideodownloader.about

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.R
import com.example.allvideodownloader.databinding.FragmentAboutBinding
import com.example.allvideodownloader.utils.showToast

class AboutFragment : Fragment() {
    private lateinit var binding : FragmentAboutBinding
    companion object {
        fun newInstance(): AboutFragment {
            val args = Bundle()

            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.btnPrivacyPolicy.setOnClickListener {
            callDialog()
        }
    }

    private fun callDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.privacy_policy_dialog)
        val checkBox = dialog.findViewById<CheckBox>(R.id.checkBoxAgree)
        dialog.findViewById<Button>(R.id.btnDialogClose).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<Button>(R.id.btnDialogAgree).setOnClickListener {
            if (checkBox.isChecked){
                dialog.dismiss()
            }else{
                activity?.showToast(getString(R.string.accept_to_continue))
            }
        }
        dialog.show()

    }
}