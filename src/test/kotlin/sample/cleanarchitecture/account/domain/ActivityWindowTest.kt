package sample.cleanarchitecture.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ActivityWindowTest {

    @Test
    fun `입출금 활동 내역으로 잔액을 계산할 수 있다`() {
        // given
        val depositActivity = Activity(
            id = ActivityId(1L),
            ownerAccountId = AccountId(1L),
            sourceAccountId = AccountId(2L),
            targetAccountId = AccountId(3L),
            timestamp = LocalDateTime.of(2020, 1, 1, 0, 0),
            money =  Money.of((1_000).toBigInteger())
        )

        val withdrawalActivity = Activity(
            id = ActivityId(1L),
            ownerAccountId = AccountId(1L),
            sourceAccountId = AccountId(3L),
            targetAccountId = AccountId(2L),
            timestamp = LocalDateTime.of(2020, 1, 1, 1, 0),
            money =  Money.of((500).toBigInteger())
        )

        val activityWindow = ActivityWindow(mutableListOf(depositActivity, withdrawalActivity))

        // when
        val result = activityWindow.calculateBalance(accountId = AccountId(3L))

        // then
        assertThat(result).isEqualTo(Money.of((500).toBigInteger()))
    }

    @Test
    fun `입출금 활동을 추가할 수 있다`() {
        // given
        val activityWindow = ActivityWindow(mutableListOf())
        val activity = Activity(
            id = ActivityId(1L),
            ownerAccountId = AccountId(1L),
            sourceAccountId = AccountId(2L),
            targetAccountId = AccountId(3L),
            timestamp = LocalDateTime.of(2020, 1, 1, 0, 0),
            money =  Money.of((1_000).toBigInteger())
        )

        // when
        activityWindow.addActivity(activity)

        // then
        assertThat(activityWindow.activities).hasSize(1)
    }

}