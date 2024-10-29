package sample.cleanarchitecture.account.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface AccountRepository : JpaRepository<AccountJpaEntity, Long>