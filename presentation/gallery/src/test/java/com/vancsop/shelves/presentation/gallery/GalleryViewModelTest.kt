package com.vancsop.shelves.presentation.gallery

import com.vancsop.shelves.domain.catalogue.ExportItemsUseCase
import com.vancsop.shelves.domain.catalogue.GetItemsUseCase
import com.vancsop.shelves.domain.catalogue.GetItemsUseCase.Item
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: GalleryViewModel
    private val getItems: GetItemsUseCase = mockk()
    private val exportItems: ExportItemsUseCase = mockk()

    @Test
    fun `WHEN getItems returns items THEN show Content`() = runTest {
        // GIVEN
        val items = listOf(
            Item(
                classification = "phone",
                shelfId = "A1",
                path = "images/img1.jpg"
            ),
            Item(
                classification = "keyboard",
                shelfId = "A2",
                path = "images/img2.jpg"
            ),
            Item(
                classification = "remote",
                shelfId = "A2",
                path = "images/img3.jpg"
            )
        )
        val expected = listOf(
            GalleryViewModel.Image(label = "A1: phone", path = "images/img1.jpg"),
            GalleryViewModel.Image(label = "A2: keyboard", path = "images/img2.jpg"),
            GalleryViewModel.Image(label = "A2: remote", path = "images/img3.jpg")
        )
        coEvery { getItems() } returns flowOf(items)
        initViewModel()

        assertEquals(
            GalleryViewModel.State.Content(
                selected = expected.first(),
                images = expected
            ),
            viewModel.state.value
        )
    }

    @Test
    fun `WHEN getItems returns empty list THEN show Empty screen`() = runTest {
        // GIVEN
        val items = emptyList<Item>()
        coEvery { getItems() } returns flowOf(items)
        initViewModel()

        assertEquals(
            GalleryViewModel.State.Empty,
            viewModel.state.value
        )
    }

    @Test
    fun `WHEN getItems takes more time THEN show Loading screen`() = runTest {
        // GIVEN
        val delay = 100L
        coEvery { getItems() } returns flow {
            delay(delay)
            emptyList<Item>()
        }
        initViewModel()
        assertEquals(
            GalleryViewModel.State.Loading,
            viewModel.state.value
        )
        advanceTimeBy(delay)
    }

    private fun initViewModel() {
        viewModel = GalleryViewModel(
            getItems = getItems,
            exportItems = exportItems
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
