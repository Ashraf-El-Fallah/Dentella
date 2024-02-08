package com.af.dentalla.di.mapper

import com.af.dentalla.data.remote.dto.LoginResponse
import com.af.dentalla.data.remote.dto.SignUpResponse
import com.af.dentalla.data.mapper.BaseMapper
import com.af.dentalla.data.mapper.LoginEntityMapper
import com.af.dentalla.data.mapper.SignUpEntityMapper
import com.af.dentalla.domain.entity.LoginEntity
import com.af.dentalla.domain.entity.SignUpEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLoginEntityMapper(loginEntityMapper: LoginEntityMapper): BaseMapper<LoginResponse, LoginEntity>

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpEntityMapper(signUpEntityMapper: SignUpEntityMapper): BaseMapper<SignUpResponse, SignUpEntity>
}