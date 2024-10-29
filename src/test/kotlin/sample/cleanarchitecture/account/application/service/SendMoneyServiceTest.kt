package sample.cleanarchitecture.account.application.service

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.boot.test.mock.mockito.MockBean
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import sample.cleanarchitecture.account.application.port.out.AccountLock
import sample.cleanarchitecture.account.application.port.out.LoadAccountPort
import sample.cleanarchitecture.account.application.port.out.UpdateAccountStatePort
import sample.cleanarchitecture.account.domain.Account
import sample.cleanarchitecture.account.domain.AccountId
import sample.cleanarchitecture.account.domain.ActivityWindow
import sample.cleanarchitecture.account.domain.Money
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class SendMoneyServiceTest {

    @Mock
    private lateinit var loadAccountPort: LoadAccountPort

    @Mock
    private lateinit var accountLock: AccountLock

    @Mock
    private lateinit var updateAccountStatePort: UpdateAccountStatePort

    @InjectMocks
    private lateinit var sendMoneyService: SendMoneyService

    @Test
    fun `돈을 송금할 수 있다`() {
        // given
        val sourceAccountId = AccountId(1L)
        val targetAccountId = AccountId(2L)
        val money = Money.of(1000.toBigInteger())

        val command = SendMoneyCommand(
            sourceAccountId = sourceAccountId,
            targetAccountId = targetAccountId,
            money = money
        )

        val sourceAccount = Account(
            id = sourceAccountId,
            baselineBalance = money,
            activityWindow = ActivityWindow(mutableListOf())
        )

        val targetAccount = Account(
            id = targetAccountId,
            baselineBalance = Money.ZERO,
            activityWindow = ActivityWindow(mutableListOf())
        )

        given(loadAccountPort.loadAccount(eq(sourceAccountId), any())).willReturn(sourceAccount)
        given(loadAccountPort.loadAccount(eq(targetAccountId), any())).willReturn(targetAccount)

        // when
        val result = sendMoneyService.sendMoney(command)

        // then
        assertThat(result).isTrue()

        assertThat(sourceAccount.activityWindow.activities).hasSize(1)
            .extracting("ownerAccountId", "sourceAccountId", "targetAccountId", "money")
            .containsExactly(
                tuple(sourceAccountId, sourceAccountId, targetAccountId, money)
            )

        assertThat(targetAccount.activityWindow.activities).hasSize(1)
            .extracting("ownerAccountId", "sourceAccountId", "targetAccountId", "money")
            .containsExactly(
                tuple(targetAccountId, sourceAccountId, targetAccountId, money)
            )

    }

    @Test
    fun `돈을 송금할 때, 잔액이 부족하다면 실패한다`() {
        // given
        val sourceAccountId = AccountId(1L)
        val targetAccountId = AccountId(2L)
        val money = Money.of(1000.toBigInteger())

        val command = SendMoneyCommand(
            sourceAccountId = sourceAccountId,
            targetAccountId = targetAccountId,
            money = money
        )

        val sourceAccount = Account(
            id = sourceAccountId,
            baselineBalance = Money.ZERO,
            activityWindow = ActivityWindow(mutableListOf())
        )

        val targetAccount = Account(
            id = targetAccountId,
            baselineBalance = Money.ZERO,
            activityWindow = ActivityWindow(mutableListOf())
        )

        given(loadAccountPort.loadAccount(eq(sourceAccountId), any())).willReturn(sourceAccount)
        given(loadAccountPort.loadAccount(eq(targetAccountId), any())).willReturn(targetAccount)

        // when
        val result = sendMoneyService.sendMoney(command)

        // then
        assertThat(result).isFalse()

        assertThat(sourceAccount.activityWindow.activities).hasSize(0)
        assertThat(targetAccount.activityWindow.activities).hasSize(0)

    }
}