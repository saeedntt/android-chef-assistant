package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListItemRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem
import com.mychefassistant.core.utils.BaseSuspendUseCase

class AddShoppingListItemUseCase(private val shoppingListItemRepository: ShoppingListItemRepository) :
    BaseSuspendUseCase<Pair<ShoppingList, ShoppingListItem>, Int>() {
    override suspend fun execute(parameter: Pair<ShoppingList, ShoppingListItem>): Result<Int> {
        return Result.success(
            shoppingListItemRepository.addShoppingListItem(parameter.first, parameter.second)
        )
    }
}