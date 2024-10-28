package sample.cleanarchitecture.account.domain

import sample.cleanarchitecture.account.domain.Account.Companion.AccountId

class ActivityWindow(
    val activities: MutableList<Activity>
) {
    fun calculateBalance(accountId: AccountId): Money {
        val depositBalance: Money = activities.stream()
            .filter { a -> a.targetAccountId == accountId }
            .map(Activity::money)
            .reduce(Money.ZERO, Money::add)

        val withdrawalBalance: Money = activities.stream()
            .filter { a -> a.sourceAccountId == accountId }
            .map(Activity::money)
            .reduce(Money.ZERO, Money::add)

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    fun addActivity(withdrawal: Activity) {
        this.activities.add(withdrawal)
    }
}