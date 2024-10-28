package sample.cleanarchitecture.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AccountTest {

    @Test
    fun `잔액을 계산할 수 있다`() {
        // given
        val accountId = AccountId(1L)
        val baselineBalance = Money.of((1_000).toBigInteger())
        val activity = Activity(
            id = ActivityId(1L),
            ownerAccountId = AccountId(1L),
            sourceAccountId = AccountId(2L),
            targetAccountId = AccountId(1L),
            timestamp = LocalDateTime.of(2020, 1, 1, 0, 0),
            money = Money.of((1_000).toBigInteger()),
        )
        val activityWindow = ActivityWindow(mutableListOf(activity))
        val account = Account(id = accountId, baselineBalance = baselineBalance, activityWindow = activityWindow)

        // when
        val result = account.calculateBalance()

        // then
        assertThat(result).isEqualTo(Money.of((2_000).toBigInteger()))
    }

    @Test
    fun `인출 할 수 있다`() {
        // given
        val accountId = AccountId(1L)
        val baselineBalance = Money.of((1_000).toBigInteger())
        val activityWindow = ActivityWindow(mutableListOf())
        val account = Account(id = accountId, baselineBalance = baselineBalance, activityWindow = activityWindow)

        // when
        val result = account.withdraw(targetAccountId = AccountId(1L), money = Money.of((900).toBigInteger()))

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `인출 할 때, 잔액을 초과할 수 없다`() {
        // given
        val accountId = AccountId(1L)
        val baselineBalance = Money.of((1_000).toBigInteger())
        val activityWindow = ActivityWindow(mutableListOf())
        val account = Account(id = accountId, baselineBalance = baselineBalance, activityWindow = activityWindow)

        // when
        val result = account.withdraw(targetAccountId = AccountId(3L), money = Money.of((2_000).toBigInteger()))

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `예금 할 수 있다`() {
        // given
        val baselineBalance = Money.of((1_000).toBigInteger())
        val activityWindow = ActivityWindow(mutableListOf())
        val accountId = AccountId(1L)
        val account = Account(id = accountId, baselineBalance = baselineBalance, activityWindow = activityWindow)

        // when
        val result = account.deposit(sourceAccountId = AccountId(2L), money = Money.of((1_000).toBigInteger()))

        // then
        assertThat(result).isTrue()
        assertThat(account.calculateBalance()).isEqualTo(Money.of((2_000).toBigInteger()))
    }


}