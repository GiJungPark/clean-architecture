package sample.cleanarchitecture.account.domain

import java.time.LocalDateTime

class Activity(
    val id: ActivityId? = null,
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