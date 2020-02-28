package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 */

internal val RANDOM = Random(System.currentTimeMillis())

internal val PRODUCT_IDS = (0 until RANDOM.nextInt(20, 30)).map { RANDOM.nextLong() }.toSet()
private val PRODUCT_NAMES = setOf(
        "Gold", "Silver", "Copper",
        "USD/GBP", "AUD/USD", "GBP/EUR",
        "US30", "FTSE100", "S&P500",
        "DAX", "EURO50", "OIL",
        "Microsoft", "Apple", "Amazon",
        "Aon", "MSCI", "Nasdaq",
        "AMD", "GoPro", "Plug Power",
        "Disney", "Zynga", "Netflix",
        "Twitter", "Facebook", "Square",
        "Nvidia", "NIO", "Activision Blizzard"
)

internal val PRODUCT_TYPES = listOf(
        "Commodity",
        "Share",
        "Index",
        "Currency"
)

internal val PRODUCT_TOS = PRODUCT_IDS.zip(PRODUCT_NAMES).map { (id, name) ->
    id to ProductTO(
            name,
            PRODUCT_TYPES.random(RANDOM)
    )
}.toMap()

internal val AVAILABLE_LOCALES = Locale.getAvailableLocales()
internal val PRODUCT_DETAILS_TOS = PRODUCT_TOS.map { (id, product) ->
    val locale = generateSequence { AVAILABLE_LOCALES.random(RANDOM) }
            .first { isLocaleAcceptable(it) }

    id to ProductDetailsTO(
            country = locale.country,
            type = product.type,
            subtype = "Subtype" + RANDOM.nextInt(10),
            currency = Currency.getInstance(locale).displayName,
            spread = RANDOM.nextDouble(),
            margin = (RANDOM.nextDouble() * 100).roundToInt()
    )
}.toMap()

private fun isLocaleAcceptable(locale: Locale): Boolean {
    val isCurrencyAvailable = try {
        Currency.getInstance(locale)
        true
    } catch (error: Exception) {
        false
    }

    return locale.country != null && isCurrencyAvailable
}