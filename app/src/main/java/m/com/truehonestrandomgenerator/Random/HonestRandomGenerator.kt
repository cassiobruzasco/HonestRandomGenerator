package m.com.truehonestrandomgenerator.Random

import android.util.Log
import kotlin.random.Random

/**
 * This class Generates a random based on a number range
 * Starting on {listInitialRange} and ending on {listEndRange}
 *
 * @param mode What mode should apply for this pull, current modes available HONEST/NORMAL/EASY
 * @param numberOfRolls How many times this should be rolled
 * @param poolSize List range of 0 until x, this param represents the x. Example: 0..1000
 * @param handicap The value of current difficult for each consecutive roll
 */
class HonestRandomGenerator constructor(mode: String, numberOfRolls: Int, poolSize: Int, handicap: Int) {

    // Pass forward the actual counter for how many draws was Epic
    var finalEpicCounter: Int = 0
    // Pass forward the actual counter for how many draws was Rare
    var finalRareCounter: Int = 0
    // Pass forward the actual counter for how many draws was Common
    var finalCommonCounter: Int = 0
    // Counts and resets whenever there is an Epic draw
    private var counterEpic: Int = 0
    // Counts and resets whenever there is an Rare draw
    private var counterRare: Int = 0
    // Counts and resets whenever there is an Common draw
    private var counterCommon: Int = 0
    // How many times this should be rolled
    private var numberOfDraws: Int = numberOfRolls
    // Initial value for pool
    private var listInitialRange: Int = 0
    // List range of 0 until x, this param represents the x. Example: 0..1000
    private var listEndRange: Int = poolSize
    // Rarities
    private var rarityEpic: Double = 1.0
    private var rarityRare: Double = 5.0
    private var rarityCommon: Double = 33.3
    // Range of an epic pull
    private var epicDraw: IntRange = 0..(poolSize*(rarityEpic/100)).toInt()
    // Final range value
    private var epicChanceLast: Int = epicDraw.last
    // Range of a rare pull
    private var rareDraw: IntRange = epicDraw.last..(poolSize*(rarityRare/100)).toInt()
    // Final range value
    private var rareChanceLast: Int = rareDraw.last
    // Range of a common pull
    private var commonDraw: IntRange = rareDraw.last..(poolSize*(rarityCommon/100)).toInt()
    // Final range value
    private var commonChanceLast: Int = commonDraw.last
    // Value of current difficult for each consecutive roll
    private var handicap: Int = handicap
    // What mode should apply for this pull or pulls, current modes available HONEST/NORMAL/EASY
    private var mode: String = mode

    companion object GenerateRandom {
        // MODES
        const val HONEST_MODE: String = "HONEST_MODE"
        const val NORMAL_MODE: String = "NORMAL_MODE"
        const val EASY_MODE: String = "EASY_MODE"
        // RARITIES
        const val EPIC_RARITY: String = "EPIC_RARITY"
        const val RARE_RARITY: String = "RARE_RARITY"
        const val COMMON_RARITY: String = "COMMON_RARITY"
    }

    /**
     * Generate random numbers within a range
     */
    fun generateRandom(): String {
        val randomValue = List(numberOfDraws) {
            Random.nextInt(listInitialRange, listEndRange)
        }

        for (value in randomValue) {
            when (value) {
                in epicDraw -> counterEpic++
                in rareDraw -> counterRare++
                in commonDraw -> counterCommon++
            }

            if (mode == HONEST_MODE) {
                applyHonestRandom()
            }
        }
        return randomValue.toString()
    }

    /**
     * *#* Apply Honest Random *#*
     * This sum a handicap number at the end of each rarity pull.
     * Becomes more 'honest' after each pull to acquire a rarer pull
     */
    private fun applyHonestRandom() {
        if (counterEpic == 0) {
            epicDraw = 0..epicDraw.last + handicap
        } else {
            finalEpicCounter++
            counterEpic = 0
            epicDraw = 0..epicChanceLast
        }

        if (counterRare == 0) {
            rareDraw = rareDraw.first + handicap..rareDraw.last + handicap
        } else {
            finalRareCounter++
            counterRare = 0
            rareDraw = epicDraw.last..rareChanceLast + epicDraw.last
        }

        if (counterCommon == 0) {
            commonDraw = commonDraw.first + handicap..commonDraw.last + handicap
        } else {
            finalCommonCounter++
            counterCommon = 0
            commonDraw = rareDraw.last..commonChanceLast + rareDraw.last
        }
    }

    fun getCurrentRarityChance(rarity: String): Float {
        var result = 0f
        lateinit var range: IntRange
        when (rarity) {
            EPIC_RARITY -> range = epicDraw
            RARE_RARITY -> range = rareDraw
            COMMON_RARITY -> range = commonDraw
        }
        for (i in range) {
            result = result + (i.toFloat()/listEndRange)
        }
        return result
    }

    fun setRarityChance(rarity: String, newValue: Double) {
        when (rarity) {
            EPIC_RARITY -> {
                rarityEpic = newValue
                epicDraw = 0..(listEndRange*(rarityEpic/100)).toInt()
            }

            RARE_RARITY -> {
                rarityRare = newValue
                rareDraw = epicDraw.last..(listEndRange*(rarityRare/100)).toInt()
            }

            COMMON_RARITY -> {
                rarityCommon = newValue
                commonDraw = rareDraw.last..(listEndRange*(rarityCommon/100)).toInt()
            }
        }
    }
}