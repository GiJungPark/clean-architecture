package sample.cleanarchitecture.account.adapter.persistence

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import sample.cleanarchitecture.account.application.port.out.LoadAccountPort
import sample.cleanarchitecture.account.application.port.out.UpdateAccountStatePort
import sample.cleanarchitecture.account.domain.Account
import java.time.LocalDateTime

@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val activityRepository: ActivityRepository,
    private val accountMapper: AccountMapper,
) : LoadAccountPort, UpdateAccountStatePort {

    override fun loadAccount(accountId: Account.Companion.AccountId, baselineDate: LocalDateTime): Account {
        val account = accountRepository.findById(accountId.value).orElseThrow { EntityNotFoundException("Account not found: $accountId") }

        val activities = activityRepository.findByOwnerSince(accountId.value, baselineDate)

        val withdrawBalance = activityRepository.getWithdrawalBalanceUntil(accountId.value, baselineDate) ?: 0L

        val depositBalance = activityRepository.getDepositBalanceUntil(accountId.value, baselineDate) ?: 0L

        return accountMapper.mapToDomainEntity(account, activities, withdrawBalance, depositBalance)
    }

    override fun updateActivities(account: Account) {
        for (activity in account.activityWindow.activities) {
            if (activity.id == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity))
            }
        }
    }
}