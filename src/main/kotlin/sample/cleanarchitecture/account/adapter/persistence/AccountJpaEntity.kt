package sample.cleanarchitecture.account.adapter.persistence

import jakarta.persistence.*

@Table(name = "account")
@Entity
class AccountJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
)