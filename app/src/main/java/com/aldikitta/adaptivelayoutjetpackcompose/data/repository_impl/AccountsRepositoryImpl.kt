package com.aldikitta.adaptivelayoutjetpackcompose.data.repository_impl

import com.aldikitta.adaptivelayoutjetpackcompose.data.data_source.local.LocalAccountsDataProvider
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.Account
import com.aldikitta.adaptivelayoutjetpackcompose.domain.repository.AccountsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountsRepositoryImpl : AccountsRepository {

    override fun getDefaultUserAccount(): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getDefaultUserAccount())
    }

    override fun getAllUserAccounts(): Flow<List<Account>> = flow {
        emit(LocalAccountsDataProvider.allUserAccounts)
    }

    override fun getContactAccountByUid(uid: Long): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getContactAccountByUid(uid))
    }
}
