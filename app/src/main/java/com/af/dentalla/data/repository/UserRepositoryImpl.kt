package com.af.dentalla.data.repository

import android.util.Log
import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.local.DataStorePreferencesService
import com.af.dentalla.data.remote.api.ApiService
import com.af.dentalla.data.remote.dto.ArticleDto
import com.af.dentalla.data.remote.dto.CardsDto
import com.af.dentalla.data.remote.dto.DoctorProfileDto
import com.af.dentalla.data.remote.dto.PostDtoItem
import com.af.dentalla.data.remote.dto.UserProfileInformationDto
import com.af.dentalla.data.remote.requests.Article
import com.af.dentalla.data.remote.requests.Card
import com.af.dentalla.data.remote.requests.UserPasswords
import com.af.dentalla.data.remote.requests.UserProfileInformation
import com.af.dentalla.data.remote.requests.LoginUser
import com.af.dentalla.data.remote.requests.Post
import com.af.dentalla.data.remote.requests.SignUpUser
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.AccountManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dataStorePreferencesService: DataStorePreferencesService
) : UserRepository {
    private val accountType = AccountManager.accountType.toString().lowercase()

    override fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>> {
        return flow {
            try {
                emit(NetWorkResponseState.Loading)
                val authenticateLoginResponse =
                    service.loginUser(accountType, loginUser)
                if (authenticateLoginResponse.isSuccessful) {
                    saveToken(authenticateLoginResponse.body()?.token)
                    emit(NetWorkResponseState.Success(Unit))
                } else {
                    emit(NetWorkResponseState.Error(Exception("HTTP error ${authenticateLoginResponse.code()}")))
                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(Exception("Exception :${e.message}")))
            }
        }
    }

    override fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>> {
        return performRequest(
            request = { service.signUpUser(accountType, signUpUser) }
        )
    }

    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsDto>>> {
        return performRequest(
            request = { service.getAllDoctorsCards() }
        )
    }

    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticleDto>>> {
        return performRequest(
            request = { service.getAllArticles() }
        )
    }

    override fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsDto>>> {
        return performRequest(
            request = { service.searchAboutDoctorsByUniversity(university) }
        )
    }

    override fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileDto>> {
        return performRequest(
            request = { service.getDoctorProfile(cardId) }
        )
    }

    override fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsDto>>> {
        return performRequest(
            request = { service.getSpecialityCards(specialityId) }
        )
    }

    override fun getAllPosts(): Flow<NetWorkResponseState<List<PostDtoItem>>> {
        return performRequest(
            request = { service.getAllPosts() }
        )
    }

    override fun addArticle(article: Article): Flow<NetWorkResponseState<Unit>> {
        return performRequest(
            request = { service.addArticle(article) }
        )
    }

    private fun <T : Any> performRequest(
        request: suspend () -> Response<T>,
        onSuccess: (Response<T>) -> Unit = {}
    ): Flow<NetWorkResponseState<T>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                val response = request()
                if (response.isSuccessful) {
                    onSuccess(response)
                    response.body()?.let {
                        emit(NetWorkResponseState.Success(it))
                    } ?: emit(NetWorkResponseState.Error(Exception("Response body is null")))
                } else {
                    emit(NetWorkResponseState.Error(Exception("HTTP error ${response.code()}")))
                }
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(Exception("Exception: ${e.message}")))
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

    override fun returnUserProfileInformation(): Flow<NetWorkResponseState<UserProfileInformationDto>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                val response = service.returnUserProfileInformation(
                    userType = accountType
                )
                emit(NetWorkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    override fun updateUserProfileInformation(userProfileInformation: UserProfileInformation): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                Log.d("Doctor Profile", ".......")
                service.updateUserProfileInformation(
                    userType = accountType,
                    userName = createPartFromString(userProfileInformation.userName),
                    email = createPartFromString(userProfileInformation.email),
                    phoneNumber = createPartFromString(userProfileInformation.phoneNumber),
                    bio = createPartFromString(userProfileInformation.bio),
                    currentLevel = createPartFromString(userProfileInformation.currentLevel),
                    currentUniversity = createPartFromString(userProfileInformation.currentUniversity),
                    photo = userProfileInformation.photo
                )
                emit(NetWorkResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(e))
            }
        }
    }

    private fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), descriptionString)
    }

    override fun changeUserPassword(userPasswords: UserPasswords): Flow<NetWorkResponseState<Unit>> {
        return flow {
            emit(NetWorkResponseState.Loading)
            try {
                service.changeUserPassword(userPasswords)
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

