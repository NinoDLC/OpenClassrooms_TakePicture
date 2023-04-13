# OpenClassrooms_TakePicture
Since Android 11, taking a picture with the camera is a terrible experience. Here's the final result that works (until Google breaks it again)

You have to take special attention to all of this: 
* 1/ Define a [provider](https://github.com/NinoDLC/OpenClassrooms_TakePicture/blob/master/app/src/main/AndroidManifest.xml#L28) for the file(s) that will get the photo data.
* 2/ Create a [`provider_paths.xml`](https://github.com/NinoDLC/OpenClassrooms_TakePicture/blob/master/app/src/main/res/xml/provider_paths.xml). I guess this is a security issue to have an "all matching" `.` but I'm not bothered anymore with Google's shenanigans. 
* 3/ Use either the new terrible [`Contracts` API](https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContracts) or the deprecated `onActivityResult` inside your [Activity/Fragment](https://github.com/NinoDLC/OpenClassrooms_TakePicture/blob/master/app/src/main/java/fr/delcey/takepictureintentdemo/MainActivity.kt#L17) to take (and receive) a full quality picture (and not the low-quality thumbnail at `data?.data`)
