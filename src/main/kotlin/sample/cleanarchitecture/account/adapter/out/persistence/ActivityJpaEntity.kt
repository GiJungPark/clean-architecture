package sample.cleanarchitecture.account.adapter.out.persistence

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "activity")
@Entity
class ActivityJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L,

    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var ownerAccountId: Long = 0L,

    @Column(nullable = false)
    var sourceAccountId: Long = 0L,

    @Column(nullable = false)
    var targetAccountId: Long = 0L,

    @Column(nullable = false)
    var amount: Long = 0L,
)