package com.kapanen.hearthstoneassessment.data

import androidx.lifecycle.LiveData
import com.kapanen.hearthstoneassessment.data.local.LocalCardsDataSource
import com.kapanen.hearthstoneassessment.data.remote.RemoteCardsDataSource
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.setting.AppSettings

class DefaultCardsRepository(
    private val remoteDataSource: RemoteCardsDataSource,
    private val localDataSource: LocalCardsDataSource,
    private val appSettings: AppSettings
) : CardsRepository {

    override fun observeCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeCards()

    override fun observeCards(cardType: String): LiveData<Result<List<Card>>> =
        localDataSource.observeCards(cardType)

    override fun observeCard(cardId: String): LiveData<Result<Card>> =
        localDataSource.observeCard(cardId)

    override fun observeFavouriteCards(): LiveData<Result<List<Card>>> =
        localDataSource.observeFavouriteCards()

    override suspend fun getCards(): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards()
    }

    override suspend fun getCards(cardType: String): Result<List<Card>> {
        if (!appSettings.isDataInitiallyLoaded) {
            updateCardsFromRemoteDataSource()
        }
        return localDataSource.getCards(cardType)
    }

    override suspend fun getCard(cardId: String): Result<Card> = localDataSource.getCard(cardId)

    override suspend fun getFavouriteCards(): Result<List<Card>> =
        localDataSource.getFavouriteCards()

    override suspend fun addFavouriteCard(card: Card) {
        localDataSource.addFavouriteCard(card)
        appSettings.notifyFavoriteUpdate(card.copy(isFavorite = true))
    }

    override suspend fun removeFavouriteCard(card: Card) {
        localDataSource.removeFavouriteCard(card)
        appSettings.notifyFavoriteUpdate(card.copy(isFavorite = false))
    }

    override suspend fun refresh() {
        updateCardsFromRemoteDataSource()
    }

    private suspend fun updateCardsFromRemoteDataSource() {
        val remoteCardsResult = remoteDataSource.getCards()

        if (remoteCardsResult.isSuccess) {
            appSettings.isDataInitiallyLoaded = true
            localDataSource.saveCards(remoteCardsResult.getOrDefault(emptyList()))
        }
    }

}
