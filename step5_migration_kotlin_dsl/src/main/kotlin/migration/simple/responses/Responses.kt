package migration.simple.responses

import migration.simple.types.Stats
import migration.simple.types.User

interface Response {
    val success: Boolean
    val description: String
}

data class DeleteResponse(
        override val success: Boolean,
        override val description: String
) : Response

data class StatsResponse(
        override val success: Boolean,
        override val description: String,
        val stats: Stats
) : Response

data class UserAddResponse(
        override val success: Boolean,
        override val description: String,
        val userId: Long
) : Response

data class UserResponse(
        override val success: Boolean,
        override val description: String,
        val users: List<User>
) : Response
