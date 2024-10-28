package sample.cleanarchitecture.account.application.port.out

import sample.cleanarchitecture.account.domain.Account.Companion.AccountId

interface AccountLock {
    fun lockAccount(accountId: AccountId)
    fun releaseAccount(accountId: AccountId)
}