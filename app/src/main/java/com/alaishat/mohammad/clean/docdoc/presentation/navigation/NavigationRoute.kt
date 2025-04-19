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
sealed class NavigationRoute(val hasBottomNavBar: Boolean = false, val isProtected: Boolean = false) {

    companion object {
        private const val APP_DOMAIN_URL = "https://docdoc.com"
        const val DOCTOR_URL = "$APP_DOMAIN_URL/doctor"

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
    data object AppointmentsRoute : NavigationRoute(hasBottomNavBar = true, isProtected = true)

    @Serializable
    data object ProfileRoute : NavigationRoute(hasBottomNavBar = true, isProtected = true)

    @Serializable
    data class LoginRoute(val isRedirected: Boolean = false) : NavigationRoute()

    @Serializable
    data object RegisterRoute : NavigationRoute()

    @Serializable
    data object AuthRoute : NavigationRoute()

    @Serializable
    data object OnboardingRoute : NavigationRoute()

    @Serializable
    data class DoctorDetailsRoute(val doctorId: Int) : NavigationRoute()

    @Serializable
    data class BookAppointmentRoute(val doctorId: Int) : NavigationRoute(isProtected = true)

    @Serializable
    data class AppointmentInfoRoute(val appointmentId: Int, val isJustBooked: Boolean = false) : NavigationRoute(isProtected = true)

}
