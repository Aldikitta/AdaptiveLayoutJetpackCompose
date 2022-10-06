package com.aldikitta.adaptivelayoutjetpackcompose.data.repository_impl

import com.aldikitta.adaptivelayoutjetpackcompose.data.data_source.local.LocalEmailsDataProvider
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.Email
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.MailboxType
import com.aldikitta.adaptivelayoutjetpackcompose.domain.repository.EmailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EmailsRepositoryImpl : EmailsRepository {

    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.filter { it.mailbox == category }
        emit(categoryEmails)
    }

    override fun getAllFolders(): List<String> {
        return LocalEmailsDataProvider.getAllFolders()
    }

    override fun getEmailFromId(id: Long): Flow<Email?> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.firstOrNull { it.id == id }
    }
}
