package sample.cleanarchitecture.account.adapter.`in`.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyUseCase
import sample.cleanarchitecture.account.domain.AccountId
import sample.cleanarchitecture.account.domain.Money

@RestController
class AccountController(
    private val sendMoneyUseCase: SendMoneyUseCase,
) {
    @PostMapping("/account/send/{sourceAccountId}/{targetAccountId}/{amount}")
    fun sendMoney(
        @PathVariable("sourceAccountId") sourceAccountId: Long,
        @PathVariable("targetAccountId") targetAccountId: Long,
        @PathVariable("amount") amount: Long,
    ) {

        val command = SendMoneyCommand(
                sourceAccountId = AccountId(sourceAccountId),
                targetAccountId = AccountId(targetAccountId),
                money = Money.of(amount.toBigInteger())
            )

        sendMoneyUseCase.sendMoney(command)
    }
}