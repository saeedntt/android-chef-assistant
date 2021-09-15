package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.ShoppingListRepository
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.utils.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetShoppingListsUseCase(private val shoppingListRepository: ShoppingListRepository) :
    BaseUseCase<Boolean, Flow<List<ShoppingList>>>() {
    override fun execute(parameter: Boolean): Result<Flow<List<ShoppingList>>> {
        return Result.success(shoppingListRepository.getShoppingList())
    }
}