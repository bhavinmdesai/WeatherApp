package dev.bhavindesai.data.sources

import kotlinx.coroutines.flow.flow

abstract class MultiDataSource<LocalType, RequestType, ResponseType>
    : LocalDataSource<LocalType>,
    RemoteDataSource<RequestType, ResponseType> {

    abstract fun mapper(remoteData: ResponseType) : LocalType

    fun fetch(request: RequestType) = flow {
        emit(getLocalData())

        val remoteData = getRemoteData(request)
        remoteData?.let {
            val localData = mapper(remoteData)
            storeLocalData(localData)

            emit(localData)
        }
    }
}