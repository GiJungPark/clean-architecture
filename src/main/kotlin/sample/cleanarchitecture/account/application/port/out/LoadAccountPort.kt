package sample.cleanarchitecture.account.application.port.out

import sample.cleanarchitecture.account.domain.Account
import sample.cleanarchitecture.account.domain.Account.Companion.AccountId
import java.time.LocalDateTime


interface LoadAccountPort {
    fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account
}