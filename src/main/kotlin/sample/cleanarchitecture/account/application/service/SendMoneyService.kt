package sample.cleanarchitecture.account.application.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyUseCase
import sample.cleanarchitecture.account.application.port.out.AccountLock
import sample.cleanarchitecture.account.application.port.out.LoadAccountPort
import sample.cleanarchitecture.account.application.port.out.UpdateAccountStatePort
import sample.cleanarchitecture.account.domain.AccountId
import java.time.LocalDateTime


@Service
class SendMoneyService (
    val loadAccountPort: LoadAccountPort,
    val accountLock: AccountLock,
    val updateAccountStatePort: UpdateAccountStatePort,
): SendMoneyUseCase {
    @Transactional
    override fun sendMoney(command: SendMoneyCommand): Boolean {
        val baselineDate = LocalDateTime.now().minusDays(10)

        val sourceAccount = loadAccountPort.loadAccount(
            accountId = command.sourceAccountId,
            baselineDate = baselineDate,
        )

        val targetAccount = loadAccountPort.loadAccount(
            accountId = command.targetAccountId,
            baselineDate = baselineDate
        )

        val sourceAccountId: AccountId = sourceAccount.id
        val targetAccountId: AccountId = targetAccount.id

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return false
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            accountLock.releaseAccount(targetAccountId)
            return false
        }

        updateAccountStatePort.updateActivities(sourceAccount)
        updateAccountStatePort.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)
        return true
    }
}