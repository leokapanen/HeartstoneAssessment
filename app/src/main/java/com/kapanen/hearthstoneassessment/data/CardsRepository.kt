package com.kapanen.hearthstoneassessment.data

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.model.Card

interface CardsRepository {

    fun observeCards(): LiveData<Result<List<Card>>>

    fun observeCards(cardType: String): LiveData<Result<List<Card>>>

    fun observeCard(cardId: String): LiveData<Result<Card>>

    fun observeFavouriteCards(): LiveData<Result<List<Card>>>

    suspend fun getCards(): Result<List<Card>>

    suspend fun getCards(cardType: String): Result<List<Card>>

    suspend fun getCard(cardId: String): Result<Card>

    suspend fun getFavouriteCards(): Result<List<Card>>

    suspend fun addFavouriteCard(card: Card)

    suspend fun removeFavouriteCard(card: Card)

    suspend fun refresh()
}
