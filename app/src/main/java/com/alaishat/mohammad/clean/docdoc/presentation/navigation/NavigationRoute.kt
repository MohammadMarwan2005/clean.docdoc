package com.alaishat.mohammad.clean.docdoc.presentation.navigation

import android.os.Bundle
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Serializable
sealed class NavigationRoute(val hasBottomNavBar: Boolean = false) {

    companion object {
        const val APP_DOMAIN_URL = "https://example.com" // Used for deep links

        // Cache all sealed subclasses and their object instances by qualified name
        private val routeMap: Map<String, NavigationRoute> by lazy {
            NavigationRoute::class.sealedSubclasses.mapNotNull { subclass ->
                val key = subclass.qualifiedName
                val instance = subclass.objectInstance
                if (key != null && instance != null) key to instance else null
            }.toMap()
        }

        fun fromRoute(route: String): NavigationRoute? {
            return routeMap.entries.firstOrNull { route.contains(it.key) }?.value
        }

        fun fromRoute(route: String, args: Bundle?): NavigationRoute? {
            val matchedEntry = routeMap.entries.firstOrNull { route.contains(it.key) }
            val matchedRoute = matchedEntry?.value ?: return null

            // If it's an object, return it directly
            if (matchedRoute::class.objectInstance != null) return matchedRoute

            // Otherwise, instantiate with bundle
            return createInstance(matchedRoute::class, args)
        }

        private fun <T : Any> createInstance(kClass: KClass<T>, bundle: Bundle?): T? {
            val constructor = kClass.primaryConstructor ?: return kClass.objectInstance
            val args = constructor.parameters.associateWith { param ->
                bundle?.get(param.name)
            }

            return try {
                constructor.callBy(args)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    @Serializable
    data object HomeRoute : NavigationRoute(hasBottomNavBar = true)

    @Serializable
    data object SpecializationsRoute : NavigationRoute(hasBottomNavBar = true)

    @Serializable
    data object SearchRoute : NavigationRoute(hasBottomNavBar = true)

    @Serializable
    data object AppointmentsRoute : NavigationRoute(hasBottomNavBar = true)

    @Serializable
    data object ProfileRoute : NavigationRoute(hasBottomNavBar = true)

    @Serializable
    data object LoginRoute : NavigationRoute()

    @Serializable
    data object RegisterRoute : NavigationRoute()
}
