package re.spitfy.ctftime.viewobject

class Resource<out T> private constructor(val data: T?, val error: Exception?) {

    constructor(data: T?): this(data, null)
    constructor(error: Exception?): this(null, error)

    fun isSuccessful(): Boolean = data != null && error == null


}