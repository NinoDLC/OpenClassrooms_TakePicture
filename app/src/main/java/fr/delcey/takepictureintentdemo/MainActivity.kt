package fr.delcey.takepictureintentdemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import fr.delcey.takepictureintentdemo.databinding.MainActivityBinding
import fr.delcey.takepictureintentdemo.utils.viewBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_CURRENT_PHOTO_URI = "KEY_CURRENT_PHOTO_URI"
    }

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    private var currentPhotoUri: Uri? = null

    // region CONTRACTS
    private val takePictureCallback = registerForActivityResult(ActivityResultContracts.TakePicture()) { successful ->
        if (successful) {
            Glide.with(binding.mainImageViewPhoto)
                .load(currentPhotoUri)
                .into(binding.mainImageViewPhoto)
        }
    }
    // endregion CONTRACTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        currentPhotoUri = savedInstanceState?.getString(KEY_CURRENT_PHOTO_URI)?.let { Uri.parse(it) }

        // region CLASSIC
        binding.mainButtonPicture.setOnClickListener {
            currentPhotoUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                File.createTempFile(
                    "JPEG_",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)

            startActivityForResult(intent, 0)
        }
        // endregion CLASSIC

        // region CONTRACTS
        binding.mainButtonPictureWithContracts.setOnClickListener {
            currentPhotoUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                File.createTempFile(
                    "JPEG_",
                    ".jpg",
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
            )

            takePictureCallback.launch(currentPhotoUri)
        }
        // endregion CONTRACTS
    }

    // region CLASSIC
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION") // ActivityResultContracts are equally bad since they can't be registered after onCreate...
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            Glide.with(binding.mainImageViewPhoto)
                .load(currentPhotoUri)
                .into(binding.mainImageViewPhoto)
        }
    }
    // endregion CLASSIC

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_CURRENT_PHOTO_URI, currentPhotoUri?.toString())
    }
}