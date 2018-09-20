package de.codecentric.psd.worblehat.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BorrowingRepository : JpaRepository<Borrowing, Long> {

    @Query("SELECT b from Borrowing b WHERE b.borrowedBook = :book")
    fun findBorrowingForBook(@Param("book") book: Book): Borrowing

    @Query("SELECT b from Borrowing b WHERE b.borrowerEmailAddress = :borrowerEmailAddress")
    fun findBorrowingsByBorrower(@Param("borrowerEmailAddress") borrowerEmailAddress: String): List<Borrowing>
}
