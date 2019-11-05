package m.com.truehonestrandomgenerator.Random

import kotlin.random.Random

/**
 * This class Generates a random based on a number range
 * Starting on {listInitialRange} and ending on {listEndRange}
 */
class HonestRandomGenerator {

    /**
     * @param publicEpicCounter Pass forward the actual counter for how many draws was Epic
     * @param publicRareCounter Pass forward the actual counter for how many draws was Rare
     * @param publicCommonCounter Pass forward the actual counter for how many draws was Common
     * @param counterEpic Counts and resets whenever there is an Epic draw
     * @param counterRare Counts and resets whenever there is an Rare draw
     * @param counterCommon Counts and resets whenever there is an Common draw
     * @param numberofDraws How many draw execute at a time
     * @param epicDraw A range of numbers equivalent to the chances of making an Epic draw
     * @param rareDraw A range of numbers equivalent to the chances of making an Rare draw
     * @param commonDraw A range of numbers equivalent to the chances of making an Common draw
     * @param handcap This is the number representing how easy will be the next pull
     */
    companion object GenerateRandom {
        var publicEpicCounter: Int = 0
        var publicRareCounter: Int = 0
        var publicCommonCounter: Int = 0
        private var counterEpic: Int = 0
        private var counterRare: Int = 0
        private var counterCommon: Int = 0
        var numberOfDraws: Int = 1
        var listInitialRange: Int = 0
        var listEndRange: Int = 1000
        var epicDraw: IntRange = 0..10
        private var epicChanceLast: Int = epicDraw.last
        var rareDraw: IntRange = epicDraw.last..50
        private var rareChanceLast: Int = rareDraw.last
        var commonDraw: IntRange = rareDraw.last..300
        private var commonChanceLast: Int = commonDraw.last
        var handcap: Int = 1
        // Constant of modes available
        val HONEST_MODE: String = "HONEST_MODE"
        val NORMAL_MODE: String = "NORMAL_MODE"
        val EASY_MODE: String = "EASY_MODE"

        /**
         * Generate random numbers within a range
         */
        fun generateRandom(mode: String): String {
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
         * Apply Honest Random
         * This sum a handcap number at the end of each rarity pull
         * to make more 'honest' pulling a rare number each time.
         */
        private fun applyHonestRandom() {
            if (counterEpic == 0) {
                epicDraw = 0..epicDraw.last+handcap
            } else {
                publicEpicCounter++
                counterEpic = 0
                epicDraw = 0..epicChanceLast
            }

            if (counterRare == 0) {
                rareDraw = rareDraw.first+handcap..rareDraw.last+handcap
            } else {
                publicRareCounter++
                counterRare = 0
                rareDraw = epicDraw.last..rareChanceLast+epicDraw.last
            }

            if (counterCommon == 0) {
                commonDraw = commonDraw.first+handcap..commonDraw.last+ handcap
            } else {
                publicCommonCounter++
                counterCommon = 0
                commonDraw = rareDraw.last..commonChanceLast+ rareDraw.last
            }
        }
    }
}