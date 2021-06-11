package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.AuthorizationRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthorizationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuthorizationRepository {
    override fun login(name: String, password: String): Single<AuthorizeResultType> =
        remoteDataSource.login(name, password)
            .doAfterSuccess {
                if (it is ResultType.Success) {
                    localDataSource.saveBearerToken(it.data)
                }
            }
            .map {
                when (it) {
                    is ResultType.Success -> {
                        AuthorizeResultType.Success
                    }

                    is ResultType.Error -> {
                        AuthorizeResultType.Error(it.error)
                    }
                }
            }.subscribeOn(Schedulers.io())

    override fun register(name: String, password: String): Single<AuthorizeResultType> =
        remoteDataSource.register(name, password).map {
            when (it) {
                is ResultType.Success -> {
                    AuthorizeResultType.Success
                }

                is ResultType.Error -> {
                    AuthorizeResultType.Error(it.error)
                }
            }
        }.subscribeOn(Schedulers.io())
}