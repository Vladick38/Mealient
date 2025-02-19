package gq.kirmanak.mealient.shopping_lists

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gq.kirmanak.mealient.shopping_lists.network.ShoppingListsDataSource
import gq.kirmanak.mealient.shopping_lists.network.ShoppingListsDataSourceImpl
import gq.kirmanak.mealient.shopping_lists.repo.ShoppingListsRepo
import gq.kirmanak.mealient.shopping_lists.repo.ShoppingListsRepoImpl
import gq.kirmanak.mealient.shopping_lists.util.LoadingHelperFactory
import gq.kirmanak.mealient.shopping_lists.util.LoadingHelperFactoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface ShoppingListsModule {

    @Binds
    fun bindShoppingListsDataSource(impl: ShoppingListsDataSourceImpl): ShoppingListsDataSource

    @Binds
    fun bindShoppingListsRepo(impl: ShoppingListsRepoImpl): ShoppingListsRepo

    @Binds
    fun bindLoadingHelperFactory(impl: LoadingHelperFactoryImpl): LoadingHelperFactory
}