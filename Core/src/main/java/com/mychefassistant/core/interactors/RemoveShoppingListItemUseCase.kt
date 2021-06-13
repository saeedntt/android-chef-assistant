package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListItemRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem
import com.mychefassistant.core.utils.BaseSuspendUseCase

class RemoveShoppingListItemUseCase(private val shoppingListItemRepository: ShoppingListItemRepository) :
    BaseSuspendUseCase<Pair<ShoppingList, ShoppingListItem>, Unit>() {
    override suspend fun execute(parameter: Pair<ShoppingList, ShoppingListItem>): Result<Unit> {
        return Result.success(
            shoppingListItemRepository.removeShoppingListItem(parameter.first, parameter.second)
        )
    }
}