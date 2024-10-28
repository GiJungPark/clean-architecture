package sample.cleanarchitecture.account.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MoneyTest {

    @Test
    fun `Money를 생성할 수 있다`() {
        // given
        val value = 10_000L

        // when
        val money = Money.of(value.toBigInteger())

        //then
        assertThat(money.amount).isEqualTo(value)
    }

    @Test
    fun `Money를 더할 수 있다`() {
        // given
        val value = 10_000L
        val money = Money.of(value.toBigInteger())

        // when
        val addMoney = Money.add(money, money)

        //then
        assertThat(addMoney.amount).isEqualTo(20_000L)
    }


    @Test
    fun `Money를 뺄 수 있다`() {
        // given
        val value = 10_000L
        val subValue = 5_000L
        val money = Money.of(value.toBigInteger())
        val subMoney = Money.of(subValue.toBigInteger())

        // when
        val subtractedMoney = Money.subtract(money, subMoney)

        //then
        assertThat(subtractedMoney.amount).isEqualTo(5_000L)
    }

    @Test
    fun `Money를 음수로 변경할 수 있다`() {
        // given
        val value = 10_000L
        val money = Money.of(value.toBigInteger())

        // when
        val negatedMoney = money.negate()

        //then
        assertThat(negatedMoney.amount).isEqualTo(-10_000L)
    }

    @Test
    fun `Money가 1이상이면, isPositive()는 true를 반환한다`() {
        // given
        val value = 1L
        val money = Money.of(value.toBigInteger())

        // when
        val result = money.isPositive()

        //then
        assertThat(result).isTrue()
    }

    @Test
    fun `Money가 1미만이면, isPositive()는 false를 반환한다`() {
        // given
        val value = 0L
        val money = Money.of(value.toBigInteger())

        // when
        val result = money.isPositive()

        //then
        assertThat(result).isFalse()
    }

}