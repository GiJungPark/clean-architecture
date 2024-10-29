package sample.cleanarchitecture.account.adapter.out.persistence

import jakarta.persistence.*

@Table(name = "account")
@Entity
internal class AccountJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
)