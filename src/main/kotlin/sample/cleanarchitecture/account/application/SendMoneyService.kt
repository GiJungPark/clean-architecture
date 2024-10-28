package sample.cleanarchitecture.account.application

import sample.cleanarchitecture.account.application.port.`in`.SendMoneyCommand
import sample.cleanarchitecture.account.application.port.`in`.SendMoneyUseCase
import sample.cleanarchitecture.account.application.port.out.AccountLock
import sample.cleanarchitecture.account.application.port.out.LoadAccountPort
import sample.cleanarchitecture.account.application.port.out.UpdateAccountStatePort

class SendMoneyService (
    val loadAccountPort: LoadAccountPort,
    val accountLock: AccountLock,
    val updateAccountStatePort: UpdateAccountStatePort,
): SendMoneyUseCase {

    override fun sendMoney(command: SendMoneyCommand): Boolean {
        // TODO: 비즈니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return true
    }
}