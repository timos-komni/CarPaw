package com.timkom.carpaw.data.places

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.timkom.carpaw.BuildConfig
import com.timkom.carpaw.util.createTAGForKClass
import java.util.concurrent.ExecutionException

/**
 * Google Maps Places SDK "wrapper" class
 */
class PlacesManager private constructor(context: Context) {

    private val autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    private val client: PlacesClient = Places.createClient(context)

    enum class SearchType(val type: String) {
        CITIES(PlaceTypes.CITIES),
        ADDRESS(PlaceTypes.ADDRESS)
    }

    @WorkerThread
    fun request(query: String?, searchType: SearchType): FindAutocompletePredictionsResponse? {
        val task = client.findAutocompletePredictions(FindAutocompletePredictionsRequest.builder().apply {
            sessionToken = autocompleteSessionToken
            typesFilter.add(searchType.type)
            setQuery(query)
        }.build())
        return try {
            Tasks.await(task)
        } catch (e: ExecutionException) {
            Log.e(TAG, "Task failed: ${e.message}")
            e.printStackTrace()
            null
        } catch (e: InterruptedException) {
            Log.e(TAG , "Task was interrupted: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    companion object {
        private val TAG = createTAGForKClass(PlacesManager::class)

        private var INSTANCE: PlacesManager? = null

        fun getInstance(context: Context): PlacesManager {
            if (INSTANCE == null) {
                Places.initialize(context, BuildConfig.PLACES_API_KEY)
                INSTANCE = PlacesManager(context)
            }
            return INSTANCE!!
        }
    }
}