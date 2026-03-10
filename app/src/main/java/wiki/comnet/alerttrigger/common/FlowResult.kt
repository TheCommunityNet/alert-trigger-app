package wiki.comnet.alerttrigger.common

sealed class FlowResult<T> {
    class Init<T>() : FlowResult<T>()

    class Success<T>(val data: T) : FlowResult<T>()

    class Error<T>(val message: String) : FlowResult<T>()

    class Loading<T>() : FlowResult<T>()
}