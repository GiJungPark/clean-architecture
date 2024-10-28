package sample.cleanarchitecture.account.application.port.`in`

import jakarta.validation.constraints.NotNull
import sample.cleanarchitecture.account.domain.Account.Companion.AccountId
import sample.cleanarchitecture.account.domain.Money
import sample.cleanarchitecture.common.SelfValidating

class SendMoneyCommand private constructor(
    @field:NotNull
    val sourceAccountId: AccountId,

    @field:NotNull
    val targetAccountId: AccountId,

    @field:NotNull
    val money: Money,
) : SelfValidating<SendMoneyCommand>() {
    init {
        this.validateSelf()
    }
}