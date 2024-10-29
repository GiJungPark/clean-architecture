package sample.cleanarchitecture.account.application.port.`in`

import sample.cleanarchitecture.account.domain.AccountId
import sample.cleanarchitecture.account.domain.Money

interface GetAccountBalanceQuery {
    fun getAccountBalance(accountId: AccountId): Money
}