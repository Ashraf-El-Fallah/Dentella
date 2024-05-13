//package com.af.dentalla.utils
//
//import android.content.Context
//import android.net.Uri
//import com.af.dentalla.utils.FileUtils.getFile
//import okhttp3.RequestBody
//import retrofit2.http.Part
//
//
//object MultiPartUtil {
//    fun fileToMultiPart(context: Context, fileUri: Uri?, partName: String?): Part {
//        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
//        // use the FileUtils to get the actual file by uri
//        val file = getFile(context, fileUri)
//
//        //Logger.i("FileUtils", file.getName());
//        //Logger.i("FileUtils", file.getPath());
//        // create RequestBody instance from file
//        val requestFile: RequestBody = RequestBody.create(
//            file,
//            parse.parse(context.contentResolver.getType(fileUri!!))
//        )
//
//        // MultipartBody.Part is used to send also the actual file partName
//        return createFormData.createFormData(partName, file!!.name, requestFile)
//    }
//
//    /**
//     * @param context    your activity context
//     * @param imagesList pass list of images URIs
//     * @return List<MultipartBody.Part> of your (list of images URIs)
//     * or null if the list you passed is empty
//    </MultipartBody.Part> */
//    fun filesToMultiPart(context: Context, imagesList: List<Uri?>): List<Part> {
//        val partList: MutableList<Part> = ArrayList<Part>()
//        if (imagesList.size != 0) {
//            for (image in imagesList) {
//                partList.add(fileToMultiPart(context, image, "images[]"))
//            }
//        }
//        return partList
//    }
//}
