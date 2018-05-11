package re.spitfy.ctftime.utils.extensions

import io.reactivex.Flowable
import re.spitfy.ctftime.presentation.Result
import re.spitfy.ctftime.utils.SchedulerProvider

fun <T> Flowable<T>.toResult(schedulerProvider: SchedulerProvider): Flowable<Result<T>> {
    return compose {
        item -> item
            .map { Result.success(it) }
            .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
            .observeOn(schedulerProvider.ui())
            .startWith(Result.inProgress())
    }
}