package sample.cleanarchitecture.account.domain

import sample.cleanarchitecture.account.domain.Account.Companion.AccountId
import java.time.LocalDateTime

class Activity(
    val ownerAccountId: AccountId,
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    val timestamp: LocalDateTime,
    val money: Money,
) {

    companion object {
        class ActivityId(val value: Long)
    }
}