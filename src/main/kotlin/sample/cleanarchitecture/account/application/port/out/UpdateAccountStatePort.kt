package sample.cleanarchitecture.account.application.port.out

import sample.cleanarchitecture.account.domain.Account

interface UpdateAccountStatePort {
    fun updateActivities(account: Account)
}