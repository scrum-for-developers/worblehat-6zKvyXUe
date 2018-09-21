package de.codecentric.psd.worblehat.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, Long> {

    fun findAllByOrderByTitle(): List<Book>

    fun findByIsbn(isbn: String): Set<Book>

    fun findTopByIsbn(isbn: String): Optional<Book>

    fun findByAuthor(author: String): Set<Book>

    fun findByEdition(edition: String): Set<Book>
}
