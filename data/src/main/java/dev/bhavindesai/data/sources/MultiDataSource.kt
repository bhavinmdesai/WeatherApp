package dev.bhavindesai.data.sources

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class MultiDataSource<LocalType, RequestType, ResponseType>
    : LocalDataSource<LocalType>,
    RemoteDataSource<RequestType, ResponseType> {

    abstract fun mapper(remoteData: ResponseType) : LocalType

    fun fetch(request: RequestType) = flow {
        emit(getLocalData())

        getRemoteData(request)
            .collect {
                it?.let { remoteData ->
                    val localData = mapper(remoteData)
                    storeLocalData(localData)

                    emit(localData)
                }
            }
    }
}