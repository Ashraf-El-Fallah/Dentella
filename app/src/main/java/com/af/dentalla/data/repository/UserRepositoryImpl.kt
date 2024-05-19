package com.af.dentalla.data.repository

import android.net.Uri
import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.ProfileInformationDto
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.DoctorPassword
import com.af.dentalla.data.remote.requests.DoctorProfileInformation
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStorePreferencesService: DataStorePreferencesService
) : UserRepository {
    private val accountType = AccountManager.accountType

    override fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>> {
        return flow {
            Log.d("LoginUser", "Start login user $loginUser")
            try {
                emit(NetWorkResponseState.Loading)
                val authenticateLoginResponse =
                    service.loginUser(accountType.toString().lowercase(), loginUser)
                if (authenticateLoginResponse.isSuccessful) {
                    if (authenticateLoginResponse != null) {
                        Log.d("LoginUser", "Login  Successfully $loginUser")
                        saveToken(authenticateLoginResponse.body()?.token)
                        emit(NetWorkResponseState.Success(Unit))
                    } else {
                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
                    }
                } else {
                    emit(NetWorkResponseState.Error(Exception("HTTP error ${authenticateLoginResponse.code()}")))
                }
            } catch (e: Exception) {
                Log.d("LoginUser", "Exception during login  ${e.message}", e)
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }
    }

    //
    override fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>> {
        return flow {
            Log.d("SignUpUser", "Start signing up user $signUpUser")
            emit(NetWorkResponseState.Loading)
            try {
                val authenticateSignUpResponse =
                    service.signUpUser(accountType.toString().lowercase(), signUpUser)
                emit(NetWorkResponseState.Success(Unit))
                Log.d("SignUpUser", "............ $signUpUser")
            } catch (e: Exception) {
                Log.d("SignUpUser", "Exception during sign up  ${e.message}")
                emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
            }
        }
    }

    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)

                val response = service.getAllDoctorsCards()
                emit(NetWorkResponseState.Success(response))
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    emit(NetWorkResponseState.Error(Exception("HTTP error ${response.code()}")))
//                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }


    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>> {
        return flow {

            try {
                emit(NetWorkResponseState.Loading)
                Log.d("Articles", "Start getting articles")
                val response = service.getAllArticles()
                emit(NetWorkResponseState.Success(response))
                Log.d("Articles", "Articles are getting successfully")

//                if (response.isSuccessful) {
//                    Log.d("Articles", "Articles got successfully")
//                    val data = response.body()
//                    if (data != null) {
//                        Log.d("Articles", "Articles got successfully")
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        Log.d("Articles", "Body is null")
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    Log.d("Articles", "HTTP error")
//                    emit(NetWorkResponseState.Error(Exception("Http error ${response.body()}")))
//                }
            } catch (e: Exception) {
                Log.d("Articles", "exception")
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                val response = service.searchAboutDoctorsByUniversity(university)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        emit(NetWorkResponseState.Success(data))
                    } else {
                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
                    }
                } else {
                    emit(NetWorkResponseState.Error(Exception("HTTp error ${response.body()}")))
                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileDto>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getDoctorProfile(cardId)
                Log.d("ProfileDetails", response.doctorName.toString())
                emit(NetWorkResponseState.Success(response))

//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        emit(NetWorkResponseState.Success(data))
//                    } else {
//                        emit(NetWorkResponseState.Error(Exception("Response body is null")))
//                    }
//                } else {
//                    emit(NetWorkResponseState.Error(Exception("HTTp error ${response.body()}")))
//                }
            } catch (e: Exception) {
                Log.d("ProfileDetails", e.message.toString())
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsDto>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getSpecialityCards(specialityId)
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun getAllPosts(): Flow<NetWorkResponseState<List<PostDtoItem>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val response = service.getAllPosts()
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun addArticle(article: Article): Flow<NetWorkResponseState<Unit>> {
        return flow {
            Log.d("ARTICLE", "Loading article")
            emit(NetWorkResponseState.Loading)
            try {
                Log.d("ARTICLE", "Start adding article")
                service.addArticle(article)
                emit(NetWorkResponseState.Success(Unit))
                Log.d("ARTICLE", "Finish adding article")
            } catch (e: Exception) {
                Log.d("ARTICLE", "can't add article")
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun addPost(post: Post): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                service.addPost(post)
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun addCard(card: Card): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                service.addCard(card)
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun returnDoctorProfileInformation(): Flow<NetWorkResponseState<ProfileInformationDto>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                val response = service.returnProfileInformation()
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun updateDoctorProfileInformation(doctorProfileInformation: DoctorProfileInformation): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                Log.d("Doctor Profile", ".......")
                service.updateDoctorProfile(
                    userName = createPartFromString(doctorProfileInformation.userName),
                    email = createPartFromString(doctorProfileInformation.email),
                    phoneNumber = createPartFromString(doctorProfileInformation.phoneNumber),
                    bio = createPartFromString(doctorProfileInformation.bio),
                    currentLevel = createPartFromString(doctorProfileInformation.currentLevel),
                    currentUniversity = createPartFromString(doctorProfileInformation.currentUniversity),
                    photo = doctorProfileInformation.photo
                )
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }


    private fun uriToMultipart(uri: Uri?): MultipartBody.Part? {
        return try {
            val file = File(uri?.path!!)
            val mimeType = getMimeType(file)
            val requestFile: RequestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("file", file.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getMimeType(file: File): String {
        val extension = file.extension.toLowerCase()
        return when (extension) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "txt" -> "text/plain"
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "xls" -> "application/vnd.ms-excel"
            "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            else -> "application/octet-stream" // default binary type
        }
    }

    //    private fun uriToMultipart(uri: Uri): MultipartBody.Part? {
//        try {
////            val contentResolver: ContentResolver = context?.contentResolver ?: return null
////            val mimeType = contentResolver.getType(uri) ?: return null
////
////            val inputStream = contentResolver.openInputStream(uri) ?: return null
////            val file = File(requireContext().cacheDir, "temp_file")
//
////            FileOutputStream(file).use { outputStream ->
////                inputStream.copyTo(outputStream)
////            }
//
//            val requestFile: RequestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
//            return MultipartBody.Part.createFormData("file", file.name, requestFile)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//    }

    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), descriptionString)
    }


    override fun changeDoctorPassword(doctorPassword: DoctorPassword): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                service.changeDoctorPassword(doctorPassword)
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun logout(): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                service.logoutFromAccount()
                dataStorePreferencesService.clearToken()
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    private suspend fun saveToken(token: String?) {
        dataStorePreferencesService.saveToken(token)
    }
}

