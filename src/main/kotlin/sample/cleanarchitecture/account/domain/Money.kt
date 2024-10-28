package sample.cleanarchitecture.account.domain

import java.math.BigInteger

class Money private constructor(
    val amount: BigInteger,
) {
    fun negate(): Money {
        return Money(this.amount.negate())
    }

    fun isPositive(): Boolean {
        return this.amount.compareTo(BigInteger.ZERO) > 0
    }

    companion object {

        val ZERO: Money = Money.of(BigInteger.ZERO)

        fun of(amount: BigInteger): Money {
            return Money(amount)
        }

        fun add(a: Money, b: Money): Money {
            return Money(a.amount.add(b.amount))
        }

        fun subtract(a: Money, b: Money): Money {
            return Money(a.amount.subtract(b.amount))
        }
    }
}