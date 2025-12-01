package com.example.educalink_ev2

import com.example.educalink_ev2.model.Post
import com.example.educalink_ev2.repository.PostRepository
import com.example.educalink_ev2.viewmodel.ResourcesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResourcesViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before fun setup() { Dispatchers.setMain(testDispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test
    fun `cargarNoticias actualiza la lista`() = runTest {
        val repo = mockk<PostRepository>()
        coEvery { repo.obtenerPosts() } returns listOf(Post(1, "Test", "Body", 1))
        val viewModel = ResourcesViewModel(repo)

        advanceUntilIdle() // Espera a que termine la corrutina

        assertEquals(1, viewModel.noticias.value.size)
    }
}