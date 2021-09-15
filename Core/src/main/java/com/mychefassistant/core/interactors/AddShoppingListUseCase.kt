package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.utils.BaseSuspendUseCase

class AddShoppingListUseCase(private val shoppingListRepository: ShoppingListRepository) :
    BaseSuspendUseCase<ShoppingList, Int>() {
    override suspend fun execute(parameter: ShoppingList): Result<Int> {
        return Result.success(shoppingListRepository.addShoppingList(parameter))
    }
}