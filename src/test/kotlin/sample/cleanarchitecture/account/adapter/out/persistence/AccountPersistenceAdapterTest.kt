package sample.cleanarchitecture.account.adapter.out.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import sample.cleanarchitecture.account.domain.*
import java.time.LocalDateTime


@DataJpaTest
@Import(AccountPersistenceAdapter::class, AccountMapper::class)
class AccountPersistenceAdapterTest {

    @Autowired
    private lateinit var accountPersistenceAdapter: AccountPersistenceAdapter

    @Autowired
    private lateinit var activityRepository: ActivityRepository

    @Test
    @Sql("AccountPersistenceAdapterTest.sql")
    fun `계좌 활동 내역 및 잔액을 불러올 수 있다`() {
        // given

        // when
        val account: Account = accountPersistenceAdapter.loadAccount(AccountId(1L), LocalDateTime.of(2018, 8, 10, 0, 0))

        assertThat(account.activityWindow.activities).hasSize(2)
        assertThat(account.calculateBalance()).isEqualTo(Money.of(500.toBigInteger()))
    }

    @Test
    fun `계좌 활동을 추가할 수 있다`() {
        // given
        val account = Account(
            id = AccountId(1L),
            baselineBalance = Money.of(555.toBigInteger()),
            activityWindow = ActivityWindow(
                mutableListOf(
                    Activity(
                        ownerAccountId = AccountId(42L),
                        sourceAccountId = AccountId(42L),
                        targetAccountId = AccountId(41L),
                        timestamp = LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                        money = Money.of(1.toBigInteger()),
                    )
                )
            )
        )

        // when
        accountPersistenceAdapter.updateActivities(account)

        // then
        assertThat(activityRepository.count()).isEqualTo(1)

        val savedActivity = activityRepository.findAll()[0]
        assertThat(savedActivity.amount).isEqualTo(1L)
    }
}