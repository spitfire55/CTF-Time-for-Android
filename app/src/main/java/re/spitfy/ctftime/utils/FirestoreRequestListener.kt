package re.spitfy.ctftime.utils

interface FirestoreRequestListener<T> {

    fun onSuccess(response: T)

    fun onFailure(error: Throwable)
}