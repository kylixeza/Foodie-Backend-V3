package com.oreyo.dataset


import com.oreyo.ml.clustering.ChallengeClustering
import org.nield.kotlinstatistics.averageBy
import org.nield.kotlinstatistics.kMeansCluster
import org.nield.kotlinstatistics.multiKMeansCluster
import org.nield.kotlinstatistics.simpleRegression

class ChallengeClusteringDataset {
	
	fun createDataset(
		uid: String,
		challenges: List<Triple<String, Int, Int>>,
		notes: List<Triple<String, Double, String>>
	): MutableList<ChallengeClustering> {
		val dataset = mutableListOf<ChallengeClustering>()
		
		challenges.forEach {
			dataset.add(
				ChallengeClustering(
					challengeId = it.first,
					calorieAverage = it.second.toDouble(),
					caloriePerDay = it.third.toDouble()
				)
			)
		}
		
		val calorieAverage = notes.asSequence()
			.averageBy(
				keySelector = { it.first },
				doubleSelector = { it.second }
			)[uid]!!
		
		
		val caloriePerDay =
			if(notes.size == 1) {
				notes[0].second
			} else {
				val linearRegressionModel = mutableListOf<Pair<Double, Double>>()
				var position = 1.0
				notes.groupBy { it.third }
					.forEach { (_, value) ->
						linearRegressionModel.add(
							Pair(
								position,
								value.asSequence()
									.averageBy(
										keySelector = { it.first },
										doubleSelector = { it.second }
									)[uid]!!
							)
						)
						position = position.plus(1.0)
					}
				linearRegressionModel.asSequence().simpleRegression().predict(position.plus(1.0))
			}
		
		dataset.add(
			ChallengeClustering(
				uid = uid,
				calorieAverage = calorieAverage,
				caloriePerDay = caloriePerDay
			)
		)
		
		return dataset
	}
	
	fun createModel(
		dataset: MutableList<ChallengeClustering>,
	) = dataset.kMeansCluster(
		k = 3,
		maxIterations = 100000,
		xSelector = { it.calorieAverage },
		ySelector = { it.caloriePerDay }
	)
}