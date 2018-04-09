package re.spitfy.ctftime.presentation

sealed class Result<T>(val inProgress: Boolean) {

    class InProgress<T>: Result<T>(true) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    class Success<T>(var data: T): Result<T>(false)
    class Failure<T>(var errorMessage: String, val e: Throwable): Result<T>(false)

    companion object {
        fun <T> inProgress(): Result<T> = InProgress()
        fun <T> success(data: T): Result<T> = Success(data)
        fun <T> failure(errorMessage: String, e: Throwable): Result<T> = Failure(errorMessage, e)
    }
}