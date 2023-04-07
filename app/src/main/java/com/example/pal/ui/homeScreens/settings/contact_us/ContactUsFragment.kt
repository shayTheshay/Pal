package com.example.pal.ui.homeScreens.settings.contact_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pal.databinding.FragmentContactUsBinding
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class ContactUsFragment : Fragment() {
    private var binding: FragmentContactUsBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactUsBinding.inflate(inflater, container, false)

        val builder = AlertDialog.Builder(requireContext())

        // the back button
        binding.backIcon.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.submitBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Your message sent", Toast.LENGTH_SHORT).show()
        }

        binding.instagramButton.setOnClickListener {

            createDialog(
                "Are you sure you want to open Instagram?",
                "https://www.instagram.com/",
                "Instagram is not installed on this device",
                builder
            )
        }

        binding.snapchatButton.setOnClickListener {
            createDialog(
                "Are you sure you want to open Snapchat?",
                "https://www.snapchat.com/",
                "Snapchat is not installed on this device",
                builder
            )
        }

        binding.facebookButton.setOnClickListener {
            createDialog(
                "Are you sure you want to open Facebook?",
                "https://www.facebook.com/",
                "Facebook is not installed on this device",
                builder
            )
        }

        binding.ticktokButton.setOnClickListener {
            createDialog(
                "Are you sure you want to open TikTok?",
                "https://www.tiktok.com/",
                "TikTok is not installed on this device",
                builder
            )
        }

        return binding.root

    }

    fun createDialog(message: String, uri: String, error: String, builder: AlertDialog.Builder) {
        builder.setMessage(message)
            .setPositiveButton("Yes") { dialog, _ ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(uri)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Do nothing if the user presses "No"
                dialog.dismiss()
            }
            .show()
    }

}