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
            this.id,
            this.id,
            targetAccountId,
            LocalDateTime.now(),
            money
        )

        this.activityWindow.addActivity(withdrawal)

        return true
    }

    // 예금
    fun deposit(money: Money, sourceAccountId: AccountId): Boolean {
        val deposit = Activity(
            this.id,
            sourceAccountId,
            this.id,
            LocalDateTime.now(),
            money
        )

        this.activityWindow.addActivity(deposit)

        return true
    }

    companion object {
        class AccountId(val value: Long)
    }

    private fun mayWithdraw(money: Money): Boolean {
        return Money.add(
            this.calculateBalance(),
            money.negate()
        ).isPositive()
    }
}