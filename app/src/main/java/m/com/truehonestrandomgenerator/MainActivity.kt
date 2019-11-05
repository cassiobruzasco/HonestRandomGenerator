package m.com.truehonestrandomgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import m.com.truehonestrandomgenerator.Random.HonestRandomGenerator
import java.lang.Integer.sum

class MainActivity : AppCompatActivity() {

    private val btn by lazy { findViewById<Button>(R.id.btn_draw) }
    private val epic by lazy { findViewById<TextView>(R.id.txt_add) }
    private val rare by lazy { findViewById<TextView>(R.id.txt_add2) }
    private val common by lazy { findViewById<TextView>(R.id.txt_add3) }
    private val resultText by lazy { findViewById<TextView>(R.id.text_rolled) }
    private val epicChanceText by lazy { findViewById<TextView>(R.id.txt_epic_chance) }
    private val rareChanceText by lazy { findViewById<TextView>(R.id.txt_rare_chance) }
    private val commonChanceText by lazy { findViewById<TextView>(R.id.txt_common_chance) }
    private var totalDraw : Int = 0
    private var epicChance: Float = 0f
    private var rareChance: Float = 0f
    private var commonChance: Float = 0f

    /**
     * UI updating code
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setChanceDraw()

        btn.text = "total draw: $totalDraw"
        btn.setOnClickListener{view ->
            totalDraw = sum(totalDraw, HonestRandomGenerator.numberOfDraws)
            btn.text = "Summons used: $totalDraw"
            resultText.text = HonestRandomGenerator.generateRandom(HonestRandomGenerator.HONEST_MODE)

            epic.text = "Epic Draw: ${HonestRandomGenerator.publicEpicCounter}"
            rare.text = "Rare Draw: ${HonestRandomGenerator.publicRareCounter}"
            common.text = "Common Draw: ${HonestRandomGenerator.publicCommonCounter}"
            setChanceDraw()
        }
    }

    /**
     * This is not part of Honest Random Generator logic
     * This only updates values at UI to show the numbers running.
     */
    private fun setChanceDraw() {
        epicChance = HonestRandomGenerator.epicDraw.last.toFloat() / HonestRandomGenerator.listEndRange.toFloat()
        epicChanceText.text = "Epic %: ${epicChance}"
        rareChance = HonestRandomGenerator.rareDraw.last.toFloat() / HonestRandomGenerator.listEndRange.toFloat()
        rareChanceText.text = "Epic %: ${rareChance}"
        commonChance = HonestRandomGenerator.commonDraw.last.toFloat() / HonestRandomGenerator.listEndRange.toFloat()
        commonChanceText.text = "Epic %: ${commonChance}"
    }
}
