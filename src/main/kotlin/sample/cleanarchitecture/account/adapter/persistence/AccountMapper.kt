package sample.cleanarchitecture.account.adapter.persistence

import org.springframework.stereotype.Component
import sample.cleanarchitecture.account.domain.Account
import sample.cleanarchitecture.account.domain.Account.Companion.AccountId
import sample.cleanarchitecture.account.domain.Activity
import sample.cleanarchitecture.account.domain.Activity.Companion.ActivityId
import sample.cleanarchitecture.account.domain.ActivityWindow
import sample.cleanarchitecture.account.domain.Money


@Component
class AccountMapper {
    fun mapToDomainEntity(
        account: AccountJpaEntity,
        activities: List<ActivityJpaEntity>,
        withdrawBalance: Long,
        depositBalance: Long,
    ): Account {

        val baselineMoney = Money.subtract(
            Money.of(depositBalance.toBigInteger()),
            Money.of(withdrawBalance.toBigInteger())
        )

        return Account.withId(
            AccountId(account.id),
            baselineMoney,
            mapToActivityWindow(activities)
        )
    }

    private fun mapToActivityWindow(activities: List<ActivityJpaEntity>): ActivityWindow {
        val mappedActivities: MutableList<Activity> = ArrayList()

        for (activity in activities) {
            mappedActivities.add(
                Activity(
                    ActivityId(activity.id),
                    AccountId(activity.ownerAccountId),
                    AccountId(activity.sourceAccountId),
                    AccountId(activity.targetAccountId),
                    activity.timestamp,
                    Money.of(activity.amount.toBigInteger())
                )
            )
        }

        return ActivityWindow(mappedActivities)
    }

    fun mapToJpaEntity(activity: Activity): ActivityJpaEntity {
        return ActivityJpaEntity(
            (if (activity.id == null) null else activity.id.value)!!,
            activity.timestamp,
            activity.ownerAccountId.value,
            activity.sourceAccountId.value,
            activity.targetAccountId.value,
            activity.money.amount.toLong()
        )
    }

}