package m.com.truehonestrandomgenerator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import m.com.truehonestrandomgenerator.Random.HonestRandomGenerator
import java.lang.Integer.sum

class MainActivity : AppCompatActivity() {

    private val btnRoll by lazy { findViewById<Button>(R.id.btn_draw) }
    private val epic by lazy { findViewById<TextView>(R.id.txt_add) }
    private val rare by lazy { findViewById<TextView>(R.id.txt_add2) }
    private val common by lazy { findViewById<TextView>(R.id.txt_add3) }
    private val resultText by lazy { findViewById<TextView>(R.id.text_rolled) }
    private val epicChanceText by lazy { findViewById<TextView>(R.id.txt_epic_chance) }
    private val rareChanceText by lazy { findViewById<TextView>(R.id.txt_rare_chance) }
    private val commonChanceText by lazy { findViewById<TextView>(R.id.txt_common_chance) }
    private val rarityEpicEdit by lazy { findViewById<EditText>(R.id.rarity_epic) }
    private val rarityRareEdit by lazy { findViewById<EditText>(R.id.rarity_rare) }
    private val rarityCommonEdit by lazy { findViewById<EditText>(R.id.rarity_common) }
    private val btnSetRarity by lazy { findViewById<Button>(R.id.set_rarity) }
    private var totalDraw : Int = 0
    private lateinit var randomGenerator: HonestRandomGenerator

    /**
     * UI updating code
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val numberOfRolls = 5
        val poolSize = 1000
        val handicap = 1
        randomGenerator = HonestRandomGenerator(HonestRandomGenerator.HONEST_MODE, numberOfRolls, poolSize, handicap)
        setChanceTextDraw()

        btnRoll.text = "Number of pulls: $totalDraw"
        btnRoll.setOnClickListener{
            totalDraw = sum(totalDraw, numberOfRolls)
            btnRoll.text = "Number of pulls: $totalDraw"
            resultText.text = randomGenerator.generateRandom()

            epic.text = "Total Epic Draw: ${randomGenerator.finalEpicCounter}"
            rare.text = "Total Rare Draw: ${randomGenerator.finalRareCounter}"
            common.text = "Total Common Draw: ${randomGenerator.finalCommonCounter}"
            setChanceTextDraw()
        }

        btnSetRarity.setOnClickListener {
            randomGenerator.setRarityChance(HonestRandomGenerator.EPIC_RARITY, rarityEpicEdit.text.toString().toDouble())
            randomGenerator.setRarityChance(HonestRandomGenerator.RARE_RARITY, rarityRareEdit.text.toString().toDouble())
            randomGenerator.setRarityChance(HonestRandomGenerator.COMMON_RARITY, rarityCommonEdit.text.toString().toDouble())
            setChanceTextDraw()
        }
    }

    /**
     * This is not part of Honest Random Generator logic
     * This only updates values at UI to show the numbers running.
     */
    private fun setChanceTextDraw() {
        epicChanceText.text = "Epic %: ${String.format("%.2f", randomGenerator.getCurrentRarityChance(HonestRandomGenerator.EPIC_RARITY)).toFloat()}"
        rareChanceText.text = "Rare %: ${String.format("%.2f", randomGenerator.getCurrentRarityChance(HonestRandomGenerator.RARE_RARITY)).toFloat()}"
        commonChanceText.text = "Common %: ${String.format("%.2f", randomGenerator.getCurrentRarityChance(HonestRandomGenerator.COMMON_RARITY)).toFloat()}"
        rarityEpicEdit.setText(randomGenerator.getCurrentRarityChance(HonestRandomGenerator.EPIC_RARITY).toString())
        rarityRareEdit.setText(randomGenerator.getCurrentRarityChance(HonestRandomGenerator.RARE_RARITY).toString())
        rarityCommonEdit.setText(randomGenerator.getCurrentRarityChance(HonestRandomGenerator.COMMON_RARITY).toString())
    }
}
