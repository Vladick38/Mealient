package gq.kirmanak.mealient.database

import androidx.room.*
import gq.kirmanak.mealient.database.recipe.RecipeDao
import gq.kirmanak.mealient.database.recipe.entity.*

@Database(
    version = 10,
    entities = [
        RecipeSummaryEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        RecipeInstructionEntity::class,
    ]
)
@TypeConverters(RoomTypeConverters::class)
internal abstract class AppDb : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}