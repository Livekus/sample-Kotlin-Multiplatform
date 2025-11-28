package com.example.demo.core.presenter.usecase

import com.example.demo.core.data.UserRepository
import com.example.demo.core.domain.User
import com.example.demo.core.domain.UserRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UserUsecaseTest {

    private class FakeRepo : UserRepository {
        var lastGetUserId: Long? = null
        var lastCreate: UserRequest? = null
        var lastUpdate: Pair<Long, UserRequest>? = null
        var lastDeleteId: Long? = null

        override fun getUsers(): Flow<List<User>> = flowOf(listOf(User(1, "A", "a@ex.com")))
        override fun getUser(id: Long): Flow<User?> {
            lastGetUserId = id
            return flowOf(null)
        }
        override fun createUser(request: UserRequest): Flow<User> {
            lastCreate = request
            return flowOf(User(2, request.name, request.email))
        }
        override fun updateUser(id: Long, request: UserRequest): Flow<User?> {
            lastUpdate = id to request
            return flowOf(User(id, request.name, request.email))
        }
        override fun deleteUser(id: Long): Flow<Boolean> {
            lastDeleteId = id
            return flowOf(true)
        }
    }

    @Test
    fun delegates_toRepository_forAllOperations() = runTest {
        val repo = FakeRepo()
        val uc = UserUsecase(repo)

        val list = uc.listUsers().first()
        assertTrue(list.isNotEmpty())

        val user = uc.getUser(10).first()
        assertNull(user)
        assertEquals(10, repo.lastGetUserId)

        val created = uc.createUser("Bob", "bob@ex.com").first()
        assertEquals("Bob", created.name)
        assertEquals("bob@ex.com", repo.lastCreate?.email)

        val updated = uc.updateUser(3, "C", "c@ex.com").first()
        assertEquals(3, updated?.id)
        assertEquals("C", repo.lastUpdate?.second?.name)

        val deleted = uc.deleteUser(99).first()
        assertTrue(deleted)
        assertEquals(99, repo.lastDeleteId)
    }
}
