package gq.kirmanak.mealient.datasource

interface AuthenticationProvider {

    suspend fun getAuthToken(): String?

}