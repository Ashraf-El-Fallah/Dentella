package com.af.dentalla.data.repository

import com.af.dentalla.data.NetWorkResponseState
import com.af.dentalla.data.mapper.ArticlesEntitySavedMapper
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
import com.af.dentalla.data.source.local.LocalDataSource
import com.af.dentalla.data.source.remote.RemoteDataSource
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.domain.entity.CardsEntity
import com.af.dentalla.domain.entity.DoctorProfileEntity
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.domain.entity.ProfileInformationEntity
import com.af.dentalla.domain.mapper.BaseMapper
import com.af.dentalla.domain.mapper.ListMapper
import com.af.dentalla.domain.repository.UserRepository
import com.af.dentalla.utils.mapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val cardsEntityMapper: ListMapper<CardsDto, CardsEntity>,
    private val allArticlesEntityMapper: ListMapper<ArticleDto, ArticlesEntity>,
    private val cardsEntity: ListMapper<CardsDto, CardsEntity>,
    private val doctorProfileEntity: BaseMapper<DoctorProfileDto, DoctorProfileEntity>,
    private val allPostsEntityMapper: ListMapper<PostDtoItem, PostEntity>,
    private val profileInformationEntity: BaseMapper<UserProfileInformationDto, ProfileInformationEntity>,
    private val localDataSource: LocalDataSource,
    private val articlesEntitySavedMapper: ArticlesEntitySavedMapper
) : UserRepository {

    override fun loginUser(loginUser: LoginUser): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.loginUser(loginUser)
    }

    override fun signUpUser(signUpUser: SignUpUser): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.signUpUser(signUpUser)
    }

    override fun getAllDoctorsCards(): Flow<NetWorkResponseState<List<CardsEntity>>> {
        return remoteDataSource.getAllDoctorsCards().map {
            it.mapResponse {
                cardsEntityMapper.map(this)
            }
        }
    }

    override fun getAllArticles(): Flow<NetWorkResponseState<List<ArticlesEntity>>> {
        return remoteDataSource.getAllArticles().map {
            it.mapResponse {
                allArticlesEntityMapper.map(this)
            }
        }
    }

    override fun getCardsBySearchByUniversity(university: String): Flow<NetWorkResponseState<List<CardsEntity>>> {
        return remoteDataSource.getCardsBySearchByUniversity(university).map {
            it.mapResponse { cardsEntity.map(this) }
        }
    }

    override fun getDoctorProfileDetails(cardId: Int): Flow<NetWorkResponseState<DoctorProfileEntity>> {
        return remoteDataSource.getDoctorProfileDetails(cardId).map {
            it.mapResponse {
                doctorProfileEntity.map(this)
            }
        }
    }

    override fun getSpecialityDoctorsCards(specialityId: Int): Flow<NetWorkResponseState<List<CardsEntity>>> {
        return remoteDataSource.getSpecialityDoctorsCards(specialityId).map {
            it.mapResponse { cardsEntityMapper.map(this) }
        }
    }

    override fun getAllPosts(): Flow<NetWorkResponseState<List<PostEntity>>> {
        return remoteDataSource.getAllPosts().map {
            it.mapResponse {
                allPostsEntityMapper.map(this)
            }
        }
    }

    override fun addArticle(article: Article): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.addArticle(article)
    }


    override fun addPost(post: Post): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.addPost(post)
    }

    override fun addCard(card: Card): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.addCard(card)
    }

    override fun returnUserProfileInformation(): Flow<NetWorkResponseState<ProfileInformationEntity>> {
        return remoteDataSource.returnUserProfileInformation().map {
            it.mapResponse {
                profileInformationEntity.map(this)
            }
        }
    }


    override fun changeUserPassword(userPasswords: UserPasswords): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.changeUserPassword(userPasswords)
    }

    override fun logout(): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.logout()
    }

    override fun deleteUserInfo(): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.deleteUserInfo()
    }

    override fun getSavedArticlesFromDataBase(): Flow<NetWorkResponseState<List<ArticleSavedEntity>>> {
        return flow {
            try {
                emit(NetWorkResponseState.Success(localDataSource.getAllSavedArticles()))
            } catch (e: Exception) {
                emit(NetWorkResponseState.Error(exception = e))
            }
        }
    }


    override suspend fun insertArticleToDataBase(articleSavedEntity: ArticlesEntity) {
        val articleSavedEntity = articlesEntitySavedMapper.map(articleSavedEntity)
        localDataSource.saveArticle(articleSavedEntity)
    }

    override suspend fun deleteSavedArticle(articleSavedEntity: ArticleSavedEntity) {
        localDataSource.deleteArticle(articleSavedEntity)
    }

    override fun updateUserProfileInformation(userProfileInformation: UserProfileInformation): Flow<NetWorkResponseState<Unit>> {
        return remoteDataSource.updateUserProfileInformation(userProfileInformation)
    }
}

