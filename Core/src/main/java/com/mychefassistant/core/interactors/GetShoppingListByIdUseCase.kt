package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.utils.BaseSuspendUseCase

class GetShoppingListByIdUseCase(private val shoppingListRepository: ShoppingListRepository) :
    BaseSuspendUseCase<Int, ShoppingList>() {
    override suspend fun execute(parameter: Int): Result<ShoppingList> {
        return Result.success(shoppingListRepository.getShoppingListById(parameter))
    }
}