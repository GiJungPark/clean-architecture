package sample.cleanarchitecture.account.application.service

import org.springframework.stereotype.Component
import sample.cleanarchitecture.account.application.port.out.AccountLock

@Component
class NoOpAccountLock : AccountLock {
}