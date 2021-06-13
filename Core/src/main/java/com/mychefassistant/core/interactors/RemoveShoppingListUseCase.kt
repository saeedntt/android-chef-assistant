package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.utils.BaseSuspendUseCase

class RemoveShoppingListUseCase(private val shoppingListRepository: ShoppingListRepository) :
    BaseSuspendUseCase<ShoppingList, Unit>() {
    override suspend fun execute(parameter: ShoppingList): Result<Unit> {
        return Result.success(shoppingListRepository.removeShoppingList(parameter))
    }
}