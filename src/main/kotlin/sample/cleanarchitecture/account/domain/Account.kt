package sample.cleanarchitecture.account.domain

import java.time.LocalDateTime

class Account(
    val id: AccountId,
    val baselineBalance: Money,
    val activityWindow: ActivityWindow,
) {
    // 잔액 계산
    fun calculateBalance(): Money {
        return Money.add(
            this.baselineBalance,
            this.activityWindow.calculateBalance(this.id)
        )
    }

    // 출금
    fun withdraw(money: Money, targetAccountId: AccountId): Boolean {
        if (!mayWithdraw(money)) {
            return false
        }

        val withdrawal = Activity(
            ownerAccountId = this.id,
            sourceAccountId =  this.id,
            targetAccountId = targetAccountId,
            timestamp = LocalDateTime.now(),
            money = money
        )

        this.activityWindow.addActivity(withdrawal)

        return true
    }

    // 예금
    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit = Activity(
            ownerAccountId = this.id,
            sourceAccountId = sourceAccountId,
            targetAccountId = this.id,
            timestamp = LocalDateTime.now(),
            money = money
        )

        this.activityWindow.addActivity(deposit)

        return true
    }

    companion object {
        fun withId(accountId: AccountId, baselineMoney: Money, activityWindow: ActivityWindow): Account {
            return Account(accountId, baselineMoney, activityWindow)
        }

        class AccountId(val value: Long)
    }

    private fun mayWithdraw(money: Money): Boolean {
        return Money.add(
            this.calculateBalance(),
            money.negate()
        ).isPositive()
    }
}