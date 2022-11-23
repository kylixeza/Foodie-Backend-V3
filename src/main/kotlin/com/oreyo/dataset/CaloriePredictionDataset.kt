package com.oreyo.dataset

import krangl.DataFrame
import krangl.readCSV
import org.nield.kotlinstatistics.NaiveBayesClassifier
import org.nield.kotlinstatistics.toNaiveBayesClassifier

class CaloriePredictionDataset {
	val df = DataFrame.readCSV("src/main/resources/calories.csv")
	private var epoch = 0
	private lateinit var nbc: NaiveBayesClassifier<Set<String>, Int>
	private var k1 = 0.5
	private var k2 = 2 * k1
	var predictionResult = Triple(0, 0.0, 0)
	
	private fun mapTransform() =
		df.rows.map {
			CaloriePredictionDatasetModel(
				it["food"] as String,
				it["category"] as String,
				it["calories"] as Int,
			)
		}
	
	private fun train(k1: Double = 0.5, k2: Double = 1.0) = run {
		val data = mapTransform()
		
		nbc = data.toNaiveBayesClassifier(
			featuresSelector = { listOf(
				it.food.splitWords().toSet(),
				it.category.splitWords().toSet())
			},
			categorySelector = { it.calories },
			k1 = k1,
			k2 = k2
		)
		
		epoch++
		nbc
	}
	
	private fun Double.epochCallback(block: () -> Unit) = run {
		if (this != -1.0 && this < 0.90 && epoch < 100) {
			block()
		}
	}
	
	fun predict(features: List<String>) {
		train(k1, k2)
		k1 -= 0.001
		k2 = 3 * k1
		
		val prediction = nbc.predictWithProbability(
			features[0].splitWords().toSet(),
			features[1].splitWords().toSet()
		)
		val result = prediction?.category ?: -1
		val accuracy = prediction?.probability ?: -1.0
		
		println("Epoch: $epoch, Result: $result, Accuracy: $accuracy")
		predictionResult = if (accuracy >= 0.7) {
			Triple(result, accuracy, epoch)
		} else {
			Triple(-1, -1.0, epoch)
		}
		
		accuracy.epochCallback {
			if (k1 > 0 && k2 > 0) {
				predict(features)
			}
		}
	}
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").toLowerCase() }
		.filter { it.isNotEmpty() }
}