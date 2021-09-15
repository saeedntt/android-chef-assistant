package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListItemRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem
import com.mychefassistant.core.utils.BaseSuspendUseCase
import kotlinx.coroutines.flow.Flow

class GetShoppingListItemsUseCase(private val shoppingListItemRepository: ShoppingListItemRepository) :
    BaseSuspendUseCase<ShoppingList, Flow<List<ShoppingListItem>>>() {
    override suspend fun execute(parameter: ShoppingList): Result<Flow<List<ShoppingListItem>>> {
        return Result.success(shoppingListItemRepository.getShoppingListItems(parameter))
    }
}