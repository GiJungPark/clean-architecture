package sample.cleanarchitecture.account.application.service

import org.springframework.stereotype.Component
import sample.cleanarchitecture.account.application.port.out.AccountLock
import sample.cleanarchitecture.account.domain.AccountId

@Component
class NoOpAccountLock : AccountLock {
    override fun lockAccount(accountId: AccountId) {
        // doesn't do anything.
    }

    override fun releaseAccount(accountId: AccountId) {
        // doesn't do anything.
    }
}