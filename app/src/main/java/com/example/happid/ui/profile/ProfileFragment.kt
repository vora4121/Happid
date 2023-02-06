package com.example.happid.ui.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.happid.data.entities.Info
import com.example.happid.databinding.FragmentProfileBinding
import com.example.happid.utils.Resource
import com.example.happid.utils.alert
import com.example.happid.utils.showOptionAlert
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class ProfileFragment : Fragment() {

    val binding: FragmentProfileBinding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    // TODO HERE WE ARE CREATING INSTANCE OF VIEWMODEL USING HILT
    // val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            if (validate()) {
                alert("Your profile submitted successfully")
                binding.edtFirstName.setText("")
                binding.edtFirstName.setText("")
                binding.edtPhone.setText("")
                binding.edtLastName.setText("")
                binding.edtPostCode.setText("")
                // TODO From here we are able to submit our profile data on server but currently not added API so here is just API calling flow implemented
                //submitProfile()
            }
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    binding.imgProfile.setImageURI(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.imgProfile.setOnClickListener {
            if (validatePermission()) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

        }
    }

    private fun validatePermission(): Boolean {
        var isGranted = false
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    isGranted = true
                }
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                    isGranted = false
                    showOptionAlert(
                        "Permission Needed",
                        "Happid needs Storage permission for selecting profile image.",
                        "OKAY"
                    ) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                list: List<PermissionRequest>,
                permissionToken: PermissionToken
            ) {
                permissionToken.continuePermissionRequest()
                isGranted = false
            }
        }).check()
        return isGranted
    }

    private fun submitProfile() {

        val info = Info(
            binding.edtFirstName.text.toString(),
            binding.edtLastName.toString(),
            binding.edtPhone.toString(),
            ""
        )

      //  profileViewModel.start(info)

/*        profileViewModel.userResponse.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    // TODO here we'll get success response
                }

                Resource.Status.ERROR -> {
                    // TODO here getting errors
                }

                Resource.Status.LOADING -> {
                    // Loading state
                }
            }


        })*/


    }

    private fun validate(): Boolean {

        with(binding) {
            if (TextUtils.isEmpty(this.edtFirstName.text.toString())) {
                alert("Please enter First name")
                return false
            } else if (TextUtils.isEmpty(this.edtLastName.text.toString())) {
                alert("Please enter Last name")
                return false
            } else if (TextUtils.isEmpty(this.edtPhone.text.toString())) {
                alert("Please enter Phone")
                return false
            }
        }
        return true
    }


}