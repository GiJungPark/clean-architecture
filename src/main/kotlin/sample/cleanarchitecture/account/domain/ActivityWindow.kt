package sample.cleanarchitecture.account.domain

class ActivityWindow(
    val activities: MutableList<Activity>,
) {
    fun calculateBalance(accountId: AccountId): Money {
        val depositBalance: Money = activities.stream()
            .filter { it.targetAccountId == accountId }
            .map { it.money }
            .reduce(Money.ZERO, Money::add)

        val withdrawalBalance: Money = activities.stream()
            .filter { it.sourceAccountId == accountId }
            .map { it.money }
            .reduce(Money.ZERO, Money::add)

        return Money.add(depositBalance, withdrawalBalance.negate())
    }

    fun addActivity(withdrawal: Activity) {
        this.activities.add(withdrawal)
    }
}