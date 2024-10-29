package sample.cleanarchitecture.account.application.service

import sample.cleanarchitecture.account.application.port.`in`.GetAccountBalanceQuery
import sample.cleanarchitecture.account.application.port.out.LoadAccountPort
import sample.cleanarchitecture.account.domain.AccountId
import sample.cleanarchitecture.account.domain.Money
import java.time.LocalDateTime

class GetAccountBalanceService(
    val loadAccountPort: LoadAccountPort,
) : GetAccountBalanceQuery {
    override fun getAccountBalance(accountId: AccountId): Money {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance()
    }
}