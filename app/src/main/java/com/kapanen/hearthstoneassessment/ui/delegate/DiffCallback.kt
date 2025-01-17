package com.kapanen.hearthstoneassessment.ui.delegate

import androidx.recyclerview.widget.DiffUtil
import com.kapanen.hearthstoneassessment.model.*

class DiffCallback(
    private val oldItems: List<Any>,
    private val newItems: List<Any>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areItemsContentTheSame(oldItem, newItem)
    }

    private fun areItemsContentTheSame(oldItem: Any, newItem: Any): Boolean {
        return (oldItem is LoadingItem && newItem is LoadingItem)
                || (oldItem is NoDataItem && newItem is NoDataItem)
                || compareCardWrappers(oldItem, newItem)
                || compareCards(oldItem, newItem)
                || compareIntStat(oldItem, newItem)
                || compareStringStat(oldItem, newItem)
                || compareFavouriteItem(oldItem, newItem)
                || compareFilterHeaderItem(oldItem, newItem)
                || compareFilterItem(oldItem, newItem)
    }

    private fun compareCardWrappers(oldItem: Any, newItem: Any): Boolean {
        return oldItem is CardWrapper && newItem is CardWrapper
                && oldItem.isFavoriteFeed == newItem.isFavoriteFeed
                && compareCards(oldItem.card, newItem.card)
    }

    private fun compareCards(oldItem: Any, newItem: Any): Boolean {
        return oldItem is Card && newItem is Card
                && oldItem.cardId == newItem.cardId
                && oldItem.isFavorite == newItem.isFavorite
    }

    private fun compareIntStat(oldItem: Any, newItem: Any): Boolean {
        return oldItem is CardIntStatItem && newItem is CardIntStatItem
                && oldItem.label == newItem.label
                && oldItem.value == newItem.value
    }

    private fun compareStringStat(oldItem: Any, newItem: Any): Boolean {
        return oldItem is CardStringStatItem && newItem is CardStringStatItem
                && oldItem.label == newItem.label
                && oldItem.value == newItem.value
    }

    private fun compareFavouriteItem(oldItem: Any, newItem: Any): Boolean {
        return oldItem is FavouriteItem && newItem is FavouriteItem
                && oldItem.card.isFavorite == newItem.card.isFavorite
    }

    private fun compareFilterHeaderItem(oldItem: Any, newItem: Any): Boolean {
        return oldItem is FilterHeader && newItem is FilterHeader
                && oldItem.title == newItem.title
    }

    private fun compareFilterItem(oldItem: Any, newItem: Any): Boolean {
        return oldItem is FilterItem && newItem is FilterItem
                && oldItem.value == newItem.value
                && oldItem.filterType == newItem.filterType
                && oldItem.isEnabled == newItem.isEnabled
    }

}
