package gq.kirmanak.mealient.data.auth.impl

import com.google.common.truth.Truth.assertThat
import gq.kirmanak.mealient.data.auth.AuthDataSource
import gq.kirmanak.mealient.data.auth.AuthRepo
import gq.kirmanak.mealient.data.auth.AuthStorage
import gq.kirmanak.mealient.data.baseurl.ServerInfoRepo
import gq.kirmanak.mealient.datasource.runCatchingExceptCancel
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_API_AUTH_HEADER
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_API_TOKEN
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_AUTH_HEADER
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_PASSWORD
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_SERVER_VERSION_V0
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_TOKEN
import gq.kirmanak.mealient.test.AuthImplTestData.TEST_USERNAME
import gq.kirmanak.mealient.test.BaseUnitTest
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepoImplTest : BaseUnitTest() {

    @MockK
    lateinit var dataSource: AuthDataSource

    @MockK
    lateinit var serverInfoRepo: ServerInfoRepo

    @MockK(relaxUnitFun = true)
    lateinit var storage: AuthStorage

    lateinit var subject: AuthRepo

    @Before
    override fun setUp() {
        super.setUp()
        subject = AuthRepoImpl(storage, dataSource, logger)
    }

    @Test
    fun `when isAuthorizedFlow then reads from storage`() = runTest {
        every { storage.authHeaderFlow } returns flowOf("", null, "header")
        assertThat(subject.isAuthorizedFlow.toList()).isEqualTo(listOf(true, false, true))
    }

    @Test
    fun `when authenticate successfully then saves to storage`() = runTest {
        coEvery { serverInfoRepo.getVersion() } returns TEST_SERVER_VERSION_V0
        coEvery { dataSource.authenticate(any(), any()) } returns TEST_TOKEN
        coEvery { dataSource.createApiToken(any()) } returns TEST_API_TOKEN
        subject.authenticate(TEST_USERNAME, TEST_PASSWORD)
        coVerify {
            dataSource.authenticate(eq(TEST_USERNAME), eq(TEST_PASSWORD))
            storage.setAuthHeader(TEST_AUTH_HEADER)
            dataSource.createApiToken(eq("Mealient"))
            storage.setAuthHeader(TEST_API_AUTH_HEADER)
        }
        confirmVerified(storage)
    }

    @Test
    fun `when authenticate fails then does not change storage`() = runTest {
        coEvery { dataSource.authenticate(any(), any()) } throws RuntimeException()
        runCatchingExceptCancel { subject.authenticate("invalid", "") }
        confirmVerified(storage)
    }

    @Test
    fun `when logout expect header removal`() = runTest {
        subject.logout()
        coVerify { storage.setAuthHeader(null) }
        confirmVerified(storage)
    }
}