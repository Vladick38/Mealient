package gq.kirmanak.mealient.datasource.ktor

import gq.kirmanak.mealient.datasource.AuthenticationProvider
import gq.kirmanak.mealient.logging.Logger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import javax.inject.Inject
import javax.inject.Provider

internal class AuthKtorConfiguration @Inject constructor(
    private val authenticationProviderProvider: Provider<AuthenticationProvider>,
    private val logger: Logger,
) : KtorConfiguration {

    private val authenticationProvider: AuthenticationProvider
        get() = authenticationProviderProvider.get()

    override fun <T : HttpClientEngineConfig> configure(config: HttpClientConfig<T>) {
        config.install(Auth) {
            bearer {
                loadTokens { getTokens() }
                refreshTokens { getTokens() }
            }
        }
    }

    private suspend fun getTokens() : BearerTokens? {
        val token = authenticationProvider.getAuthToken()
        logger.v { "getTokens(): token = $token" }
        return token?.let { BearerTokens(accessToken = it, refreshToken = "") }
    }
}