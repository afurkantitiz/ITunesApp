package com.example.casestudy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.casestudy.data.ApiRepository
import com.example.casestudy.data.entity.BaseResponse
import com.example.casestudy.data.local.LocalDataSource
import com.example.casestudy.data.local.SearchDao
import com.example.casestudy.data.remote.ApiService
import com.example.casestudy.data.remote.RemoteDataSource
import com.example.casestudy.ui.home.HomeViewModel
import com.example.casestudy.utils.CoroutineRule
import com.example.casestudy.utils.Resource
import com.example.casestudy.utils.getOrAwaitValue
import com.example.casestudy.utils.parseFileAsSearchResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelUnitTest {
    @get:Rule
    var coroutineTestRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var searchDao: SearchDao

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource

    private lateinit var apiRepository: ApiRepository
    private lateinit var homeViewModel: HomeViewModel

    private val data = parseFileAsSearchResponse("fakeSearchResponse.json")
    private val mockSuccessResponse = Response.success(data)

    private val errorResponse = "{\n" +
            " \"errorMessage\":\"Invalid value(s) for key(s): [resultEntity]\",\n" +
            " \"queryParameters\":{\"output\":\"json\", \"callback\":\"A javascript function to handle your search results\", \"country\":\"ISO-2A country code\", \"limit\":\"The number of search results to return\", \"term\":\"A search string\", \"lang\":\"ISO-2A language code\"}\n" +
            "}"

    private val errorResponseBody =
        errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

    val mockUnSuccessResponse = Response.error<BaseResponse>(400, errorResponseBody)

    private var searchStateObserver = mock<Observer<Resource<Any>>>()

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(apiService)
        localDataSource = LocalDataSource(searchDao)
        apiRepository = ApiRepository(remoteDataSource, localDataSource)
        homeViewModel = HomeViewModel(apiRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get list from viewModel and check is success`() = coroutineTestRule.runBlockingTest {

        whenever(apiService.getSearchByQuery(anyString(), anyString(), anyInt())).thenReturn(
            mockSuccessResponse
        )

        homeViewModel.getNewsByQuery("var", "movie", 1).observeForever(searchStateObserver)

        homeViewModel.getNewsByQuery("var", "movie", 1).getOrAwaitValue()

        verify(searchStateObserver).onChanged(Resource.loading())
        verify(searchStateObserver).onChanged(Resource.success(data))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get live data from viewModel and check is unsuccessful`() =
        coroutineTestRule.runBlockingTest {

            whenever(apiService.getSearchByQuery(anyString(), anyString(), anyInt())).thenReturn(
                mockUnSuccessResponse
            )

            homeViewModel.getNewsByQuery("var", "film", 1).observeForever(searchStateObserver)

            homeViewModel.getNewsByQuery("var", "film", 1).getOrAwaitValue()

            verify(searchStateObserver).onChanged(Resource.loading())
            verify(searchStateObserver).onChanged(Resource.error("Error: ERROR", null))
        }
}