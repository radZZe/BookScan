//package ru.radzze.scan_impl.ui
//
//import kotlinx.coroutines.runBlocking
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import ru.radzze.core.utils.ApiUrl
//import ru.radzze.scan_impl.domain.ScanService
//
//class ScanScreenViewModelTest {
//
//
//    private lateinit var scanService: ScanService
//    private lateinit var  client :OkHttpClient
//    private lateinit var viewModel: ScanScreenViewModel
//    private val mockWebServer = MockWebServer()
//
//    @Before
//    fun setUp() {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        client =  OkHttpClient().newBuilder()
//            .addInterceptor(interceptor)
//            .build()
//
//        scanService = Retrofit
//            .Builder()
//            .baseUrl(mockWebServer.url("/"))
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ScanService::class.java)
//
//        viewModel = ScanScreenViewModel(scanService)
//    }
//
//
//    @After
//    fun tearDown() {
//    }
//
//    @Test
//    fun `normal post image to scan`() = runBlocking {
//        val response = MockResponse().setResponseCode(200)
//        mockWebServer.enqueue(response)
//        viewModel.sendImageToScan("fdsafsdfasd")
//        val res = viewModel.isSuccessRequest
//        Assert.assertEquals(true,res)
//
//    }
//
//    @Test
//    fun `failed post image to scan`() {
//        val response = MockResponse().setResponseCode(500)
//        val response2 = MockResponse().setResponseCode(401)
//        val response3 = MockResponse().setResponseCode(400)
//        mockWebServer.enqueue(response)
//        mockWebServer.enqueue(response2)
//        mockWebServer.enqueue(response3)
//        viewModel.sendImageToScan("fdsafsdfasd")
//        Assert.assertEquals(false,viewModel.isSuccessRequest)
//        viewModel.sendImageToScan("ffffffffffff")
//        Assert.assertEquals(false,viewModel.isSuccessRequest)
//        viewModel.sendImageToScan("ggggggggggggg")
//        Assert.assertEquals(false,viewModel.isSuccessRequest)
//    }
//}