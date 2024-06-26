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

    /**
     * The Places SDK Autocomplete session token.
     */
    private val autocompleteSessionToken = AutocompleteSessionToken.newInstance()
    /**
     * The Places SDK client.
     */
    private val client: PlacesClient = Places.createClient(context)

    /**
     * The search type.
     * @param type The text that represents the search type (should be retrieved from [PlaceTypes]).
     */
    enum class SearchType(val type: String) {
        CITIES(PlaceTypes.CITIES),
        ADDRESS(PlaceTypes.ADDRESS)
    }

    /**
     * Requests the autocomplete predictions for the specified query and [SearchType].
     * **Should be called from a background thread!**
     * @param query The query to search for.
     * @param searchType The search type.
     * @return The [FindAutocompletePredictionsResponse] or null on failure.
     */
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

        /**
         * Instance of the [PlacesManager]
         */
        private var INSTANCE: PlacesManager? = null

        /**
         * Returns the instance of the [PlacesManager].
         * @param context The application context.
         * @return The [PlacesManager] instance
         */
        fun getInstance(context: Context): PlacesManager {
            if (INSTANCE == null) {
                Places.initialize(context, BuildConfig.PLACES_API_KEY)
                INSTANCE = PlacesManager(context)
            }
            return INSTANCE!!
        }
    }
}